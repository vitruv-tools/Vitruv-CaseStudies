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
import static tools.vitruv.applications.umljava.util.JavaUtil.*

class UmlToJavaHelper {
    
	private static val log = Logger.getLogger(UmlToJavaHelper);
	private new() {}



    def static void setJavaVisibility(AnnotableAndModifiable jModifiable, VisibilityKind vis) {
        removeJavaVisibilityModifiers(jModifiable);
        val v = getJavaVisibility(vis);
        if (v != null) {
            jModifiable.addModifier(v);
        }
        
    }
    /**
     * Liefert zu einem UML-VisibilityKind den entsprechenden Java-Sichtbarkeitsmodifier.
     * Default-Rückgabe: null -> Package-Private
     */
    def static Modifier getJavaVisibility(VisibilityKind v) {
        switch v.value {
                case VisibilityKind.PRIVATE : return ModifiersFactory.eINSTANCE.createPrivate
                case VisibilityKind.PROTECTED : return ModifiersFactory.eINSTANCE.createProtected
                case VisibilityKind.PUBLIC : return ModifiersFactory.eINSTANCE.createPublic
                default : return null //Package-Private
            }
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
	        return createNamespaceReferenceFromClassifier(cType);
	    } else if (dType instanceof PrimitiveType) {
	        return mapToJavaPrimitiveType(dType);
	    } else {
	        val dClass = ClassifiersFactory.eINSTANCE.createClass;
	        dClass.name = dType.name;
	        return createNamespaceReferenceFromClassifier(dClass)
		}
	} 
	
	def static org.emftext.language.java.types.PrimitiveType mapToJavaPrimitiveType(PrimitiveType pType) {
	    switch pType.name {
	        case BOOLEAN : return TypesFactory.eINSTANCE.createBoolean
	        case BYTE : return TypesFactory.eINSTANCE.createByte
	        case CHAR : return TypesFactory.eINSTANCE.createChar
	        case DOUBLE : return TypesFactory.eINSTANCE.createDouble
	        case FLOAT : return TypesFactory.eINSTANCE.createFloat
	        case INT : return TypesFactory.eINSTANCE.createInt
	        case LONG : return TypesFactory.eINSTANCE.createLong
	        case SHORT : return TypesFactory.eINSTANCE.createShort
	        default: throw new IllegalArgumentException("Invalid PrimitiveType: " + pType.name)
	    }
	       ;
	}
}