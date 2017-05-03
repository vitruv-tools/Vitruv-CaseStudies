package tools.vitruv.applications.umljava.util.uml

import java.util.ArrayList
import java.util.Iterator
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Enumeration
import org.eclipse.uml2.uml.EnumerationLiteral
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Namespace
import org.eclipse.uml2.uml.PackageableElement
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Class

class UmlClassifierAndPackageUtil {
    
    private static val logger = Logger.getLogger(UmlClassifierAndPackageUtil.simpleName)
    
    /**
     * Prevents instantiation
     */
    private new () {}
    
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
        return createUmlClassAndAddToPackage(uPackage, name, VisibilityKind.PUBLIC_LITERAL, false, false);
    }
    
    def static createSimpleUmlInterface(Package uPackage, String name) {
        return createUmlInterfaceAndAddToPackage(uPackage, name, null);
    }
    
    /**
     * Creates and returns a Uml Class. It is added the package.
     */
    def static Class createUmlClassAndAddToPackage(Package uPackage, String name, VisibilityKind visibility, boolean abstr, boolean fin) {
        val uClass = createUmlClass(name, visibility, abstr, fin)
        uPackage.packagedElements += uClass
        return uClass
    }
    
    /**
     * Creates and returns a Uml Interface. It is added the package.
     */
    def static Interface createUmlInterfaceAndAddToPackage(Package uPackage, String name, List<Interface> superInterfaces) {
        val uInterface = createUmlInterface(name, superInterfaces)
        uPackage.packagedElements += uInterface
        return uInterface
    }
    
    def static Enumeration createUmlEnumAndAddToPackage(Package uPackage, String name, VisibilityKind visibility, List<EnumerationLiteral> enumLiterals) {
        val uEnum = createUmlEnum(name, visibility, enumLiterals)
        uPackage.packagedElements += uEnum
        return uEnum
    }
    
    def static Enumeration createUmlEnum(String name, VisibilityKind visibility, List<EnumerationLiteral> enumLiterals) {
        val uEnum = UMLFactory.eINSTANCE.createEnumeration
        setName(uEnum, name)
        uEnum.visibility = visibility
        if (!enumLiterals.nullOrEmpty) {
            uEnum.ownedLiterals.addAll(enumLiterals)
        }
        return uEnum
    }
    
    def static List<EnumerationLiteral> createUmlEnumLiteralsFromList(List<String> enumLiteralNames) {
        val enumLiterals = new ArrayList<EnumerationLiteral>
        for (name : enumLiteralNames) {
            val literal = UMLFactory.eINSTANCE.createEnumerationLiteral
            literal.name = name
            enumLiterals += literal
        }
        return enumLiterals
    }
    
    /**
     * Creates and returns a Uml-Class.
     * The class is not contained in a rootmodel.
     * 
     * @throws IllegalArgumentException if name or visibility is null
     */
    def static Class createUmlClass(String name, VisibilityKind visibility, boolean abstr, boolean fin) {
        val uClass = UMLFactory.eINSTANCE.createClass;
        setName(uClass, name)
        uClass.visibility = visibility;
        uClass.isAbstract = abstr;
        uClass.isFinalSpecialization = fin;
        return uClass;
    }
    
    /**
     * Creates and returns an Interface.
     * Visibility is automatically set to public.
     * SuperInterfaces can be null.
     * The interface is not contained in a rootmodel.
     * 
     * @throws IllegalArgumentException if name is null.
     */
    def static Interface createUmlInterface(String name, List<Interface> superInterfaces) {
        val uInterface = UMLFactory.eINSTANCE.createInterface;
        setName(uInterface, name)
        uInterface.visibility = VisibilityKind.PUBLIC_LITERAL;
        if (!superInterfaces.nullOrEmpty) {
            uInterface.generals.addAll(superInterfaces);
        }        
        return uInterface;
    }
    
    /**
     * Creates and returns a PrimitiveType. It is added to the model.
     */
    def static PrimitiveType createUmlPrimitiveTypeAndAddToModel(Package uPackage, String pTypeName) {
        val pType = createUmlPrimitiveType(pTypeName);
        uPackage.packagedElements += pType
        return pType
    }
    
    /**
     * Returns a PrimitiveType. It is not contained in a rootelement.
     * @throws IllegalArgumentException if name is null
     */
    def static createUmlPrimitiveType (String name) {
        val pType = UMLFactory.eINSTANCE.createPrimitiveType;
        setName(pType, name)
        return pType;
    }
    
    /**
     * Extracts the list of superinterfaces of umlInterface. Returns null if umlInterface has no
     * superinterfaces.
     * 
     */
    def static EList<Classifier> extractSuperInterfaces(Interface umlInterface) {
        val supers = umlInterface?.generals
        if (supers.nullOrEmpty) {
            return null
        }
        return supers
    }
    
    /**
     * Removes the elements in the Iterator iter with the same name as classif.
     */
    def static void removeClassifierFromIterator(Iterator<? extends Classifier> iter, Classifier classif) {
        while (iter.hasNext) {
            if (classif.name.equals(iter.next.name)) {
                iter.remove;
            }
        }
    }
    
    /**
     * Converts the parent namespace/package into a list of Strings
     * 
     * org.example.test.test2 --> [org, example, test]
     * 
     */
    def static List<String> getUmlParentNamespaceAsStringList(Namespace uNamespace) {
        if (!(uNamespace.namespace instanceof Model)) {
            return buildNamespaceStringList(uNamespace.namespace, new ArrayList<String>)
        } else {
            return new ArrayList<String>
        }
        
    }
    
    /**
     * Converts the namespace/package into a list of Strings
     * 
     * org.example.test.test2 --> [org, example, test, test2]
     * 
     */
    def static List<String> getUmlNamespaceAsStringList(Namespace uNamespace) {
        return buildNamespaceStringList(uNamespace, new ArrayList<String>)
    }
    
    def private static List<String> buildNamespaceStringList(Namespace uNamespace, List<String> namespace) {
        namespace += uNamespace?.name
        if (uNamespace?.namespace !== null && !(uNamespace?.namespace instanceof Model)) {
            return buildNamespaceStringList(uNamespace.namespace, namespace)
        } else {
            return namespace.reverse
        }
    }
    
    def static void setName(NamedElement namedElement, String name) {
        if (name === null) {
            throw new IllegalArgumentException("Invalid name: " + name + " for " + namedElement);
        }
        namedElement.name = name;
    }
    
    def static removePackagedElementFromPackage(Package uPackage, PackageableElement packageable) {
        val iter = uPackage.packagedElements.iterator
        while (iter.hasNext) {
            if (iter.next.name.equals(packageable.name)) {
                iter.remove;
            }
        }
    }
}