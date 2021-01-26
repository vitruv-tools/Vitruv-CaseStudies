package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.MediaStoreTest
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase
import static org.junit.jupiter.api.Assertions.assertNotNull

package abstract class AbstractUmlMediaStoreTestModels extends UmlTestModelsBase implements MediaStoreTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	protected abstract def String getUmlMediaStoreModelPath()

	override mediaStoreCreation() {
		return newModel [
			val umlMediaStoreResource = getTestResource(umlMediaStoreModelPath)
			val umlMediaStoreModel = umlMediaStoreResource.umlRootModel
			assertNotNull(umlMediaStoreModel, "Could not find UML MediaStore model!")
			return #[
				umlMediaStoreModel
			]
		]
	}
}
