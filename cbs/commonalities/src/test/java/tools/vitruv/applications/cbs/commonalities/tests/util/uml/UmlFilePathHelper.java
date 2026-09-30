package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.PATH_SEPARATOR;
import static tools.vitruv.applications.cbs.commonalities.tests.util.common.FilePathUtil.appendFile;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.resource.UMLResource;

public final class UmlFilePathHelper {

	private static final String MODEL_PATH = "model" + PATH_SEPARATOR;

	private UmlFilePathHelper() {
	}

	public static String umlModelFilePath(String modelName) {
		return appendFile(MODEL_PATH, modelName, UMLResource.FILE_EXTENSION);
	}

	public static String umlModelFilePath(Model model) {
		return umlModelFilePath(model.getName());
	}
}
