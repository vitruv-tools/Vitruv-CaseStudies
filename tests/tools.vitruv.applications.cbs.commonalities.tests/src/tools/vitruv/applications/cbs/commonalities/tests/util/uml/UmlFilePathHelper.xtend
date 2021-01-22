package tools.vitruv.applications.cbs.commonalities.tests.util.uml

import org.eclipse.uml2.uml.Model
import tools.vitruv.domains.uml.UmlDomain

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.*

class UmlFilePathHelper {

	static val MODEL_PATH = "model" + PATH_SEPARATOR

	private new() {
	}

	static def umlModelFilePath(String modelName) {
		return MODEL_PATH.appendFile(modelName, UmlDomain.FILE_EXTENSION)
	}

	static def umlModelFilePath(Model model) {
		return umlModelFilePath(model.name)
	}
}
