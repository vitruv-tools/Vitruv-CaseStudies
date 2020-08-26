package tools.vitruv.applications.cbs.commonalities.tests.util.uml

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.Model
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter

import static com.google.common.base.Preconditions.*
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.Assert.*
import static tools.vitruv.testutils.matchers.ModelMatchers.*

import static extension tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlFilePathHelper.*

class UmlTestHelper {

	val extension VitruvApplicationTestAdapter vitruvApplicationTestAdapter

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null")
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter
	}

	def Model createAndSynchronizeUmlModel(Model umlModel) {
		createAndSynchronizeModel(umlModel.umlModelFilePath, umlModel)
		return umlModel
	}

	def umlModelResource(Model umlModel) {
		return getResourceAt(umlModel.umlModelFilePath)
	}

	def getUmlRootModel(Resource umlModelResource) {
		assertTrue("Expecting resource to contain exactly 1 root object: " + umlModelResource.URI,
			umlModelResource.contents.size == 1)
		val umlModel = umlModelResource.contents.head as Model
		assertNotNull("Could not find UML root model in resource: " + umlModelResource.URI, umlModel)
		return umlModel
	}

	def assertUmlModelExists(Model umlModel) {
		assertThat(umlModel.umlModelResource, contains(umlModel, ignoringUnsetFeatures))
	}
}
