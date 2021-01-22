package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

class UmlMediaStoreTestModels_5_ProvidedRoles extends AbstractUmlMediaStoreTestModels {

	// Increment 5: Additionally includes provided roles.
	static val UML_MEDIA_STORE_MODEL_PATH = 'resources/model/uml/MediaStore_5_ProvidedRoles.uml'

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override protected getUmlMediaStoreModelPath() {
		return UML_MEDIA_STORE_MODEL_PATH
	}
}
