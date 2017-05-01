package tools.vitruv.applications.umljava.uml2java

import org.apache.log4j.Logger
import org.eclipse.uml2.uml.EnumerationLiteral
import org.eclipse.uml2.uml.LiteralUnlimitedNatural
import org.eclipse.uml2.uml.PrimitiveType
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.Type
import org.eclipse.uml2.uml.VisibilityKind
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.classifiers.ClassifiersFactory
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.emftext.language.java.modifiers.AnnotableAndModifiable
import org.emftext.language.java.modifiers.Modifier
import org.emftext.language.java.modifiers.ModifiersFactory
import org.emftext.language.java.types.TypeReference
import org.emftext.language.java.types.TypesFactory

import static tools.vitruv.applications.umljava.util.JavaUtil.*
import org.emftext.language.java.members.MembersFactory
import java.util.Set
import java.util.HashSet
import java.util.ArrayList
import java.util.LinkedList
import java.util.List
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType

class UmlToJavaHelper {
    
	private static val logger = Logger.getLogger(UmlToJavaHelper);
	private new() {
	}
    
    def static void setJavaVisibility(AnnotableAndModifiable jModifiable, VisibilityKind uVisibility) {
        setJavaVisibilityModifier(jModifiable, getJavaVisibilityConstantFromUmlVisibilityKind(uVisibility))
    }
    
    /**
 	 * Creates a Java-NamespaceReference if cType != null
     * Creates a PrimitiveType if dType != null && cType == null
     * Creates Void if both Types are null
     * 
     * @param dType uml-DataType
     * @param cType java-Class
     */
	def static TypeReference createTypeReference(Type dType, ConcreteClassifier cType) {
		if (dType == null && cType == null) {//TODO dispatch Method
		    return TypesFactory.eINSTANCE.createVoid();
		} else if (cType != null) {
	        return createNamespaceReferenceFromClassifier(cType);
	    } else if (dType instanceof PrimitiveType) {
	        return mapToJavaPrimitiveType(dType);
	    } else {//TODO dType ist DataType
	        val dClass = ClassifiersFactory.eINSTANCE.createClass;
	        dClass.name = dType.name;
	        return createNamespaceReferenceFromClassifier(dClass)
		}
	} 
	
	/**
	 * Returns the corresponding Java-PrimitiveType by checking the given Uml-PrimitiveType's name.
	 * (Case-sensitive)
	 * Returns null if no corresponding Java-PrimitiveType could be found.
	 */
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
	        default: {
	            logger.warn("No corresponding Java-PrimitiveType for " + pType + ". Returning null.")
	            return null
	        }
	    }
	}
	
    def static letUserSelectCollectionTypeName(UserInteracting userInteracting) {
    	var List<java.lang.Class<?>> collectionDataTypes = new ArrayList
		collectionDataTypes += #[ArrayList, LinkedList, HashSet]
		val List<String> collectionDataTypeNames = new ArrayList<String>(collectionDataTypes.size)
		for (collectionDataType : collectionDataTypes) {
			collectionDataTypeNames.add(collectionDataType.name)
		}
		val String selectTypeMsg = "Select a Collectiontype for the association end"
		val int selectedType = userInteracting.selectFromMessage(UserInteractionType.MODAL, selectTypeMsg,
			collectionDataTypeNames)
		val java.lang.Class<?> selectedClass = collectionDataTypes.get(selectedType)
	    return selectedClass.name
    }
	
}