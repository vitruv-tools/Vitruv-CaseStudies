package tools.vitruv.applications.umljava.util

import java.util.Iterator
import org.eclipse.emf.common.util.EList
import org.eclipse.uml2.uml.AggregationKind
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Interface
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.Operation
import org.eclipse.uml2.uml.Parameter
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.uml2.uml.VisibilityKind
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction
import org.eclipse.uml2.uml.PrimitiveType
import java.util.List
import java.util.ArrayList

/**
 * Uml-Util-Class
 */
class UmlUtil {
	
    /**
     * Prevents instantiation
     */
    private new () {
        
    }
    
    /**
     * Creates a simple Uml CLass (public, not static, not abstract, no fields, no operations)
     */
    def static createSimpleUmlClass(Model model, String name) {
        return createUmlClassAndAddToModel(model, name, VisibilityKind.PUBLIC_LITERAL, false, false);
    }
    
    def static createSimpleUmlInterface(Model model, String name) {
        return createUmlInterfaceAndAddToModel(model, name, null);
    }
    
    /**
     * Creates and returns a Uml Class. It is added the model.
     */
    def static Class createUmlClassAndAddToModel(Model model, String name, VisibilityKind vis, boolean abstr, boolean fin) {
        val uClass = createUmlClass(name, vis, abstr, fin)
        model.packagedElements += uClass
        return uClass
    }
    
    /**
     * Creates and returns a Uml Interface. It is added the model.
     */
    def static Interface createUmlInterfaceAndAddToModel(Model model, String name, List<Interface> superInterfaces) {
        val uI = createUmlInterface(name, superInterfaces)
        model.packagedElements += uI
        return uI
    }
    
    /**
     * Creates and returns a Uml-Class.
     * The class is not contained in a rootmodel.
     * 
     * @throws IllegalArgumentException if name or vis is null
     */
    def static Class createUmlClass(String name, VisibilityKind vis, boolean abstr, boolean fin) {
        val uC = UMLFactory.eINSTANCE.createClass;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create UmlClass - name is null");
        }
        uC.name = name;
        if (vis == null) {
            throw new IllegalArgumentException("Cannot create UmlClass - visibility is null");
        }
        uC.visibility = vis;
        uC.isAbstract = abstr;
        uC.isFinalSpecialization = fin;
        return uC;
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
        val uI = UMLFactory.eINSTANCE.createInterface;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create UmlInterface - name is null");
        }
        uI.name = name;
        uI.visibility = VisibilityKind.PUBLIC_LITERAL;
        if (!superInterfaces.nullOrEmpty) {
            uI.generals.addAll(superInterfaces);
        }        
        return uI;
    }
    
    /**
     * @return public Operation with name; no return, params or modifier
     */
    def static Operation createSimpleUmlOperation(String name) {
        return createUmlOperation(name, null, VisibilityKind.PUBLIC_LITERAL, false, false, null)
    }
    
    /**
     * Creates an uml operation that acts as InterfaceMethod. (public, not static, not abstract)
     */
    def protected Operation createUmlInterfaceOperation(String name, Type returnType, List<Parameter> params) {
        return createUmlOperation(name, returnType, VisibilityKind.PUBLIC_LITERAL, false, false, params);
    }
    
    /**
     * 
     * return and params can be null
     * @throws IllegalArgumentException if name or vis is null
     */
    def static Operation createUmlOperation(String name, Type returnType, VisibilityKind vis, boolean abstr, boolean stat, List<Parameter> params) {
        val op = UMLFactory.eINSTANCE.createOperation;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create UmlOperation - name is null");
        }
        op.name = name;
        if (returnType != null) {
            op.type = returnType;
        }
        if (vis == null) {
            throw new IllegalArgumentException("Cannot create UmlOperation - visibility is null");
        }
        op.visibility = vis;
        op.isAbstract = abstr;
        op.isStatic = stat;
        if (!params.nullOrEmpty) {
            op.ownedParameters.addAll(params);
        }
        return op;
    }
    
    /**
     * @throws IllegalArgumentException if name or vis is null
     */
    def static createUmlAttribute(String name, Type type, VisibilityKind vis, boolean fin, boolean stat) {
        val attr = UMLFactory.eINSTANCE.createProperty;
        if (name == null) {
            throw new IllegalArgumentException("Cannot create UmlProperty - name is null");
        }
        attr.name = name;
        if (vis == null) {
            throw new IllegalArgumentException("Cannot create UmlProperty - visibility is null");
        }
        attr.visibility = vis;
        attr.isReadOnly = fin;
        attr.isStatic = stat;
        if (type != null) {
            attr.type = type;
        }
        return attr;
    }
    
    /**
     * If name == null, it becomes a return type
     */
    def static Parameter createUmlParameter(String name, Type type) {
        val param = UMLFactory.eINSTANCE.createParameter;
        param.type = type;
        if (name == null) {
            param.direction = ParameterDirectionKind.RETURN_LITERAL;
        } else {
            param.name = name;
            param.direction = ParameterDirectionKind.IN_LITERAL;
        }
        return param;
    }
    
    /**
     * Creates and returns a PrimitiveType. It is added to the model.
     */
    def static PrimitiveType createUmlPrimitiveTypeAndAddToModel(Model model, String pTypeName) {
        val pType = createUmlPrimitiveType(pTypeName);
        model.packagedElements += pType
        return pType
    }
    
    /**
     * Returns a PrimitiveType. It is not contained in a rootelement.
     * @throws IllegalArgumentException if name is null
     */
    def static createUmlPrimitiveType (String name) {
        val pType = UMLFactory.eINSTANCE.createPrimitiveType;
        if (name == null) {
            throw new IllegalArgumentException("Invalid Primitive Type name: " + name);
        }
        pType.name = name;
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
    def static List<String> getUmlParentNamespaceAsStringList(org.eclipse.uml2.uml.Namespace uNamespace) {
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
    def static List<String> getUmlNamespaceAsStringList(org.eclipse.uml2.uml.Namespace uNamespace) {
    	return buildNamespaceStringList(uNamespace, new ArrayList<String>)
    }
    
    def private static List<String> buildNamespaceStringList(org.eclipse.uml2.uml.Namespace uNamespace, List<String> namespace) {
    	namespace += uNamespace?.name
    	if (uNamespace?.namespace !== null && !(uNamespace?.namespace instanceof Model)) {
    		return buildNamespaceStringList(uNamespace.namespace, namespace)
    	} else {
    		return namespace.reverse
    	}
    }
    
    def static createDirectedAssociation(Class fromClass, Class toClass, int lowerLimit, int upperLimit) {
    	fromClass.createAssociation(true, AggregationKind.NONE_LITERAL, firstLettertoLowercase(toClass.name), lowerLimit, upperLimit, toClass, false, AggregationKind.NONE_LITERAL, firstLettertoLowercase(fromClass.name), 0, 1)
    }
    
    
    
    def static private String firstLettertoLowercase(String s) {
    	return Character.toLowerCase(s.charAt(0)) + s.substring(1)
    }
}