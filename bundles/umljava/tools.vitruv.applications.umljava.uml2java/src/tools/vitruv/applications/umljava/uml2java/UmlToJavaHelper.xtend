package tools.vitruv.applications.umljava.uml2java

import java.util.ArrayList
import java.util.HashSet
import java.util.LinkedList
import java.util.List
import org.eclipse.uml2.uml.PrimitiveType
import org.emftext.language.java.members.Field
import org.emftext.language.java.types.TypeReference
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor

import static tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.umljava.util.java.JavaStandardType.*
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.classifiers.ConcreteClassifier

/**
 * A helper class that contains util functions which depends on
 * the correspondence model or userinteracting.
 * 
 * @author Fei
 */
class UmlToJavaHelper {
    
	private new() {
	}
	
	//TODO re-/move primitive type mapping
	
	/**
	 * Returns the corresponding Java-PrimitiveType by checking the given Uml-PrimitiveType's name.
	 * (Case-sensitive)
	 * Returns null if no corresponding Java-PrimitiveType could be found.
	 */
	def static TypeReference mapToJavaPrimitiveType(PrimitiveType pType) {
	    try {
	    	// TODO compatibility issue: adapt mapping to be compatible with predefined uml::PrimitiveTypes
	        createJavaPrimitiveType(pType.name)
	    } catch (IllegalArgumentException i){
	        return null
	    }
	}
	
	/**
	 * Prompts a message to the user that allows him to choose a collection datatype.
	 * 
	 * @param userInteractor the userInteractor to prompt the message
	 * @return the selected name of the collection datatype by the user
	 */
    def static letUserSelectCollectionTypeName(UserInteractor userInteractor) {
        var List<Class<?>> collectionDataTypes = new ArrayList
        collectionDataTypes += #[ArrayList, LinkedList, HashSet]
        val List<String> collectionDataTypeNames = new ArrayList<String>(collectionDataTypes.size)
        for (collectionDataType : collectionDataTypes) {
            collectionDataTypeNames.add(collectionDataType.name)
        }
        val String selectTypeMsg = "Select a Collectiontype for the association end"
        val int selectedType = userInteractor.singleSelectionDialogBuilder.message(selectTypeMsg)
            .choices(collectionDataTypeNames).windowModality(WindowModality.MODAL).startInteraction()
        val Class<?> selectedClass = collectionDataTypes.get(selectedType)
        return selectedClass.name
    }
    
    /**
     * Creates a getter for the attribute and adds it t its class.
     * 
     * @param jAttribute the attribute for which a getter should be created
     */
    def static createGetterForAttribute(Field jAttribute) {
        val jGetter = createJavaGetterForAttribute(jAttribute, JavaVisibility.PUBLIC)
        jAttribute.containingConcreteClassifier.members += jGetter
    }
    
    /**
     * Creates a setter for the attribute and adds it to its class.
     * 
     * @param jAttribute the attribute for which a getter should be created
     */
    def static createSetterForAttribute(Field jAttribute) {
        val jSetter = createJavaSetterForAttribute(jAttribute, JavaVisibility.PUBLIC)
        jAttribute.containingConcreteClassifier.members += jSetter
    }
    
    
    /**
     * Displays the given message with the userInteractor.
     * 
     * @param userInteractor the userInteractor to display the message.
     * @param message the message to display
     */
    def static void showMessage(UserInteractor userInteractor, String message) {
        userInteractor.notificationDialogBuilder.message(message).windowModality(WindowModality.MODAL)
            .startInteraction()
    }
	
}