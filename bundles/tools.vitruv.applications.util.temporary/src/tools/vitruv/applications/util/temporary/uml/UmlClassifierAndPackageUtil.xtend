package tools.vitruv.applications.util.temporary.uml

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.ArrayList
import java.util.Collections
import java.util.List
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.BehavioredClassifier
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.DataType
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.EnumerationLiteral
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Namespace
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind

/**
 * Util class for classifier and package functions.
 * 
 * @author Fei
 * 
 */
@Utility
class UmlClassifierAndPackageUtil {
    static val logger = Logger.getLogger(UmlClassifierAndPackageUtil.simpleName)

    /**
     * Searches and retrieves the UML package in the UML model that has an equal name as the given package name.
     * If there is more than one package with the given name, an {@link IllegalStateException} is thrown.
     * @param umlModel the UML model Model in which the UML packages should be searched
     * @param packageName the package name for which a fitting UML package should be retrieved
     * @return the UML package or null if none could be found
     */
    def static Package findUmlPackage(Model umlModel, String packageName) {
        val Set<Package> allPackages = umlModel.eAllContents.filter(Package).toSet
        val packages = allPackages.filter[name == packageName]
        if (packages.nullOrEmpty) {
            logger.warn("The UML-Package with the name " + packageName + " does not exist in the correspondence model")
            return null
        }
        if (packages.size > 1) {
            throw new IllegalStateException("There is more than one package with name " + packageName + " in the UML model.")
        }
        return packages.head
    }

    /**
     * Searches and retrieves the UML interface located in a specific package of a UML model that has an equal name as the given package name (ignoring the capitalization of the first letter).
     * If there is more than one package with the given name an {@link IllegalStateException} is thrown.
     * @param umlModel the UML model Model in which the UML packages should be searched
     * @param interfaceName the interface name for which a fitting UML interface should be retrieved
     * @param packageName the package name in which the UML interface should be located.
     * @return the UML interface or null if none could be found
     */
    def static Interface findUmlInterface(Model umlModel, String interfaceName, String packageName) {
        return umlModel.findUmlType(interfaceName, packageName, Interface)
    }

    /**
     * Searches and retrieves the UML class located in a specific package of a UML model that has an equal name as the given package name (ignoring the capitalization of the first letter).
     * If there is more than one package with the given name an {@link IllegalStateException} is thrown.
     * 
     * @param umlModel the UML model Model in which the UML packages should be searched
     * @param className the class name for which a fitting UML class should be retrieved
     * @param packageName the package name in which the UML class should be located.
     * @return the UML class or null if none could be found
     */
    def static Class findUmlClass(Model umlModel, String className, String packageName) {
        return umlModel.findUmlType(className, packageName, Class)
    }

    def private static <T extends Type> findUmlType(Model umlModel, String typeName, String packageName, java.lang.Class<T> type) {
        val uPackage = umlModel.findUmlPackage(packageName)
        if (uPackage === null) {
            return null
        }
        val types = uPackage.ownedTypes.filter(type).filter[name.toFirstUpper == typeName.toFirstUpper].toSet
        if (types.size > 1) {
            throw new IllegalStateException("There is more than one type with name " + typeName + " in the package " + uPackage)
        }
        return types.head
    }

    def static Package createUmlPackageAndAddToSuperPackage(String name, Package superPackage) {
        val uPackage = UMLFactory.eINSTANCE.createPackage
        uPackage.name = name
        superPackage.packagedElements += uPackage
        return uPackage
    }

    /**
     * Creates a simple Uml CLass (public, not static, not abstract, no fields, no operations)
     */
    def static createSimpleUmlClass(Package uPackage, String name) {
        return createUmlClassAndAddToPackage(uPackage, name, VisibilityKind.PUBLIC_LITERAL, false, false)
    }

    /**
     * Creates a simple uml interface (public, no super interfaces, no operations no attributes)
     */
    def static createSimpleUmlInterface(Package uPackage, String name) {
        return createUmlInterfaceAndAddToPackage(uPackage, name, null)
    }

    /**
     * Creates and returns a new Uml Class. It is added to the given package.
     * @param uPackage the package that contains the new class
     * @param name the name of the new class
     * @param visibilty the visibility of the class
     * @param abstr if the class should be abstract
     * @param fin if the class should be final
     * @return the new uml class
     */
    def static Class createUmlClassAndAddToPackage(Package uPackage, String name, VisibilityKind visibility, boolean abstr, boolean fin) {
        val uClass = createUmlClass(name, visibility, abstr, fin)
        uPackage.packagedElements += uClass
        return uClass
    }

    /**
     * Creates and returns a new Uml interface. It is added to the given package.
     * The visibility of the new intrface is public by default.
     * 
     * @param uPackage the package that contains the new interface
     * @param name the name of the new interface
     * @param superInterfaces super interfaces of the new interface
     * @return the new uml interface
     */
    def static Interface createUmlInterfaceAndAddToPackage(Package uPackage, String name, List<Interface> superInterfaces) {
        val uInterface = createUmlInterface(name, superInterfaces)
        uPackage.packagedElements += uInterface
        return uInterface
    }

    /**
     * Creates and returns a new Uml datatype. It is added to the given package.
     * 
     * @param uPackage the package that contains the new datatype
     * @param name the name of the new datatype
     * @return the new datatype
     */
    def static DataType createUmlDataType(Package uPackage, String name) {
        val dataType = UMLFactory.eINSTANCE.createDataType
        dataType.name = name
        uPackage.packagedElements += dataType
        return dataType
    }

    /**
     * Creates and returns a new Uml enumeration. It is added to the given package.
     * 
     * @param uPackage the package that contains the new enum
     * @param name the name of the new enum
     * @param visibility the visibility of the enum
     * @param enumLiterals the enum constants of the new enum
     * @return the new enum
     */
    def static Enumeration createUmlEnumAndAddToPackage(Package uPackage, String name, VisibilityKind visibility, List<EnumerationLiteral> enumLiterals) {
        val uEnum = createUmlEnum(name, visibility, enumLiterals)
        uPackage.packagedElements += uEnum
        return uEnum
    }

    /**
     * Creates and returns a new Uml enumeration.
     * It is not contained in any package.
     * 
     * @param name the name of the new enum
     * @param visibility the visibility of the enum
     * @param enumLiterals the enum constants of the new enum
     * @return the new enum
     */
    def static Enumeration createUmlEnum(String name, VisibilityKind visibility, List<EnumerationLiteral> enumLiterals) {
        val uEnum = UMLFactory.eINSTANCE.createEnumeration
        setName(uEnum, name)
        uEnum.visibility = visibility
        if (!enumLiterals.nullOrEmpty) {
            uEnum.ownedLiterals.addAll(enumLiterals)
        }
        return uEnum
    }

    /**
     * Creates a enum literal with the given name.
     * The enum literal is not contained in an enum.
     * 
     * @param name the name of the enum literal
     * @return the new enum literal
     */
    def static EnumerationLiteral createUmlEnumerationLiteral(String name) {
        val literal = UMLFactory.eINSTANCE.createEnumerationLiteral
        literal.name = name
        return literal
    }

    /**
     * Creates for each name in the given enumLiteralNames a new enum literal object.
     * @enumLiteralName list of enum literal names
     * @return the list with the new enum literals
     */
    def static List<EnumerationLiteral> createUmlEnumLiteralsFromList(List<String> enumLiteralNames) {
        val enumLiterals = new ArrayList<EnumerationLiteral>
        for (name : enumLiteralNames) {
            enumLiterals += createUmlEnumerationLiteral(name)
        }
        return enumLiterals
    }

    /**
     * Creates and returns a Uml-Class.
     * The class is not contained in a package.
     * 
     * @param name the name of the new class
     * @param visibilty the visibility of the class
     * @param abstr if the class should be abstract
     * @param fin if the class should be final
     * @return the new uml class
     */
    def static Class createUmlClass(String name, VisibilityKind visibility, boolean abstr, boolean fin) {
        val uClass = UMLFactory.eINSTANCE.createClass
        setName(uClass, name)
        uClass.visibility = visibility
        uClass.isAbstract = abstr
        uClass.isFinalSpecialization = fin
        return uClass
    }

    /**
     * Creates and returns an Interface.
     * Visibility is automatically set to public.
     * SuperInterfaces can be null.
     * The interface is not contained in a package.
     * @param uPackage the package that contains the new interface
     * @param name the name of the new interface
     * @param superInterfaces super interfaces of the new interface
     * @return the new uml interface
     */
    def static Interface createUmlInterface(String name, List<Interface> superInterfaces) {
        val uInterface = UMLFactory.eINSTANCE.createInterface
        setName(uInterface, name)
        uInterface.visibility = VisibilityKind.PUBLIC_LITERAL
        if (!superInterfaces.nullOrEmpty) {
            uInterface.generals.addAll(superInterfaces)
        }
        return uInterface
    }

    /**
     * Creates and returns a PrimitiveType with the given name. It is added to the given package.
     * @param uPackage the package that contains the new primitive type
     * @param pTypeName the name of the new primitive type
     * @return a new primitive type with the given name
     */
    def static PrimitiveType createUmlPrimitiveTypeAndAddToModel(Package uPackage, String pTypeName) {
        val pType = createUmlPrimitiveType(pTypeName)
        uPackage.packagedElements += pType
        return pType
    }

    /**
     * Returns a PrimitiveType with the given name. It is not contained in a package.
     * @param name the name of the new primitive type
     * @return a new primitive type with the given name
     */
    def static createUmlPrimitiveType(String name) {
        val pType = UMLFactory.eINSTANCE.createPrimitiveType
        setName(pType, name)
        return pType
    }

    /**
     * Extracts the list of superinterfaces of umlInterface. Returns null if umlInterface has no
     * superinterfaces.
     * @return the list of supper interfaces of the given umlInterface
     */
    def static EList<Classifier> extractSuperInterfaces(Interface umlInterface) {
        val supers = umlInterface?.generals
        if (supers.nullOrEmpty) {
            return null
        }
        return supers
    }

    /**
     * Adds the superclassifier to the given subclassifier
     * @param subClassifier the classifier who inherits the super clsssifier
     * @param super classifier the new super classifier of the sub classifier
     */
    def static addUmlSuperClassifier(Classifier subClassifier, Classifier superClassifier) {
        if (subClassifier === null || superClassifier === null) {
            throw new IllegalArgumentException("Can not create generalization relation for null")
        }
        subClassifier.generals += superClassifier
    }

    /**
     * Adds a new interface realization of the given interface to the implementor
     * @param implementor the classifier that implements the interface
     * @param realizationName the name of the interface implementation relation
     * @param interfaceToImplement the interface that the implementor implements
     */
    def static addUmlInterfaceRealization(BehavioredClassifier implementor, String realizationName, Interface interfaceToImplement) {
        if (implementor === null || interfaceToImplement === null) {
            throw new IllegalArgumentException("Can not create implementation relation for null")
        }
        implementor.createInterfaceRealization(realizationName, interfaceToImplement)
    }

    /**
     * Removes a super classifier from a given sub classifier
     * @param classifierToRemove the classifier that should be removed as super classifier of sub classifier
     */
    def static removeUmlGeneralClassifier(Classifier subClassifier, Classifier classifierToRemove) {
        if (subClassifier === null || classifierToRemove === null) {
            throw new IllegalArgumentException("Can not remove generalization relation for null")
        }
        val iter = subClassifier.generalizations.iterator
        while (iter.hasNext) {
            if (iter.next.general.name.equals(classifierToRemove.name)) {
                iter.remove
            }
        }
    }

    /**
     * Removes a implemented interface from an implementor
     */
    def static removeUmlImplementedInterface(Class implementor, Interface interfaceToRemove) {
        if (implementor === null || interfaceToRemove === null) {
            throw new IllegalArgumentException("Can not remove class generalization relation for null")
        }
        val iter = implementor.interfaceRealizations.iterator
        while (iter.hasNext) {
            if (iter.next.contract.name.equals(interfaceToRemove.name)) {
                iter.remove
            }
        }
    }

    /**
     * Converts the namespace of the given namespace into a list of Strings:
     * org.example.test.test2 --> [org, example, test]
     * If the given namepace is a uml model (org.eclipse.uml2.uml.Model), it will return
     * an empty list
     */
    def static List<String> getUmlParentNamespaceAsStringList(Namespace uNamespace) {
        if (!(uNamespace.namespace instanceof Model)) {
            return buildNamespaceStringList(uNamespace.namespace, new ArrayList<String>)
        } else {
            return Collections.<String>emptyList
        }

    }

    /**
     * Converts the namespace of the given namespace into a list of Strings
     * and appends the name of the given namespace to the end of the list
     * org.example.test.test2 --> [org, example, test, test2]
     */
    def static List<String> getUmlNamespaceAsStringList(Namespace uNamespace) {
        return buildNamespaceStringList(uNamespace, new ArrayList<String>)
    }

    /**
     * Converts the namespace of the given namespace into a String
     * and appends the name of the given namespace to the end of the String
     * org.example.test.test2 --> "org.example.test.test2"
     */
    def static String getUmlNamespaceAsString(Namespace uNamespace) {
        return uNamespace.getUmlNamespaceAsStringList.join(".")
    }

    /**
     * Removes the given packageable element from the given package.
     * @param uPackage the package from which the packageable element should be removed
     * @param packageable the packageable element that should be removed from the fiven package
     */
    def static removePackagedElementFromPackage(Package uPackage, PackageableElement packageable) {
        val iter = uPackage.packagedElements.iterator
        while (iter.hasNext) {
            if (iter.next.name.equals(packageable.name)) {
                iter.remove
            }
        }
    }

    def package static setName(NamedElement namedElement, String name) {
        if (name === null) {
            throw new IllegalArgumentException("Invalid name: " + name + " for " + namedElement)
        }
        namedElement.name = name
    }

    def private static List<String> buildNamespaceStringList(Namespace uNamespace, List<String> namespace) {
        namespace += uNamespace?.name
        if (uNamespace?.namespace !== null && !(uNamespace?.namespace instanceof Model)) {
            return buildNamespaceStringList(uNamespace.namespace, namespace)
        } else {
            return namespace.reverse
        }
    }
}
