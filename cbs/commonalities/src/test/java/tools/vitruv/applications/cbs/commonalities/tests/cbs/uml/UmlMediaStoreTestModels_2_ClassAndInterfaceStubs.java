package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlMediaStoreTestModels_2_ClassAndInterfaceStubs extends AbstractUmlMediaStoreTestModels {

	// Increment 2: Additionally includes empty classes and interfaces for all components and component interfaces.
	private static final String UML_MEDIA_STORE_MODEL_PATH = "src/test/resources/model/uml/MediaStore_2_ClassAndInterfaceStubs.uml";

	public UmlMediaStoreTestModels_2_ClassAndInterfaceStubs(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	protected String getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH;
	}
}
