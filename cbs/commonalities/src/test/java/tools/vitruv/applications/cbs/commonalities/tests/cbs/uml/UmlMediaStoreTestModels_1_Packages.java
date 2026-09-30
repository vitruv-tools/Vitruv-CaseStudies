package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlMediaStoreTestModels_1_Packages extends AbstractUmlMediaStoreTestModels {

	// Increment 1: Check if the corresponding UML packages exist.
	private static final String UML_MEDIA_STORE_MODEL_PATH = "src/test/resources/model/uml/MediaStore_1_Packages.uml";

	public UmlMediaStoreTestModels_1_Packages(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	protected String getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH;
	}
}
