package tools.vitruv.applications.umljava.uml2java

import org.emftext.language.java.members.Field
import tools.vitruv.applications.umljava.util.java.JavaVisibility
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor

import static tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil.*

/**
 * A helper class that contains util functions which depends on
 * the correspondence model or userinteracting.
 * 
 * @author Fei
 */
class UmlToJavaHelper {
    
	private new() {
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