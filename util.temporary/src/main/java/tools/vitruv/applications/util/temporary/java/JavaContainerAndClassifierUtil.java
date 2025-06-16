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
        EList<String> _namespaces = jPackage.getNamespaces();
        List<String> _javaPackageAsStringList = JavaContainerAndClassifierUtil.getJavaPackageAsStringList(containingPackage);
        Iterables.<String>addAll(_namespaces, _javaPackageAsStringList);
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
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(superInterfaces);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
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
        boolean _xifexpression = false;
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(constantsList);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
            _xifexpression = jEnum.getConstants().addAll(constantsList);
        }
        return _xifexpression;
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
        EList<ConcreteClassifier> _classifiers = compUnit.getClassifiers();
        _classifiers.add(jClassifier);
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
            {
                TypeReference _next = iter.next();
                final Classifier type = IterableExtensions.<ClassifierReference>head(((NamespaceClassifierReference) _next).getClassifierReferences()).getTarget();
                boolean _equals = classif.getName().equals(type.getName());
                if (_equals) {
                    iter.remove();
                }
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
        String _name = jPackage.getName();
        packageStringList.add(_name);
        return packageStringList;
    }

    public static Field getJavaAttributeByName(final org.emftext.language.java.classifiers.Class jClass, final String attributeName) {
        final Iterable<Field> candidates = Iterables.<Field>filter(jClass.getMembers(), Field.class);
        for (final Field member : candidates) {
            String _name = member.getName();
            boolean _equals = Objects.equals(_name, attributeName);
            if (_equals) {
                return member;
            }
        }
        return null;
    }

    public static Constructor getFirstJavaConstructor(final org.emftext.language.java.classifiers.Class jClass) {
        final Iterable<Constructor> candidates = Iterables.<Constructor>filter(jClass.getMembers(), Constructor.class);
        boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(candidates);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
            return IterableExtensions.<Constructor>head(candidates);
        } else {
            return null;
        }
    }

    public static void removeJavaClassifierFromPackage(final org.emftext.language.java.containers.Package jPackage, final ConcreteClassifier jClassifier) {
        final Iterator<CompilationUnit> iter = jPackage.getCompilationUnits().iterator();
        while (iter.hasNext()) {
            boolean _equals = iter.next().getName().equals(jClassifier.getName());
            if (_equals) {
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
        } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
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
        final Function1<CompilationUnit, EList<ConcreteClassifier>> _function = (CompilationUnit it) -> {
            return it.getClassifiers();
        };
        final Function1<T, Boolean> _function_1 = (T it) -> {
            String _firstUpper = StringExtensions.toFirstUpper(it.getName());
            String _firstUpper_1 = StringExtensions.toFirstUpper(name);
            return Boolean.valueOf(Objects.equals(_firstUpper, _firstUpper_1));
        };
        final Iterable<T> matchingClassifiers = IterableExtensions.<T>filter(Iterables.<T>filter((Iterables.<ConcreteClassifier>concat(ListExtensions.<CompilationUnit, EList<ConcreteClassifier>>map(javaPackage.getCompilationUnits(), _function))), classifierType), _function_1);
        int _size = IterableExtensions.size(matchingClassifiers);
        boolean _greaterThan = (_size > 1);
        if (_greaterThan) {
            throw new IllegalStateException(("Multiple matching classifers were found: " + matchingClassifiers));
        }
        return IterableExtensions.<T>head(matchingClassifiers);
    }

    public static org.emftext.language.java.containers.Package getContainingPackageFromCorrespondenceModel(final Classifier classifier, final EditableCorrespondenceModelView<?> correspondenceModel) {
        String namespace = classifier.getContainingCompilationUnit().getNamespacesAsString();
        if ((namespace.endsWith("$") || namespace.endsWith("."))) {
            int _length = namespace.length();
            int _minus = (_length - 1);
            namespace = namespace.substring(0, _minus);
        }
        final String finalNamespace = namespace;
        Iterable<org.emftext.language.java.containers.Package> packagesWithCorrespondences = Iterables.<org.emftext.language.java.containers.Package>filter(correspondenceModel.getCorrespondingEObjects(
                ContainersPackage.Literals.PACKAGE), org.emftext.language.java.containers.Package.class);
        final Function1<org.emftext.language.java.containers.Package, Boolean> _function = (org.emftext.language.java.containers.Package pack) -> {
            String _namespacesAsString = pack.getNamespacesAsString();
            String _name = pack.getName();
            String _plus = (_namespacesAsString + _name);
            return Boolean.valueOf(finalNamespace.equals(_plus));
        };
        final Iterable<org.emftext.language.java.containers.Package> packagesWithNamespace = IterableExtensions.<org.emftext.language.java.containers.Package>filter(packagesWithCorrespondences, _function);
        if ((((null != packagesWithNamespace) && (0 < IterableExtensions.size(packagesWithNamespace))) &&
                (null != packagesWithNamespace.iterator().next()))) {
            return packagesWithNamespace.iterator().next();
        }
        return null;
    }

    /**
     * Returns the namespace of the compilation unit where the given object is directly or indirectly contained
     */
    protected static List<String> _getJavaNamespace(final CompilationUnit compUnit) {
        return compUnit.getNamespaces();
    }

    protected static List<String> _getJavaNamespace(final ConcreteClassifier classifier) {
        EObject _eContainer = classifier.eContainer();
        return JavaContainerAndClassifierUtil.getJavaNamespace(((CompilationUnit) _eContainer));
    }

    protected static List<String> _getJavaNamespace(final NamedElement element) {
        throw new IllegalArgumentException(("Unsupported type for retrieving namespace: " + element));
    }

    protected static List<String> _getJavaNamespace(final Void element) {
        throw new IllegalArgumentException(("Can not retrieve namespace for " + element));
    }

    protected static CompilationUnit _getContainingCompilationUnit(final ConcreteClassifier classifier) {
        EObject _eContainer = classifier.eContainer();
        return ((CompilationUnit) _eContainer);
    }

    protected static CompilationUnit _getContainingCompilationUnit(final Member mem) {
        EObject _eContainer = mem.eContainer();
        return JavaContainerAndClassifierUtil.getContainingCompilationUnit(((ConcreteClassifier) _eContainer));
    }

    protected static CompilationUnit _getContainingCompilationUnit(final Parameter param) {
        EObject _eContainer = param.eContainer();
        return JavaContainerAndClassifierUtil.getContainingCompilationUnit(((Member) _eContainer));
    }

    protected static CompilationUnit _getContainingCompilationUnit(final NamedElement element) {
        throw new IllegalArgumentException(("Unsupported type for retrieving compilation unit: " + element));
    }

    protected static CompilationUnit _getContainingCompilationUnit(final Void element) {
        throw new IllegalArgumentException(("Can not retrieve compilation unit for " + element));
    }

    public static String getRootPackageName(final String packageName) {
        String[] _split = null;
        if (packageName!=null) {
            _split=packageName.split("\\.");
        }
        String _get = null;
        if (_split!=null) {
            _get=_split[0];
        }
        return _get;
    }

    public static String getLastPackageName(final String packageName) {
        String _substring = null;
        if (packageName!=null) {
            int _indexOf = packageName.indexOf(".");
            int _plus = (_indexOf + 1);
            _substring=packageName.substring(_plus);
        }
        return _substring;
    }

    private static String getCompilationUnitName(final String namespacesAsString, final String classifierName) {
        return ((namespacesAsString + classifierName) + ".java");
    }

    public static String getCompilationUnitName(final org.emftext.language.java.containers.Package containingPackage, final String className) {
        StringConcatenation _builder = new StringConcatenation();
        {
            if ((containingPackage != null)) {
                String _namespacesAsString = containingPackage.getNamespacesAsString();
                _builder.append(_namespacesAsString);
                String _name = containingPackage.getName();
                _builder.append(_name);
                _builder.append(".");
            }
        }
        return JavaContainerAndClassifierUtil.getCompilationUnitName(_builder.toString(), className);
    }

    public static String getCompilationUnitName(final Optional<org.emftext.language.java.containers.Package> containingPackage, final String className) {
        org.emftext.language.java.containers.Package _xifexpression = null;
        boolean _isPresent = containingPackage.isPresent();
        if (_isPresent) {
            _xifexpression = containingPackage.get();
        } else {
            _xifexpression = null;
        }
        return JavaContainerAndClassifierUtil.getCompilationUnitName(_xifexpression, className);
    }

    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange, final List<String> newNamespaces) {
        EList<String> _namespaces = elementToChange.getNamespaces();
        boolean _notEquals = (!Objects.equals(newNamespaces, _namespaces));
        if (_notEquals) {
            elementToChange.getNamespaces().clear();
            EList<String> _namespaces_1 = elementToChange.getNamespaces();
            Iterables.<String>addAll(_namespaces_1, newNamespaces);
            return true;
        }
        return false;
    }

    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange, final Optional<org.emftext.language.java.containers.Package> containingPackage) {
        boolean _xifexpression = false;
        boolean _isPresent = containingPackage.isPresent();
        if (_isPresent) {
            _xifexpression = JavaContainerAndClassifierUtil.updateNamespaces(elementToChange, containingPackage.get());
        } else {
            _xifexpression = JavaContainerAndClassifierUtil.updateNamespaces(elementToChange, Collections.<String>emptyList());
        }
        return _xifexpression;
    }

    public static boolean updateNamespaces(final NamespaceAwareElement elementToChange, final org.emftext.language.java.containers.Package containingPackage) {
        return JavaContainerAndClassifierUtil.updateNamespaces(elementToChange, JavaContainerAndClassifierUtil.getJavaPackageAsStringList(containingPackage));
    }

    public static boolean updateName(final NamedElement elementToChange, final String newName) {
        String _name = elementToChange.getName();
        boolean _notEquals = (!Objects.equals(newName, _name));
        if (_notEquals) {
            elementToChange.setName(newName);
            return true;
        }
        return false;
    }

    public static void updateCompilationUnitName(final CompilationUnit compilationUnit, final String simpleName) {
        final Function1<String, CharSequence> _function = (String it) -> {
            return it;
        };
        compilationUnit.setName(JavaContainerAndClassifierUtil.getCompilationUnitName(IterableExtensions.<String>join(compilationUnit.getNamespaces(), "", ".", ".", _function), simpleName));
    }

    /**
     * Updates the classifier name together with the name of its compilation unit.
     */
    public static void changeNameWithCompilationUnit(final Classifier classifier, final String newName) {
        JavaContainerAndClassifierUtil.updateName(classifier, newName);
        CompilationUnit _containingCompilationUnit = classifier.getContainingCompilationUnit();
        if (_containingCompilationUnit!=null) {
            JavaContainerAndClassifierUtil.updateCompilationUnitName(_containingCompilationUnit, newName);
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
            return _getJavaNamespace((ConcreteClassifier)classifier);
        } else if (classifier instanceof CompilationUnit) {
            return _getJavaNamespace((CompilationUnit)classifier);
        } else if (classifier != null) {
            return _getJavaNamespace(classifier);
        } else {
            return _getJavaNamespace((Void)null);
        }
    }

    @XbaseGenerated
    public static CompilationUnit getContainingCompilationUnit(final NamedElement classifier) {
        if (classifier instanceof ConcreteClassifier) {
            return _getContainingCompilationUnit((ConcreteClassifier)classifier);
        } else if (classifier instanceof Parameter) {
            return _getContainingCompilationUnit((Parameter)classifier);
        } else if (classifier instanceof Member) {
            return _getContainingCompilationUnit((Member)classifier);
        } else if (classifier != null) {
            return _getContainingCompilationUnit(classifier);
        } else {
            return _getContainingCompilationUnit((Void)null);
        }
    }

    private JavaContainerAndClassifierUtil() {

    }
}
