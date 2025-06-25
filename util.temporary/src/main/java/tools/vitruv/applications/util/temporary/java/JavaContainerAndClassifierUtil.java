package tools.vitruv.applications.util.temporary.java;

import com.google.common.collect.Iterables;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ClassifiersFactory;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.commons.NamespaceAwareElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.ContainersPackage;
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
 * Class for java classifier, package and compilation unit util functions
 *
 */
public final class JavaContainerAndClassifierUtil {
    /**
     * Creates and returns a new Java class with the given name, visibility, and modifiers.
     * The new class is not contained in a compilation unit.
     *
     * @param name the name of the class
     * @param visibility the visibility of the class
     * @param abstr true if the class should be abstract
     * @param fin true if the class should be final
     * @return the new class with the given attributes
     */
    public static org.emftext.language.java.classifiers.Class createJavaClass(
            final String name,
            final JavaVisibility visibility,
            final boolean abstr,
            final boolean fin) {

        final org.emftext.language.java.classifiers.Class jClass =
                ClassifiersFactory.eINSTANCE.createClass();

        JavaModifierUtil.setName(jClass, name);
        JavaModifierUtil.setJavaVisibilityModifier(jClass, visibility);
        JavaModifierUtil.setAbstract(jClass, abstr);
        JavaModifierUtil.setFinal(jClass, fin);

        return jClass;
    }


    /**
     * Creates a new java package
     * @param name the name of the new package
     * @param containingPackage the super package of the new package or null if it is the default package
     * @return the new package
     */
    public static org.emftext.language.java.containers.Package createJavaPackage(final String name, final org.emftext.language.java.containers.Package containingPackage) {
        final org.emftext.language.java.containers.Package jPackage = ContainersFactory.eINSTANCE.createPackage();
        JavaModifierUtil.setName(jPackage, name);
        EList<String> namespaces = jPackage.getNamespaces();
        List<String> packageAsStringList = JavaContainerAndClassifierUtil.getJavaPackageAsStringList(containingPackage);
        Iterables.<String>addAll(namespaces, packageAsStringList);
        return jPackage;
    }



    /**
     * Creates a new java interface with the given name and list of super interfaces
     * The created interface is not contained in a compilation unit.
     * @param name the name of the interface
     * @param superInterfaces the superinterfaces of the interface
     * @return the new interface
     */
    public static Interface createJavaInterface(final String name, final List<Interface> superInterfaces) {
        final Interface jInterface = ClassifiersFactory.eINSTANCE.createInterface();
        JavaModifierUtil.setName(jInterface, name);
        jInterface.makePublic();
        if (!IterableExtensions.isNullOrEmpty(superInterfaces)) {
            jInterface.getExtends().addAll(JavaTypeUtil.createNamespaceReferenceFromList(superInterfaces));
        }
        return jInterface;
    }

    /**
     * Creats a new java enum with the given properties
     * The created Enum is not contained in a compilationunit.
     * @param name the name of the enum
     * @param visibility the visibility of the enum
     * @param constantsList list of enum constants for the enum
     * @return the new enum
     */
    public static Enumeration createJavaEnum(final String name, final JavaVisibility visibility, final List<EnumConstant> constantsList) {
        final Enumeration jEnum = ClassifiersFactory.eINSTANCE.createEnumeration();
        JavaModifierUtil.setName(jEnum, name);
        JavaModifierUtil.setJavaVisibilityModifier(jEnum, visibility);
        JavaContainerAndClassifierUtil.addEnumConstantIfNotNull(jEnum, constantsList);
        return jEnum;
    }

    /**
     * Add constantList to the enum constants of the given jEnum if constantsList is not null or empty
     */
    public static boolean addEnumConstantIfNotNull(final Enumeration jEnum, final List<EnumConstant> constantsList) {
        boolean wasAdded = false;
        if (!IterableExtensions.isNullOrEmpty(constantsList)) {
            wasAdded = jEnum.getConstants().addAll(constantsList);
        }
        return wasAdded;
    }

    /**
     * Creates a java compilation unit with the given naem
     * The method automatically sets the .java FileExtension for the compilation unit name
     * There are no classifiers in the compilation unit yet.
     * @param nameWithoutFileExtension the name without .java file extension
     * @return the new compilation unit
     */
    public static CompilationUnit createEmptyCompilationUnit(final String nameWithoutFileExtension) {
        final CompilationUnit cu = ContainersFactory.eINSTANCE.createCompilationUnit();
        cu.setName((nameWithoutFileExtension + ".java"));
        return cu;
    }

    public static CompilationUnit createJavaCompilationUnitWithClassifierInPackage(final ConcreteClassifier jClassifier, final org.emftext.language.java.containers.Package jPackage) {
        final CompilationUnit compUnit = JavaContainerAndClassifierUtil.createEmptyCompilationUnit(jClassifier.getName());
        EList<ConcreteClassifier> classifiers = compUnit.getClassifiers();
        classifiers.add(jClassifier);
        compUnit.getNamespaces().addAll(JavaContainerAndClassifierUtil.getJavaPackageAsStringList(jPackage));
        return compUnit;
    }

    /**
     * Removes all classifiers of the iterator which has the same name as the given classifier classif
     * @param iter iterator of typreferences
     * @param classif classifier that should be removed from the iterator
     */
    public static void removeClassifierFromIterator(final Iterator<TypeReference> iter, final ConcreteClassifier classif) {
        while (iter.hasNext()) {
            TypeReference nextReference = iter.next();
            final Classifier type = IterableExtensions.<ClassifierReference>head(((NamespaceClassifierReference) nextReference).getClassifierReferences()).getTarget();
            boolean namesAreEqual = classif.getName().equals(type.getName());
            if (namesAreEqual) {
                iter.remove();
            }
        }
    }

    /**
     * For org.example.package it will return [org, example, package]
     * Returns empty list if jPackage is the default package.
     */
    public static List<String> getJavaPackageAsStringList(final org.emftext.language.java.containers.Package jPackage) {
        if (((jPackage == null) || StringExtensions.isNullOrEmpty(jPackage.getName()))) {
            return Collections.<String>emptyList();
        }
        final ArrayList<String> packageStringList = new ArrayList<String>();
        packageStringList.addAll(jPackage.getNamespaces());
        String name = jPackage.getName();
        packageStringList.add(name);
        return packageStringList;
    }

    public static Field getJavaAttributeByName(final org.emftext.language.java.classifiers.Class jClass, final String attributeName) {
        final Iterable<Field> candidates = Iterables.<Field>filter(jClass.getMembers(), Field.class);
        for (final Field member : candidates) {
            String name = member.getName();
            boolean namesAreEqual = Objects.equals(name, attributeName);
            if (namesAreEqual) {
                return member;
            }
        }
        return null;
    }

    public static Constructor getFirstJavaConstructor(final org.emftext.language.java.classifiers.Class jClass) {
        final Iterable<Constructor> candidates = Iterables.<Constructor>filter(jClass.getMembers(), Constructor.class);
        if (!IterableExtensions.isNullOrEmpty(candidates)) {
            return IterableExtensions.<Constructor>head(candidates);
        } else {
            return null;
        }
    }

    public static void removeJavaClassifierFromPackage(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
        final Iterator<CompilationUnit> iter = jPackage.getCompilationUnits().iterator();
        while (iter.hasNext()) {
            boolean namesAreEqual = iter.next().getName().equals(jClassifier.getName());
            if (namesAreEqual) {
                iter.remove();
            }
        }
    }

    public static File createPackageInfo(final String directory, final String packageName) {
        try {
            final File file = new File((directory + "/package-info.java"));
            file.createNewFile();
            final PrintWriter writer = new PrintWriter(file);
            writer.println((("package " + packageName) + ";"));
            writer.close();
            return file;
        } catch (Throwable e) {
            throw Exceptions.sneakyThrow(e);
        }
    }

    /**
     * Finds and retrieves a specific {@link ConcreteClassifier} that is contained in a {@link Package}.
     * The {@link ConcreteClassifier} is found by name, ignoring the capitalization of the first letter.
     * @param name is the name of the desired {@link ConcreteClassifier}, the first letter can be upper and lower case.
     * @param javaPackage is the specific {@link Package} to search in.
     * @param classifierType specifies the class of the desired {@link ConcreteClassifier}, e.g. {@link Interface}.
     * @return the found classifier, or null if there is no matching classifer.
     * @throws IllegalStateException if there are multiple classifers in the package with a matching name.
     */
    public static <T extends ConcreteClassifier> T findClassifier(final String name, final org.emftext.language.java.containers.Package javaPackage, final Class<T> classifierType) {
        final Function1<CompilationUnit, EList<ConcreteClassifier>> getClassifierList = (CompilationUnit it) -> it.getClassifiers();

        final Function1<T, Boolean> nameMatches = (T it) -> {
            String classifierNameUpper = StringExtensions.toFirstUpper(it.getName());
            String searchNameUpper = StringExtensions.toFirstUpper(name);
            return Objects.equals(classifierNameUpper, searchNameUpper);
        };

        final Iterable<T> matchingClassifiers = IterableExtensions.filter(
                Iterables.filter(
                        Iterables.concat(
                                ListExtensions.map(javaPackage.getCompilationUnits(), getClassifierList)
                        ),
                        classifierType
                ),
                nameMatches
        );

        int size = IterableExtensions.size(matchingClassifiers);
        if (size > 1) {
            throw new IllegalStateException(("Multiple matching classifers were found: " + matchingClassifiers));
        }
        return IterableExtensions.<T>head(matchingClassifiers);
    }

    public static org.emftext.language.java.containers.Package getContainingPackageFromCorrespondenceModel(final Classifier classifier, final EditableCorrespondenceModelView<?> correspondenceModel) {
        String namespace = classifier.getContainingCompilationUnit().getNamespacesAsString();
        if ((namespace.endsWith("$") || namespace.endsWith("."))) {
            int length = namespace.length();
            int minusOne = (length - 1);
            namespace = namespace.substring(0, minusOne);
        }
        final String finalNamespace = namespace;
        Iterable<org.emftext.language.java.containers.Package> packagesWithCorrespondences = Iterables.<org.emftext.language.java.containers.Package>filter(correspondenceModel.getCorrespondingEObjects(
                ContainersPackage.Literals.PACKAGE), org.emftext.language.java.containers.Package.class);

        final Function1<org.emftext.language.java.containers.Package, Boolean> packageMatchesNamespace = (org.emftext.language.java.containers.Package pack) -> {
            String namespacesAsString = pack.getNamespacesAsString();
            String name = pack.getName();
            String fullPackageName = (namespacesAsString + name);
            return finalNamespace.equals(fullPackageName);
        };

        final Iterable<org.emftext.language.java.containers.Package> packagesWithNamespace = IterableExtensions.<org.emftext.language.java.containers.Package>filter(packagesWithCorrespondences, packageMatchesNamespace);

        if (packagesWithNamespace != null && IterableExtensions.size(packagesWithNamespace) > 0 && packagesWithNamespace.iterator().next() != null) {
            return packagesWithNamespace.iterator().next();
        }
        return null;
    }

    /**
     * Returns the namespace of the compilation unit where the given object is directly or indirectly contained
     */
    protected static List<String> getJavaNamespaceForCompilationUnit(final CompilationUnit compUnit) {
        return compUnit.getNamespaces();
    }

    protected static List<String> getJavaNamespaceForConcreteClassifier(final ConcreteClassifier classifier) {
        EObject eContainer = classifier.eContainer();
        return JavaContainerAndClassifierUtil.getJavaNamespaceForCompilationUnit(((CompilationUnit) eContainer));
    }

    protected static List<String> getJavaNamespaceForNamedElement(final NamedElement element) {
        throw new IllegalArgumentException(("Unsupported type for retrieving namespace: " + element));
    }

    protected static List<String> getJavaNamespaceForVoid(final Void element) {
        throw new IllegalArgumentException(("Can not retrieve namespace for " + element));
    }

    protected static CompilationUnit getContainingCompilationUnitForConcreteClassifier(final ConcreteClassifier classifier) {
        EObject eContainer = classifier.eContainer();
        return ((CompilationUnit) eContainer);
    }

    protected static CompilationUnit getContainingCompilationUnitForMember(final Member mem) {
        EObject eContainer = mem.eContainer();
        return JavaContainerAndClassifierUtil.getContainingCompilationUnitForConcreteClassifier(((ConcreteClassifier) eContainer));
    }

    protected static CompilationUnit getContainingCompilationUnitForParameter(final Parameter param) {
        EObject eContainer = param.eContainer();
        return JavaContainerAndClassifierUtil.getContainingCompilationUnitForMember(((Member) eContainer));
    }

    protected static CompilationUnit getContainingCompilationUnitForNamedElement(final NamedElement element) {
        throw new IllegalArgumentException(("Unsupported type for retrieving compilation unit: " + element));
    }

    protected static CompilationUnit getContainingCompilationUnitForVoid(final Void element) {
        throw new IllegalArgumentException(("Can not retrieve compilation unit for " + element));
    }

    public static String getRootPackageName(final String packageName) {
        if (packageName == null) {
            return null;
        }
        String[] parts = packageName.split("\\.");
        if (parts.length > 0) {
            return parts[0];
        }
        return ""; // for empty packageName
    }

    public static String getLastPackageName(final String packageName) {
        if (packageName == null) {
            return null;
        }
        int firstDotIndex = packageName.indexOf(".");
        if (firstDotIndex < 0) {
            return packageName; // No dot found, return the whole string as per original logic
        }
        return packageName.substring(firstDotIndex + 1);
    }

    private static String buildCompilationUnitName(final String namespacesAsString, final String classifierName) {
        return ((namespacesAsString + classifierName) + ".java");
    }

    public static String getCompilationUnitName(final org.emftext.language.java.containers.Package containingPackage, final String className) {
        StringConcatenation builder = new StringConcatenation();
        if (containingPackage != null) {
            String namespacesAsString = containingPackage.getNamespacesAsString();
            builder.append(namespacesAsString);
            String name = containingPackage.getName();
            builder.append(name);
            builder.append(".");
        }
        return JavaContainerAndClassifierUtil.buildCompilationUnitName(builder.toString(), className);
    }

    public static String getCompilationUnitName(final Optional<org.emftext.language.java.containers.Package> containingPackage, final String className) {
        return JavaContainerAndClassifierUtil.getCompilationUnitName(containingPackage.orElse(null), className);
    }

    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange, final List<String> newNamespaces) {
        EList<String> currentNamespaces = elementToChange.getNamespaces();
        boolean notEquals = (!Objects.equals(newNamespaces, currentNamespaces));
        if (notEquals) {
            elementToChange.getNamespaces().clear();
            EList<String> namespacesToUpdate = elementToChange.getNamespaces();
            Iterables.<String>addAll(namespacesToUpdate, newNamespaces);
            return true;
        }
        return false;
    }

    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange, final Optional<org.emftext.language.java.containers.Package> containingPackage) {
        if (containingPackage.isPresent()) {
            return JavaContainerAndClassifierUtil.updateNamespaces(elementToChange, containingPackage.get());
        } else {
            return JavaContainerAndClassifierUtil.updateNamespaces(elementToChange, Collections.<String>emptyList());
        }
    }

    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange, final org.emftext.language.java.containers.Package containingPackage) {
        return JavaContainerAndClassifierUtil.updateNamespaces(elementToChange, JavaContainerAndClassifierUtil.getJavaPackageAsStringList(containingPackage));
    }

    public static boolean updateName(final NamedElement elementToChange, final String newName) {
        String currentName = elementToChange.getName();
        boolean notEquals = (!Objects.equals(newName, currentName));
        if (notEquals) {
            elementToChange.setName(newName);
            return true;
        }
        return false;
    }

    public static void updateCompilationUnitName(final CompilationUnit compilationUnit, final String simpleName) {
        // Use the built-in JaMoPP method to get the correctly formatted namespace string (e.g., "my.package.")
        String namespaceString = compilationUnit.getNamespacesAsString();
        compilationUnit.setName(JavaContainerAndClassifierUtil.buildCompilationUnitName(namespaceString, simpleName));
    }

    /**
     * Updates the classifier name together with the name of its compilation unit.
     */
    public static void changeNameWithCompilationUnit(final Classifier classifier, final String newName) {
        JavaContainerAndClassifierUtil.updateName(classifier, newName);
        CompilationUnit containingCompilationUnit = classifier.getContainingCompilationUnit();
        if (containingCompilationUnit != null) {
            JavaContainerAndClassifierUtil.updateCompilationUnitName(containingCompilationUnit, newName);
        }
    }

    public static boolean isInExistingLibrary(final CompilationUnit compilationUnit) {
        return URIUtil.isPathmap(compilationUnit.eResource().getURI());
    }

    public static boolean isInExistingLibrary(final Classifier classifier) {
        return JavaContainerAndClassifierUtil.isInExistingLibrary(classifier.getContainingCompilationUnit());
    }

    @XbaseGenerated
    public static List<String> getJavaNamespace(final NamedElement classifier) {
        if (classifier instanceof ConcreteClassifier) {
            return getJavaNamespaceForConcreteClassifier((ConcreteClassifier)classifier);
        } else if (classifier instanceof CompilationUnit) {
            return getJavaNamespaceForCompilationUnit((CompilationUnit)classifier);
        } else if (classifier != null) {
            return getJavaNamespaceForNamedElement(classifier);
        } else {
            return getJavaNamespaceForVoid((Void)null);
        }
    }

    @XbaseGenerated
    public static CompilationUnit getContainingCompilationUnit(final NamedElement classifier) {
        if (classifier instanceof ConcreteClassifier) {
            return getContainingCompilationUnitForConcreteClassifier((ConcreteClassifier)classifier);
        } else if (classifier instanceof Parameter) {
            return getContainingCompilationUnitForParameter((Parameter)classifier);
        } else if (classifier instanceof Member) {
            return getContainingCompilationUnitForMember((Member)classifier);
        } else if (classifier != null) {
            return getContainingCompilationUnitForNamedElement(classifier);
        } else {
            return getContainingCompilationUnitForVoid((Void)null);
        }
    }

    private JavaContainerAndClassifierUtil() {

    }
}
