package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlMediaStoreTestModels_1_Packages extends AbstractUmlMediaStoreTestModels {

	// Increment 1: Check if the corresponding UML packages exist.
	static val UML_MEDIA_STORE_MODEL_PATH = 'resources/model/uml/MediaStore_1_Packages.uml'

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH
	}
}
