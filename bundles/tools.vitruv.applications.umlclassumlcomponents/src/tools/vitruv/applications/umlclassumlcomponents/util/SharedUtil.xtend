package tools.vitruv.applications.umlclassumlcomponents.util

import tools.vitruv.framework.userinteraction.UserInteractionOptions.WindowModality
import tools.vitruv.framework.userinteraction.UserInteractor

class SharedUtil {
		
	static def int modalTextUserinteracting(UserInteractor userInteractor, String msg, String... selections) {
		return userInteractor.singleSelectionDialogBuilder.message(msg).choices(selections)
		    .windowModality(WindowModality.MODAL).startInteraction()
	}

	static def boolean modalTextYesNoUserInteractor(UserInteractor userInteractor, String msg) {
		return 0 == userInteractor.modalTextUserinteracting(msg, "Yes", "No")
	}


	/***********
	*Constants:*
	************/
	
	public static val PACKAGE_SUFFIX = " Package"
	public static val FOLDER_NAME = "model/" 
	public static val MODEL_NAME = "model" 
	public static val MODEL_FILE_EXTENSION = "uml"
	
	public static val CLASS_DATATYPES_PACKAGE = "Class DataTypes"
	public static val CLASS_DATATYPES_PACKAGE_NAME = CLASS_DATATYPES_PACKAGE + PACKAGE_SUFFIX
	public static val CLASS_DATATYPES_PACKAGE_TAG = CLASS_DATATYPES_PACKAGE + " Tag" 
	
	public static val DATA_TYPE_REPRESENTATION_TAG = "DataTypeRepresentation"
	public static val DATA_TYPE_OPERATION_TAG = "DataTypeOperation"
	public static val DATA_TYPE_PROPERTY = "DataTypeProperty"
	
	public static val COMP_INTERFACE_SUFFIX = " (Component)"
	public static val COMP_IFR_AND_USAGE_SUFFIX = " (Component)"
	public static val CLASS_INTERFACE_SUFFIX = " (Class)"
	public static val CLASS_IFR_AND_USAGE_SUFFIX = " (Class)"
	
}