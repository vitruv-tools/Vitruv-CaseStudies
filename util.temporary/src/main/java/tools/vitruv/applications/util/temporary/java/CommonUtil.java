package tools.vitruv.applications.util.temporary.java;

import tools.vitruv.change.interaction.UserInteractionOptions;
import tools.vitruv.change.interaction.UserInteractor;

/**
 * Utility class with general-purpose helper methods.
 * Not specific to Java or UML.
 *
 * Author: Fei
 */
public final class CommonUtil {

    private CommonUtil() {
        // Prevent instantiation
    }

    /**
     * Displays the given message using the provided UserInteractor.
     *
     * @param userInteractor the UserInteractor used to display the message
     * @param message        the message to display
     */
    public static void showMessage(final UserInteractor userInteractor, final String message) {
        userInteractor.getNotificationDialogBuilder()
                .message(message)
                .windowModality(UserInteractionOptions.WindowModality.MODAL)
                .startInteraction();
    }
}
