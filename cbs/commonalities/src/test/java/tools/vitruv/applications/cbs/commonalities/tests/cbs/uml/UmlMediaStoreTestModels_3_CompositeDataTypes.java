package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlMediaStoreTestModels_3_CompositeDataTypes extends AbstractUmlMediaStoreTestModels {

	// Increment 3: Additionally includes classes and properties for CompositeDataTypes and their inner declarations.
	private static final String UML_MEDIA_STORE_MODEL_PATH = "src/test/resources/model/uml/MediaStore_3_CompositeDataTypes.uml";

	public UmlMediaStoreTestModels_3_CompositeDataTypes(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	protected String getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH;
	}
}
