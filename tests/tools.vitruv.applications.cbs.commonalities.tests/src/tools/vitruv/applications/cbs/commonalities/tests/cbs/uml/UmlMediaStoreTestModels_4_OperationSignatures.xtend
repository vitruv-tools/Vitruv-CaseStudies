package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlMediaStoreTestModels_4_OperationSignatures extends AbstractUmlMediaStoreTestModels {

	// Increment 4: Additionally includes operations with parameters for interface operation signatures.
	static val UML_MEDIA_STORE_MODEL_PATH = 'resources/model/uml/MediaStore_4_OperationSignatures.uml'

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH
	}
}
