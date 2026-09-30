package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlMediaStoreTestModels_4_OperationSignatures extends AbstractUmlMediaStoreTestModels {

	// Increment 4: Additionally includes operations with parameters for interface operation signatures.
	private static final String UML_MEDIA_STORE_MODEL_PATH = "src/test/resources/model/uml/MediaStore_4_OperationSignatures.uml";

	public UmlMediaStoreTestModels_4_OperationSignatures(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	protected String getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH;
	}
}
