package tools.vitruv.applications.umlclassumlcomponents.sharedutil

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType

class SharedUtil {
		
	public static def int modalTextUserinteracting(UserInteracting userInteracting, String msg, String... selections) {
		return userInteracting.selectFromMessage(UserInteractionType.MODAL, msg, selections)
	}

	public static def boolean modalTextYesNoUserInteracting(UserInteracting userInteracting, String msg) {
		return 0 == userInteracting.modalTextUserinteracting(msg, "Yes", "No")
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