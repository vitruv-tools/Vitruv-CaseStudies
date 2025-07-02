package tools.vitruv.applications.util.temporary.java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.activextendannotations.Utility;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.commons.NamespaceAwareElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.containers.ContainersPackage.Literals;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.EnumConstant;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.change.correspondence.view.EditableCorrespondenceModelView;

/**
 * A utility class for creating, manipulating, and querying JaMoPP (Java Model
 * Printer and Parser) elements related to containers (Packages, CompilationUnits)
 * and classifiers (Classes, Interfaces, Enums).
 */
@Utility
public final class JavaContainerAndClassifierUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavaContainerAndClassifierUtil() {
    }

    /**
     * Creates a new JaMoPP Class.
     *
     * @param name         The simple name of the class.
     * @param visibility   The visibility of the class (e.g., PUBLIC, PRIVATE).
     * @param isAbstract   A boolean indicating if the class should be abstract.
     * @param isFinal      A boolean indicating if the class should be final.
     * @return The newly created {@link org.emftext.language.java.classifiers.Class}.
     */
    public static org.emftext.language.java.classifiers.Class createJavaClass(final String name,
                                                                              final JavaVisibility visibility, final boolean isAbstract, final boolean isFinal) {
        final org.emftext.language.java.classifiers.Class jClass = ClassifiersFactory.eINSTANCE.createClass();
        JavaModifierUtil.setName(jClass, name);
        JavaModifierUtil.setJavaVisibilityModifier(jClass, visibility);
        JavaModifierUtil.setAbstract(jClass, isAbstract);
        JavaModifierUtil.setFinal(jClass, isFinal);
        return jClass;
    }

    /**
     * Creates a new JaMoPP Package.
     *
     * @param name              The name of the package segment to create (e.g., "mypackage").
     * @param containingPackage The parent package, or null if it is a root package.
     * @return The newly created {@link Package}.
     */
    public static Package createJavaPackage(final String name, final Package containingPackage) {
        final Package jPackage = ContainersFactory.eINSTANCE.createPackage();
        JavaModifierUtil.setName(jPackage, name);
        final EList<String> namespaces = jPackage.getNamespaces();
        final List<String> packageStringList = getJavaPackageAsStringList(containingPackage);
        Iterables.addAll(namespaces, packageStringList);
        return jPackage;
    }

    /**
     * Creates a new JaMoPP Interface.
     *
     * @param name            The simple name of the interface.
     * @param superInterfaces A list of interfaces that this new interface should extend. Can be empty.
     * @return The newly created {@link Interface}.
     */
    public static Interface createJavaInterface(final String name, final List<Interface> superInterfaces) {
        final Interface jInterface = ClassifiersFactory.eINSTANCE.createInterface();
        JavaModifierUtil.setName(jInterface, name);
        jInterface.makePublic();
        if (superInterfaces != null && !superInterfaces.isEmpty()) {
            jInterface.getExtends().addAll(JavaTypeUtil.createNamespaceReferenceFromList(superInterfaces));
        }
        return jInterface;
    }

    /**
     * Creates a new JaMoPP Enumeration.
     *
     * @param name          The simple name of the enum.
     * @param visibility    The visibility of the enum.
     * @param constantsList A list of {@link EnumConstant}s for the enum. Can be null or empty.
     * @return The newly created {@link Enumeration}.
     */
    public static Enumeration createJavaEnum(final String name, final JavaVisibility visibility,
                                             final List<EnumConstant> constantsList) {
        final Enumeration jEnum = ClassifiersFactory.eINSTANCE.createEnumeration();
        JavaModifierUtil.setName(jEnum, name);
        JavaModifierUtil.setJavaVisibilityModifier(jEnum, visibility);
        addEnumConstantIfNotNull(jEnum, constantsList);
        return jEnum;
    }

    /**
     * Adds a list of enum constants to an enumeration if the list is not null or empty.
     *
     * @param jEnum         The enumeration to which the constants will be added.
     * @param constantsList The list of constants to add.
     * @return {@code true} if constants were added, {@code false} otherwise.
     */
    public static boolean addEnumConstantIfNotNull(final Enumeration jEnum, final List<EnumConstant> constantsList) {
        if (constantsList != null && !constantsList.isEmpty()) {
            return jEnum.getConstants().addAll(constantsList);
        }
        return false;
    }

    /**
     * Creates an empty compilation unit with a derived name.
     *
     * @param nameWithoutFileExtension The name of the main classifier, which will be used to name the file.
     * @return A new, empty {@link CompilationUnit}.
     */
    public static CompilationUnit createEmptyCompilationUnit(final String nameWithoutFileExtension) {
        final CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
        cu.setName(nameWithoutFileExtension + ".java");
        return cu;
    }

    /**
     * Creates a new compilation unit containing a given classifier within a specified package.
     *
     * @param jClassifier The classifier to be placed in the compilation unit.
     * @param jPackage    The package that will contain the compilation unit.
     * @return The newly created {@link CompilationUnit}.
     */
    public static CompilationUnit createJavaCompilationUnitWithClassifierInPackage(final ConcreteClassifier jClassifier,
                                                                                   final Package jPackage) {
        final CompilationUnit compUnit = createEmptyCompilationUnit(jClassifier.getName());
        compUnit.getClassifiers().add(jClassifier);
        compUnit.getNamespaces().addAll(getJavaPackageAsStringList(jPackage));
        return compUnit;
    }

    /**
     * Removes a type reference from an iterator if it refers to the specified classifier.
     *
     * @param iterator    An iterator over a collection of {@link TypeReference}s.
     * @param classifier The classifier whose references should be removed.
     */
    public static void removeClassifierFromIterator(final Iterator<TypeReference> iterator,
                                                    final ConcreteClassifier classifier) {
        while (iterator.hasNext()) {
            final TypeReference typeReference = iterator.next();
            if (typeReference instanceof NamespaceClassifierReference) {
                final NamespaceClassifierReference ncr = (NamespaceClassifierReference) typeReference;
                final Classifier target = ncr.getClassifierReferences().get(0).getTarget();
                if (classifier.getName().equals(target.getName())) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Converts a JaMoPP package into a list of its namespace segments including its own name.
     *
     * @param jPackage The package to convert.
     * @return A list of strings representing the fully qualified package name, or an empty list if the package is null or unnamed.
     */
    public static List<String> getJavaPackageAsStringList(final Package jPackage) {
        if (jPackage != null && jPackage.getName() != null && !jPackage.getName().isEmpty()) {
            final ArrayList<String> packageStringList = new ArrayList<>();
            packageStringList.addAll(jPackage.getNamespaces());
            packageStringList.add(jPackage.getName());
            return packageStringList;
        }
        return Collections.emptyList();
    }

    /**
     * Finds a Java field (attribute) within a class by its name.
     *
     * @param jClass        The class to search within.
     * @param attributeName The name of the field to find.
     * @return The found {@link Field}, or {@code null} if no field with that name exists.
     */
    public static Field getJavaAttributeByName(final org.emftext.language.java.classifiers.Class jClass,
                                               final String attributeName) {
        for (final Field member : Iterables.filter(jClass.getMembers(), Field.class)) {
            if (Objects.equals(member.getName(), attributeName)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Retrieves the first constructor defined in a Java class.
     *
     * @param jClass The class to search within.
     * @return The first found {@link Constructor}, or {@code null} if the class has no explicit constructors.
     */
    public static Constructor getFirstJavaConstructor(final org.emftext.language.java.classifiers.Class jClass) {
        final Iterable<Constructor> candidates = Iterables.filter(jClass.getMembers(), Constructor.class);
        return !Iterables.isEmpty(candidates) ? Iterables.get(candidates, 0) : null;
    }

    /**
     * Removes the compilation unit containing a specific classifier from a package.
     *
     * @param jPackage    The package from which to remove the compilation unit.
     * @param jClassifier The classifier whose containing compilation unit should be removed.
     */
    public static void removeJavaClassifierFromPackage(final Package jPackage, final ConcreteClassifier jClassifier) {
        final Iterator<CompilationUnit> iterator = jPackage.getCompilationUnits().iterator();
        final String classifierName = jClassifier.getName();
        while (iterator.hasNext()) {
            if (iterator.next().getName().startsWith(classifierName + ".")) {
                iterator.remove();
            }
        }
    }

    /**
     * Creates a {@code package-info.java} file in a given directory.
     *
     * @param directory   The path to the directory where the file will be created.
     * @param packageName The fully qualified name of the package.
     * @return The created {@link File}.
     */
    public static File createPackageInfo(final String directory, final String packageName) {
        try {
            final File file = new File(directory + "/package-info.java");
            file.createNewFile();
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println("package " + packageName + ";");
            }
            return file;
        } catch (final IOException e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * Finds a classifier of a specific type by name within a package. The search is case-insensitive.
     *
     * @param <T>            The type of the classifier to find (e.g., Class, Interface).
     * @param name           The simple name of the classifier.
     * @param javaPackage    The package to search in.
     * @param classifierType The class object for the generic type T.
     * @return The found classifier, or {@code null} if none is found.
     * @throws IllegalStateException if more than one matching classifier is found.
     */
    public static <T extends ConcreteClassifier> T findClassifier(final String name, final Package javaPackage,
                                                                  final Class<T> classifierType) {

        final List<T> matchingClassifiers = javaPackage.getCompilationUnits().stream()
                .flatMap(cu -> cu.getClassifiers().stream())
                .filter(classifierType::isInstance)
                .map(classifierType::cast)
                .filter(it -> it.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (matchingClassifiers.size() > 1) {
            throw new IllegalStateException("Multiple matching classifers were found: " + matchingClassifiers);
        }
        return matchingClassifiers.isEmpty() ? null : matchingClassifiers.get(0);
    }

    /**
     * Finds the JaMoPP package corresponding to a given classifier by querying a correspondence model.
     *
     * @param classifier          The classifier whose containing package is sought.
     * @param correspondenceModel The correspondence model view to query.
     * @return The corresponding {@link Package}, or {@code null} if not found.
     */
    public static Package getContainingPackageFromCorrespondenceModel(final Classifier classifier,
                                                                      final EditableCorrespondenceModelView<?> correspondenceModel) {
        String namespace = classifier.getContainingCompilationUnit().getNamespacesAsString();
        if (namespace.endsWith("$") || namespace.endsWith(".")) {
            namespace = namespace.substring(0, namespace.length() - 1);
        }

        final String finalNamespace = namespace;
        final Iterable<Package> packagesWithCorrespondences = Iterables.filter(
                correspondenceModel.getCorrespondingEObjects(Literals.PACKAGE), Package.class);

        return Iterables.tryFind(packagesWithCorrespondences,
                pack -> finalNamespace.equals(pack.getNamespacesAsString() + pack.getName())).orNull();
    }

    /**
     * Retrieves the list of namespace segments for a given Java element.
     *
     * @param element The element (e.g., a Classifier, CompilationUnit).
     * @return The list of namespace strings.
     * @throws IllegalArgumentException if the element type is not supported.
     */
    public static List<String> getJavaNamespace(final NamedElement element) {
        if (element instanceof ConcreteClassifier) {
            return getJavaNamespace(getContainingCompilationUnit(element));
        }
        if (element instanceof CompilationUnit) {
            return ((CompilationUnit) element).getNamespaces();
        }
        if (element == null) {
            throw new IllegalArgumentException("Can not retrieve namespace for null");
        }
        throw new IllegalArgumentException("Unsupported type for retrieving namespace: " + element.getClass().getName());
    }

    /**
     * Retrieves the containing compilation unit for a given Java element.
     *
     * @param element The element (e.g., a Classifier, Member, Parameter).
     * @return The containing {@link CompilationUnit}.
     * @throws IllegalArgumentException if the element type is not supported.
     */
    public static CompilationUnit getContainingCompilationUnit(final NamedElement element) {
        if (element instanceof CompilationUnit) {
            return (CompilationUnit) element;
        }
        if (element instanceof ConcreteClassifier) {
            return (CompilationUnit) element.eContainer();
        }
        if (element instanceof Member) {
            return getContainingCompilationUnit((ConcreteClassifier) element.eContainer());
        }
        if (element instanceof Parameter) {
            return getContainingCompilationUnit((Member) element.eContainer());
        }
        if (element == null) {
            throw new IllegalArgumentException("Can not retrieve compilation unit for null");
        }
        throw new IllegalArgumentException(
                "Unsupported type for retrieving compilation unit: " + element.getClass().getName());
    }

    /**
     * Extracts the root segment from a fully qualified package name.
     *
     * @param packageName The package name (e.g., "org.example.test").
     * @return The root segment ("org"), or the full name if there are no dots. Returns null if input is null.
     */
    public static String getRootPackageName(final String packageName) {
        if (packageName == null) {
            return null;
        }
        final String[] packageParts = packageName.split("\\.");
        return packageParts.length > 0 ? packageParts[0] : null;
    }

    /**
     * Extracts all but the root segment from a fully qualified package name.
     *
     * @param packageName The package name (e.g., "org.example.test").
     * @return The trailing segments ("example.test"), or the full name if there are no dots. Returns null if input is null.
     */
    public static String getLastPackageName(final String packageName) {
        if (packageName == null) {
            return null;
        }
        final int firstDotIndex = packageName.indexOf(".");
        return firstDotIndex >= 0 ? packageName.substring(firstDotIndex + 1) : packageName;
    }

    /**
     * Constructs the fully qualified name for a compilation unit based on a classifier name and its namespaces.
     *
     * @param namespacesAsString A dot-separated string of parent packages.
     * @param classifierName     The simple name of the main classifier in the unit.
     * @return The full name of the compilation unit (e.g., "com.example.MyClass.java").
     */
    private static String getCompilationUnitName(final String namespacesAsString, final String classifierName) {
        return namespacesAsString + classifierName + ".java";
    }

    /**
     * Constructs the fully qualified name for a compilation unit.
     *
     * @param containingPackage The package containing the unit. Can be null.
     * @param className         The simple name of the main class in the unit.
     * @return The full name of the compilation unit.
     */
    public static String getCompilationUnitName(final Package containingPackage, final String className) {
        final StringBuilder builder = new StringBuilder();
        if (containingPackage != null) {
            builder.append(containingPackage.getNamespacesAsString());
            builder.append(containingPackage.getName());
            builder.append(".");
        }
        return getCompilationUnitName(builder.toString(), className);
    }

    /**
     * Constructs the fully qualified name for a compilation unit from an Optional package.
     *
     * @param containingPackage An Optional that may contain the package.
     * @param className         The simple name of the main class in the unit.
     * @return The full name of the compilation unit.
     */
    public static String getCompilationUnitName(final Optional<Package> containingPackage, final String className) {
        return getCompilationUnitName(containingPackage.orElse(null), className);
    }

    /**
     * Updates the namespaces of an element.
     *
     * @param elementToChange The element whose namespaces are to be updated.
     * @param newNamespaces   The new list of namespace strings.
     * @return {@code true} if the namespaces were changed, {@code false} otherwise.
     */
    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange,
                                           final List<String> newNamespaces) {
        final EList<String> currentNamespaces = elementToChange.getNamespaces();
        if (!Objects.equals(newNamespaces, currentNamespaces)) {
            currentNamespaces.clear();
            currentNamespaces.addAll(newNamespaces);
            return true;
        }
        return false;
    }

    /**
     * Updates the namespaces of an element based on an Optional containing package.
     *
     * @param elementToChange   The element to update.
     * @param containingPackage An Optional that may contain the package.
     * @return {@code true} if the namespaces were changed, {@code false} otherwise.
     */
    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange,
                                           final Optional<Package> containingPackage) {
        final List<String> newNamespaces = containingPackage.map(JavaContainerAndClassifierUtil::getJavaPackageAsStringList)
                .orElse(Collections.emptyList());
        return updateNamespaces(elementToChange, newNamespaces);
    }

    /**
     * Updates the namespaces of an element to match a given package.
     *
     * @param elementToChange   The element to update.
     * @param containingPackage The package whose namespace structure should be adopted.
     * @return {@code true} if the namespaces were changed, {@code false} otherwise.
     */
    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange, final Package containingPackage) {
        return updateNamespaces(elementToChange, getJavaPackageAsStringList(containingPackage));
    }

    /**
     * Updates the name of a named element.
     *
     * @param elementToChange The element whose name is to be updated.
     * @param newName         The new name for the element.
     * @return {@code true} if the name was changed, {@code false} otherwise.
     */
    public static boolean updateName(final NamedElement elementToChange, final String newName) {
        if (!Objects.equals(newName, elementToChange.getName())) {
            elementToChange.setName(newName);
            return true;
        }
        return false;
    }

    /**
     * Updates the file name of a compilation unit based on its namespaces and a new simple name.
     *
     * @param compilationUnit The compilation unit to update.
     * @param simpleName      The new simple name of the primary classifier.
     */
    public static void updateCompilationUnitName(final CompilationUnit compilationUnit, final String simpleName) {
        final String namespacesAsString = String.join(".", compilationUnit.getNamespaces());
        final String prefix = namespacesAsString.isEmpty() ? "" : namespacesAsString + ".";
        compilationUnit.setName(prefix + simpleName + ".java");
    }

    /**
     * Changes the name of a classifier and updates the name of its containing compilation unit accordingly.
     *
     * @param classifier The classifier to rename.
     * @param newName    The new simple name for the classifier.
     */
    public static void changeNameWithCompilationUnit(final Classifier classifier, final String newName) {
        updateName(classifier, newName);
        final CompilationUnit containingCompilationUnit = classifier.getContainingCompilationUnit();
        if (containingCompilationUnit != null) {
            updateCompilationUnitName(containingCompilationUnit, newName);
        }
    }

    /**
     * Checks if a compilation unit is part of an existing library (e.g., the JDK) by checking its URI scheme.
     *
     * @param compilationUnit The compilation unit to check.
     * @return {@code true} if the unit is from a library identified by a pathmap URI, {@code false} otherwise.
     */
    public static boolean isInExistingLibrary(final CompilationUnit compilationUnit) {
        return URIUtil.isPathmap(compilationUnit.eResource().getURI());
    }

    /**
     * Checks if a classifier is part of an existing library.
     *
     * @param classifier The classifier to check.
     * @return {@code true} if the classifier is from a library, {@code false} otherwise.
     */
    public static boolean isInExistingLibrary(final Classifier classifier) {
        return isInExistingLibrary(classifier.getContainingCompilationUnit());
    }
}