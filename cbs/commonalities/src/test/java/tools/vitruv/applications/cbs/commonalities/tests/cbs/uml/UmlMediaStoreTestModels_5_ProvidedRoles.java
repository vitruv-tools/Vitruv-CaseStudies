package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlMediaStoreTestModels_5_ProvidedRoles extends AbstractUmlMediaStoreTestModels {

	// Increment 5: Additionally includes provided roles.
	private static final String UML_MEDIA_STORE_MODEL_PATH = "src/test/resources/model/uml/MediaStore_5_ProvidedRoles.uml";

	public UmlMediaStoreTestModels_5_ProvidedRoles(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	protected String getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH;
	}
}
