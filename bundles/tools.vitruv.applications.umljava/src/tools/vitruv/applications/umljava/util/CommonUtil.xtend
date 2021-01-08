package tools.vitruv.applications.umljava.util

import tools.vitruv.framework.userinteraction.UserInteractor
import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import edu.kit.ipd.sdq.activextendannotations.Utility

/**
 * This class contains util functions that are not explicit attached to java or uml.
 * 
 * @author Fei
 */
@Utility
class CommonUtil {

	/**
	 * Displays the given message with the userInteraction.
	 * 
	 * @param userInteractor the userInteractor to display the message.
	 * @param message the message to display
	 */
	def static void showMessage(UserInteractor userInteractor, String message) {
		userInteractor.notificationDialogBuilder.message(message).windowModality(WindowModality.MODAL).
			startInteraction()
	}
}
