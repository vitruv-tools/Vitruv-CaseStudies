package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml

import tools.vitruv.applications.cbs.commonalities.tests.cbs.AbstractMediaStoreTest
import tools.vitruv.applications.cbs.commonalities.tests.uml.UmlTestModelsBase
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static org.junit.Assert.*

package abstract class AbstractUmlMediaStoreTestModels extends UmlTestModelsBase implements AbstractMediaStoreTest.DomainModels {

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	protected abstract def String getUmlMediaStoreModelPath()

	override mediaStoreCreation() {
		return newModel [
			val umlMediaStoreResource = getTestResource(umlMediaStoreModelPath)
			val umlMediaStoreModel = umlMediaStoreResource.umlRootModel
			assertNotNull("Could not find UML MediaStore model!", umlMediaStoreModel)
			return #[
				umlMediaStoreModel
			]
		]
	}
}
