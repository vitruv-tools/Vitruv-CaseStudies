package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlMediaStoreTestModels_2_ClassAndInterfaceStubs extends AbstractUmlMediaStoreTestModels {

	// Increment 2: Additionally includes empty classes and interfaces for all components and component interfaces.
	static val UML_MEDIA_STORE_MODEL_PATH = 'resources/model/uml/MediaStore_2_ClassAndInterfaceStubs.uml'

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH
	}
}
