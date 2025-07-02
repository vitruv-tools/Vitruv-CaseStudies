package tools.vitruv.applications.util.temporary.uml;

import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

/**
 * Util class for classifier and package functions.
 *
 */
public final class UmlClassifierAndPackageUtil {
    private static final Logger logger = Logger.getLogger(UmlClassifierAndPackageUtil.class.getSimpleName());

    /**
     * Searches and retrieves the UML package in the UML model that has the same name as the given package
     * name (case-insensitive). If more than one package with the given name exists, an
     * {@link IllegalStateException} is thrown.
     *
     * @param umlModel the UML model in which the UML packages should be searched
     * @param packageName the package name for which a matching UML package should be retrieved
     * @return the UML package or {@code null} if none could be found
     */
    public static org.eclipse.uml2.uml.Package findUmlPackage(
            final Model umlModel, final String packageName) {

        Set<org.eclipse.uml2.uml.Package> allPackages =
                IteratorExtensions.toSet(
                        Iterators.filter(umlModel.eAllContents(), org.eclipse.uml2.uml.Package.class));

        Iterable<org.eclipse.uml2.uml.Package> matchingPackages =
                IterableExtensions.filter(
                        allPackages,
                        umlPackage -> {
                            String modelPackageName = umlPackage.getName() != null
                                    ? umlPackage.getName().toLowerCase()
                                    : null;
                            String searchName = packageName != null
                                    ? packageName.toLowerCase()
                                    : null;
                            return Objects.equals(modelPackageName, searchName);
                        });

        if (IterableExtensions.isNullOrEmpty(matchingPackages)) {
            UmlClassifierAndPackageUtil.logger.warn(
                    "The UML package with the name '" + packageName + "' does not exist in the correspondence model.");
            return null;
        }

        if (IterableExtensions.size(matchingPackages) > 1) {
            throw new IllegalStateException(
                    "There is more than one package with name '" + packageName + "' in the UML model.");
        }

        return IterableExtensions.head(matchingPackages);
    }

    /**
     * Searches and retrieves the UML interface located in a specific package of a UML model that has
     * the same name as the given interface name, ignoring the capitalization of the first letter.
     * If more than one matching interface exists, an {@link IllegalStateException} is thrown.
     *
     * @param umlModel the UML model in which the UML packages should be searched
     * @param interfaceName the interface name to search for
     * @param packageName the name of the package in which the UML interface should be located
     * @return the UML interface, or {@code null} if none could be found
     */
    public static Interface findUmlInterface(
            final Model umlModel, final String interfaceName, final String packageName) {

        return UmlClassifierAndPackageUtil.findUmlType(
                umlModel, interfaceName, packageName, Interface.class);
    }


    /**
     * Searches and retrieves the UML class located in a specific package of a UML model that has
     * the same name as the given class name (ignoring capitalization of the first letter).
     * If more than one class with the given name exists, an {@link IllegalStateException} is thrown.
     *
     * @param umlModel the UML model in which the UML packages should be searched
     * @param className the class name to retrieve
     * @param packageName the package in which the UML class should be located
     * @return the UML class or {@code null} if none could be found
     */
    public static org.eclipse.uml2.uml.Class findUmlClass(
            final Model umlModel, final String className, final String packageName) {

        return UmlClassifierAndPackageUtil.findUmlType(
                umlModel, className, packageName, org.eclipse.uml2.uml.Class.class);
    }

    /**
     * Searches and retrieves the UML enumeration located in a specific package of a UML model that
     * has the same name as the given enumeration name (ignoring capitalization of the first letter).
     * If more than one enumeration with the given name exists, an {@link IllegalStateException} is thrown.
     *
     * @param umlModel the UML model in which the UML packages should be searched
     * @param enumName the enumeration name to retrieve
     * @param packageName the package in which the UML enumeration should be located
     * @return the UML enumeration or {@code null} if none could be found
     */
    public static Enumeration findUmlEnum(
            final Model umlModel, final String enumName, final String packageName) {

        return UmlClassifierAndPackageUtil.findUmlType(
                umlModel, enumName, packageName, Enumeration.class);
    }

    /**
     * Generic method to find a UML type (e.g., Class, Interface, Enumeration) by name within a specific package.
     *
     * @param umlModel the UML model containing the package
     * @param typeName the name of the type to search for
     * @param packageName the name of the package to search in
     * @param type the expected UML type class (e.g., Class.class, Interface.class)
     * @param <T> a subtype of UML {@link Type}
     * @return the UML type, or {@code null} if not found
     * @throws IllegalStateException if multiple types with the same name are found
     */
    private static <T extends Type> T findUmlType(
            final Model umlModel,
            final String typeName,
            final String packageName,
            final Class<T> type) {

        org.eclipse.uml2.uml.Package umlPackage =
                UmlClassifierAndPackageUtil.findUmlPackage(umlModel, packageName);

        if (umlPackage == null) {
            return null;
        }

        Set<T> matchingTypes =
                IterableExtensions.toSet(
                        IterableExtensions.filter(
                                Iterables.filter(umlPackage.getOwnedTypes(), type),
                                umlType -> {
                                    String actualName = StringExtensions.toFirstUpper(umlType.getName());
                                    String expectedName = StringExtensions.toFirstUpper(typeName);
                                    return Objects.equals(actualName, expectedName);
                                }));

        if (matchingTypes.size() > 1) {
            throw new IllegalStateException(
                    "There is more than one type with name '"
                            + typeName
                            + "' in the package "
                            + umlPackage);
        }

        return IterableExtensions.head(matchingTypes);
    }


    /**
     * Creates a new UML package and adds it to the given super package.
     *
     * @param name the name of the new UML package
     * @param superPackage the package to which the new package will be added
     * @return the created UML package
     */
    public static org.eclipse.uml2.uml.Package createUmlPackageAndAddToSuperPackage(
            final String name, final org.eclipse.uml2.uml.Package superPackage) {

        org.eclipse.uml2.uml.Package uPackage = UMLFactory.eINSTANCE.createPackage();
        uPackage.setName(name);
        superPackage.getPackagedElements().add(uPackage);
        return uPackage;
    }

    /**
     * Creates a simple UML class (public, not static, not abstract, no fields, no operations).
     *
     * @param uPackage the package to contain the class
     * @param name the name of the class
     * @return the created UML class
     */
    public static org.eclipse.uml2.uml.Class createSimpleUmlClass(
            final org.eclipse.uml2.uml.Package uPackage, final String name) {

        return UmlClassifierAndPackageUtil.createUmlClassAndAddToPackage(
                uPackage, name, VisibilityKind.PUBLIC_LITERAL, false, false);
    }

    /**
     * Creates a simple UML interface (public, no super interfaces, no operations, no attributes).
     *
     * @param uPackage the package to contain the interface
     * @param name the name of the interface
     * @return the created UML interface
     */
    public static Interface createSimpleUmlInterface(
            final org.eclipse.uml2.uml.Package uPackage, final String name) {

        return UmlClassifierAndPackageUtil.createUmlInterfaceAndAddToPackage(
                uPackage, name, null);
    }

    /**
     * Creates and returns a new UML class, and adds it to the given package.
     *
     * @param uPackage the package that will contain the new class
     * @param name the name of the new class
     * @param visibility the visibility of the class
     * @param abstr whether the class should be abstract
     * @param fin whether the class should be final
     * @return the created UML class
     */
    public static org.eclipse.uml2.uml.Class createUmlClassAndAddToPackage(
            final org.eclipse.uml2.uml.Package uPackage,
            final String name,
            final VisibilityKind visibility,
            final boolean abstr,
            final boolean fin) {

        org.eclipse.uml2.uml.Class uClass =
                UmlClassifierAndPackageUtil.createUmlClass(name, visibility, abstr, fin);
        uPackage.getPackagedElements().add(uClass);
        return uClass;
    }


    private UmlClassifierAndPackageUtil() {
        // Utility class; prevent instantiation
    }

    /**
     * Creates and returns a new UML interface and adds it to the given package. Visibility is public
     * by default.
     */
    public static Interface createUmlInterfaceAndAddToPackage(
            final org.eclipse.uml2.uml.Package uPackage,
            final String name,
            final List<Interface> superInterfaces) {

        Interface uInterface = createUmlInterface(name, superInterfaces);
        uPackage.getPackagedElements().add(uInterface);
        return uInterface;
    }

    /** Creates and returns a new UML data type and adds it to the given package. */
    public static DataType createUmlDataType(
            final org.eclipse.uml2.uml.Package uPackage, final String name) {

        DataType dataType = UMLFactory.eINSTANCE.createDataType();
        dataType.setName(name);
        uPackage.getPackagedElements().add(dataType);
        return dataType;
    }

    /** Creates and returns a new UML enumeration and adds it to the given package. */
    public static Enumeration createUmlEnumAndAddToPackage(
            final org.eclipse.uml2.uml.Package uPackage,
            final String name,
            final VisibilityKind visibility,
            final List<EnumerationLiteral> enumLiterals) {

        Enumeration uEnum = createUmlEnum(name, visibility, enumLiterals);
        uPackage.getPackagedElements().add(uEnum);
        return uEnum;
    }

    /** Creates and returns a UML enumeration (not added to any package). */
    public static Enumeration createUmlEnum(
            final String name,
            final VisibilityKind visibility,
            final List<EnumerationLiteral> enumLiterals) {

        Enumeration uEnum = UMLFactory.eINSTANCE.createEnumeration();
        setName(uEnum, name);
        uEnum.setVisibility(visibility);

        if (!IterableExtensions.isNullOrEmpty(enumLiterals)) {
            uEnum.getOwnedLiterals().addAll(enumLiterals);
        }

        return uEnum;
    }

    /** Creates a UML enumeration literal with the given name. */
    public static EnumerationLiteral createUmlEnumerationLiteral(final String name) {
        EnumerationLiteral literal = UMLFactory.eINSTANCE.createEnumerationLiteral();
        literal.setName(name);
        return literal;
    }

    /** Creates multiple enumeration literals from a list of names. */
    public static List<EnumerationLiteral> createUmlEnumLiteralsFromList(
            final List<String> enumLiteralNames) {

        List<EnumerationLiteral> enumLiterals = new ArrayList<>();
        for (String name : enumLiteralNames) {
            enumLiterals.add(createUmlEnumerationLiteral(name));
        }
        return enumLiterals;
    }

    /** Creates and returns a UML class (not added to any package). */
    public static org.eclipse.uml2.uml.Class createUmlClass(
            final String name,
            final VisibilityKind visibility,
            final boolean abstr,
            final boolean fin) {

        org.eclipse.uml2.uml.Class uClass = UMLFactory.eINSTANCE.createClass();
        setName(uClass, name);
        uClass.setVisibility(visibility);
        uClass.setIsAbstract(abstr);
        uClass.setIsFinalSpecialization(fin);
        return uClass;
    }

    /** Creates a UML interface (not added to a package). */
    public static Interface createUmlInterface(
            final String name, final List<Interface> superInterfaces) {

        Interface uInterface = UMLFactory.eINSTANCE.createInterface();
        setName(uInterface, name);
        uInterface.setVisibility(VisibilityKind.PUBLIC_LITERAL);

        if (!IterableExtensions.isNullOrEmpty(superInterfaces)) {
            uInterface.getGenerals().addAll(superInterfaces);
        }

        return uInterface;
    }

    /** Creates and adds a primitive type to the given package. */
    public static PrimitiveType createUmlPrimitiveTypeAndAddToModel(
            final org.eclipse.uml2.uml.Package uPackage, final String pTypeName) {

        PrimitiveType pType = createUmlPrimitiveType(pTypeName);
        uPackage.getPackagedElements().add(pType);
        return pType;
    }

    /** Creates a primitive type (not added to a package). */
    public static PrimitiveType createUmlPrimitiveType(final String name) {
        PrimitiveType pType = UMLFactory.eINSTANCE.createPrimitiveType();
        setName(pType, name);
        return pType;
    }

    /** Extracts superinterfaces from the given UML interface. */
    public static EList<Classifier> extractSuperInterfaces(final Interface umlInterface) {
        if (umlInterface == null) {
            return null;
        }

        EList<Classifier> supers = umlInterface.getGenerals();
        return IterableExtensions.isNullOrEmpty(supers) ? null : supers;
    }

    /** Adds a super classifier to a given subclassifier. */
    public static boolean addUmlSuperClassifier(
            final Classifier subClassifier, final Classifier superClassifier) {

        if (subClassifier == null || superClassifier == null) {
            throw new IllegalArgumentException("Cannot create generalization relation for null");
        }

        return subClassifier.getGenerals().add(superClassifier);
    }

    /** Adds an interface realization to the given implementor. */
    public static InterfaceRealization addUmlInterfaceRealization(
            final BehavioredClassifier implementor,
            final String realizationName,
            final Interface interfaceToImplement) {

        if (implementor == null || interfaceToImplement == null) {
            throw new IllegalArgumentException("Cannot create implementation relation for null");
        }

        return implementor.createInterfaceRealization(realizationName, interfaceToImplement);
    }

    /** Removes a general classifier from a sub-classifier. */
    public static void removeUmlGeneralClassifier(
            final Classifier subClassifier, final Classifier classifierToRemove) {

        if (subClassifier == null || classifierToRemove == null) {
            throw new IllegalArgumentException("Cannot remove generalization relation for null");
        }

        Iterator<Generalization> iter = subClassifier.getGeneralizations().iterator();
        while (iter.hasNext()) {
            if (iter.next().getGeneral().getName().equals(classifierToRemove.getName())) {
                iter.remove();
            }
        }
    }

    /** Removes an implemented interface from a UML class. */
    public static void removeUmlImplementedInterface(
            final org.eclipse.uml2.uml.Class implementor, final Interface interfaceToRemove) {

        if (implementor == null || interfaceToRemove == null) {
            throw new IllegalArgumentException("Cannot remove implementation relation for null");
        }

        Iterator<InterfaceRealization> iter = implementor.getInterfaceRealizations().iterator();
        while (iter.hasNext()) {
            if (iter.next().getContract().getName().equals(interfaceToRemove.getName())) {
                iter.remove();
            }
        }
    }

    /** Converts the parent namespace of a UML namespace into a list of string segments. */
    public static List<String> getUmlParentNamespaceAsStringList(final Namespace uNamespace) {
        Namespace parent = uNamespace.getNamespace();
        if (parent instanceof Model) {
            return Collections.emptyList();
        }

        return buildNamespaceStringList(parent, new ArrayList<>());
    }

    /** Converts a UML namespace into a list of string segments. */
    public static List<String> getUmlNamespaceAsStringList(final Namespace uNamespace) {
        return buildNamespaceStringList(uNamespace, new ArrayList<>());
    }

    /** Converts a UML namespace into a dot-separated string. */
    public static String getUmlNamespaceAsString(final Namespace uNamespace) {
        return IterableExtensions.join(getUmlNamespaceAsStringList(uNamespace), ".");
    }

    /** Removes a packaged element from a given package. */
    public static void removePackagedElementFromPackage(
            final org.eclipse.uml2.uml.Package uPackage, final PackageableElement packageable) {

        Iterator<PackageableElement> iter = uPackage.getPackagedElements().iterator();
        while (iter.hasNext()) {
            if (iter.next().getName().equals(packageable.getName())) {
                iter.remove();
            }
        }
    }

    /** Internal helper: sets the name of a named element. */
    static void setName(final NamedElement namedElement, final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Invalid name: " + name + " for " + namedElement);
        }
        namedElement.setName(name);
    }

    /** Internal recursive builder for namespace lists. */
    private static List<String> buildNamespaceStringList(
            final Namespace uNamespace, final List<String> namespace) {

        namespace.add(uNamespace != null ? uNamespace.getName() : null);

        Namespace parent = uNamespace != null ? uNamespace.getNamespace() : null;
        if (parent != null && !(parent instanceof Model)) {
            return buildNamespaceStringList(parent, namespace);
        } else {
            return ListExtensions.reverse(namespace);
        }
    }
}
