package tools.vitruv.applications.umlclassumlcomponents.class2comp

import tools.vitruv.framework.userinteraction.UserInteractionType
import tools.vitruv.framework.userinteraction.UserInteracting

class class2compUtil {
		
	def static int modalTextUserinteracting(UserInteracting userInteracting, String msg, String... selections) {
		return userInteracting.selectFromMessage(UserInteractionType.MODAL, msg, selections)
	}

	def static boolean modalTextYesNoUserInteracting(UserInteracting userInteracting, String msg) {
		return 0 == userInteracting.modalTextUserinteracting(msg, "Yes", "No")
	}
}