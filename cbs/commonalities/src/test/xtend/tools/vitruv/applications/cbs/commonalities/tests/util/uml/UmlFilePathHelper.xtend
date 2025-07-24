package tools.vitruv.applications.cbs.commonalities.tests.util.uml

import org.eclipse.uml2.uml.Model

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.*
import org.eclipse.uml2.uml.resource.UMLResource

class UmlFilePathHelper {

	static val MODEL_PATH = "model" + PATH_SEPARATOR

	private new() {
	}

	static def umlModelFilePath(String modelName) {
		return MODEL_PATH.appendFile(modelName, UMLResource.FILE_EXTENSION)
	}

	static def umlModelFilePath(Model model) {
		return umlModelFilePath(model.name)
	}
}
