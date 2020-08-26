package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlMediaStoreTestModels_3_CompositeDataTypes extends AbstractUmlMediaStoreTestModels {

	// Increment 3: Additionally includes classes and properties for CompositeDataTypes and their inner declarations.
	static val UML_MEDIA_STORE_MODEL_PATH = 'resources/model/uml/MediaStore_3_CompositeDataTypes.uml'

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH
	}
}
