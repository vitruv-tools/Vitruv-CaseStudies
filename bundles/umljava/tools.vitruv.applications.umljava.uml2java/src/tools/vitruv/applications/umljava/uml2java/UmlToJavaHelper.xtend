package tools.vitruv.applications.umljava.uml2java

import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.types.NamespaceClassifierReference
import org.eclipse.uml2.uml.Type
import org.emftext.language.java.types.TypesFactory
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.DataType
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emftext.language.java.classifiers.ConcreteClassifier
import tools.vitruv.framework.util.datatypes.ClaimableMap
import org.emftext.language.java.modifiers.Modifier
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.modifiers.Private
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Protected
import org.emftext.language.java.modifiers.Public
import org.emftext.language.java.types.ClassifierReference
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.members.Member
import java.util.Iterator
import org.eclipse.uml2.uml.Operation
import java.util.List
import java.util.ArrayList
import org.eclipse.uml2.uml.ParameterDirectionKind
import org.eclipse.uml2.uml.UMLFactory
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.BasicEList

class UmlToJavaHelper {
    private static val BOOLEAN = "boolean";
    private static val BYTE = "byte";
    private static val CHAR = "char";
    private static val DOUBLE = "double";
    private static val FLOAT = "float";
    private static val INT = "int";
    private static val LONG = "long";
    private static val SHORT = "short";
    
	private static val log = Logger.getLogger(UmlToJavaHelper);
	private new() {}

    /* 
    static val staticLoggerInit = {
        PropertyConfigurator.configure("log4j.properties");
    }*/

    /**
     * Liefert zu einem UML-VisibilityKind den entsprechenden Java-Sichtbarkeitsmodifier.
     * Default-Rückgabe: Public.
     */
    def static Modifier getJavaVisibility(VisibilityKind v) {
        switch v.value {
                case VisibilityKind.PRIVATE : return ModifiersFactory.eINSTANCE.createPrivate
                case VisibilityKind.PROTECTED : return ModifiersFactory.eINSTANCE.createProtected
                default : return ModifiersFactory.eINSTANCE.createPublic
            }
    }
    
    /**
     * Entfernt alle Sichtbarkeiten-Modifier eines AnnotableAndModifiables.
     * (Klassen, Interfaces, Methoden, Attribute)
     */
    def static removeJavaVisibilityModifiers(AnnotableAndModifiable a) {
        a.removeModifier(typeof(Private))
        a.removeModifier(typeof(Protected))
        a.removeModifier(typeof(Public))
    }
    
    /**
     * @param a         Objekt, dessen Modifer entfernt werden soll
     * @param modifier  Klasse des Modifiers, das von Objekt a entfernt werden soll 
     */
    def static <T extends Modifier> removeJavaModifier(AnnotableAndModifiable a, java.lang.Class<T> modifier ) {
        a.removeModifier(modifier)
    }
    /**
     * 
     * @param dType uml-DataType
     * @param cType java-Class
     */
	def static TypeReference createTypeReference(Type dType, ConcreteClassifier cType) {
	    
		
		if (dType == null && cType == null) {
		    return TypesFactory.eINSTANCE.createVoid();
		} else if (cType != null) {
	        return createClassifierReference(cType);
	    } else if (dType instanceof PrimitiveType) {
	        return mapToJavaPrimitiveType(dType);
	    } else {
	        val dClass = ClassifiersFactory.eINSTANCE.createClass;
	        dClass.name = dType.name;
	        return createClassifierReference(dClass)
		}
	} 
	
	def static org.emftext.language.java.types.PrimitiveType mapToJavaPrimitiveType(PrimitiveType pType) {
	    switch pType.name {
	        case BOOLEAN : return TypesFactory.eINSTANCE.createBoolean
	        case BYTE : return TypesFactory.eINSTANCE.createByte
	        case CHAR : return TypesFactory.eINSTANCE.createChar
	        case DOUBLE : return TypesFactory.eINSTANCE.createDouble
	        case FLOAT : return TypesFactory.eINSTANCE.createFloat
	        case INT : {
	            System.out.println("ssssssssssssssssss");
	            return TypesFactory.eINSTANCE.createInt;
	        }
	         
	        case LONG : return TypesFactory.eINSTANCE.createLong
	        case SHORT : return TypesFactory.eINSTANCE.createShort
	        default: throw new IllegalArgumentException("Invalid PrimitiveType: " + pType.name)
	    }
	       ;
	}
	
	/**
	 * NameSpaceClassifierReference hat eine Liste von ClassifierReferences
	 * @param concreteClassifiers = Class, Interface, Enum or Annotation
	 */
	def static NamespaceClassifierReference createClassifierReference(ConcreteClassifier concreteClassifier) {
        val namespaceClassifierReference = TypesFactory.eINSTANCE.createNamespaceClassifierReference
        val classifierRef = TypesFactory.eINSTANCE.createClassifierReference
        classifierRef.target = EcoreUtil.copy(concreteClassifier)
        namespaceClassifierReference.classifierReferences.add(classifierRef)

        return namespaceClassifierReference
    }
    
    /**
     * @param add true -> hinzufügen, sonst entfernen
     */
    def static setJavaModifier(AnnotableAndModifiable jMem, Modifier mod, boolean add) {
        if (add) {
                if (!jMem.hasModifier(mod.class)) {
                    jMem.addModifier(mod)
                }
            } else {
                jMem.removeModifier(mod.class)
            }
    }
    
    /**
     * Entfernt alle Classifier von Iterator anhand des Namens.
     * 
     * @param iter Iterator über eine Liste von TypeReferences
     * @param classif Klasse oder Interface, das entfernt werden soll
     */
    def static removeClassifierFromIterator(Iterator<TypeReference> iter, ConcreteClassifier classif) {
        while (iter.hasNext) {
                val type = (iter.next as NamespaceClassifierReference).classifierReferences.head.target
                if (classif.name.equals(type.name)) {
                    iter.remove;
                }
            }
    }
    
    def static List<Operation> addInterfaceMethodsToUmlClass(org.eclipse.uml2.uml.Interface inter, org.eclipse.uml2.uml.Class c) {
        var EList<Operation> opList = new BasicEList<Operation>;
        for (Operation op : inter.ownedOperations) {
            opList += copyOperation(op, c);
        }
        //c.ownedOperations.addAll(opList);
        return opList
    }
    
    def static Operation copyOperation(Operation op, org.eclipse.uml2.uml.Class c) {
        val newOp = UMLFactory.eINSTANCE.createOperation;
        newOp.name = op.name;
        if (op.type != null) {
            newOp.type = op.type
        }
        val EList<org.eclipse.uml2.uml.Parameter> l = new BasicEList<org.eclipse.uml2.uml.Parameter>
        for (org.eclipse.uml2.uml.Parameter param : op.ownedParameters) {
            l += param;
        }
        newOp.ownedParameters.addAll(l);
        newOp.isAbstract = op.isAbstract;
        newOp.isStatic = op.isStatic;
        newOp.visibility = op.visibility;
        c.ownedOperations += newOp;
        return newOp
    }
    
    /**
     * @return Liste der entfernten uml-Operationen
     */
    def static List<Operation> removeInterfaceMethodsFromUmlClass(org.eclipse.uml2.uml.Interface inter, org.eclipse.uml2.uml.Class c) {
        val iterClassMethods = c.ownedOperations.iterator;
        val EList<Operation> opList = new BasicEList<Operation>();
        /* 
        for (Operation op : c.ownedOperations) {
            for (Operation op2 : inter.ownedOperations) {
                if (umlOperationEquals(op, op2)) {
                    opList += op
                    
                }
            }
        }*/
         
        while (iterClassMethods.hasNext) {
            val currentOp = iterClassMethods.next;
            for (Operation op2 : inter.ownedOperations) {
                if (umlOperationEquals(currentOp, op2)) {
                    opList += currentOp
                    iterClassMethods.remove
                }
                    
            }
        }
        
        //System.out.println("bbbbbbbbbbbbbbb" + c.ownedOperations.removeAll(opList))
        return opList;
    }
    
    /**
     * Modifiers müssen nicht übereinstimmen, da Methoden in Java mit gleicher Signatur außer
     * Modifier nicht gültig sind
     * 
     * @return true: wenn Name, Parameter und Rückgabe übereinstimmen 
     */
    def static boolean umlOperationEquals(Operation op1, Operation op2) {
        if (op1 == null || op2 == null) {
            return false;
        }
        if (op1 == op2) {
            return true;
        }
        if (!op1.name.equals(op2.name)) {
            return false;
        }
        if (op1.ownedParameters.size != op2.ownedParameters.size) {
            return false;
        }
        for (org.eclipse.uml2.uml.Parameter p1 : op1.ownedParameters) {
            for (org.eclipse.uml2.uml.Parameter p2 : op2.ownedParameters) {
                if (!umlParameterEquals(p1, p2)) {
                    return false;
                }
            }
        }
        if (!umlTypeEquals(op1.type, op2.type)) {
            return false;
        }
        return true;
        
    }
    
    /**
     * Parameter kann auch Return-Typ sein. -> true, falls beide gleichen Type haben.
     * @return true: wenn Name und ParameterTyp gleich, oder beide null
     */
    def static boolean umlParameterEquals(org.eclipse.uml2.uml.Parameter p1, org.eclipse.uml2.uml.Parameter p2) {
        if (p1 == null && p2 == null) {
            return true;
        }
        if (p1 == null || p2 == null) {
            return false;
        }
        if (p1 == p2) {
            return true;
        }
        if (p1.direction == ParameterDirectionKind.RETURN_LITERAL && p2.direction == ParameterDirectionKind.RETURN_LITERAL) {
            if (umlTypeEquals(p1.type, p2.type)) {
                return true;
            }
            return false;
        }
        if (!p1.name.equals(p2.name)) {
            return false;
        }
        if (!umlTypeEquals(p1.type, p2.type)) {
            return false;
        }
        return true;
    }
    
    /**
     * @return true: wenn Name gleich oder beide null
     */
    def static boolean umlTypeEquals(org.eclipse.uml2.uml.Type t1, org.eclipse.uml2.uml.Type t2) {
        if (t1 == null && t2 == null) {
            return true;
        }
        if (t1 == null || t2 == null) {
            return false;
        }
        if (t1 == t2) {
            return true;
        }
        if(!t1.name.equals(t2.name)) {
            return false;
        }
        return true;
    }
}