package tools.vitruv.applications.pcmumlcomponents.tests.pcm2uml

import org.eclipse.uml2.uml.Model
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import java.nio.file.Path
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.claimOne

class UmlModelTest extends AbstractPcmUmlTest {

	@Test
	def void testModelCreation() {
		val modelUri = getUri(Path.of("model").resolve(MODEL_NAME + ".uml"))
		assertThat(modelUri, isResource)
		val correspondingElements = rootElement.correspondingElements
		assertEquals(1, correspondingElements.size)
		val umlModel = correspondingElements.get(0)
		assertTrue(umlModel instanceof Model)
		assertEquals(MODEL_NAME, (umlModel as Model).name)
	}

	@Test
	def void testModelRename() {
		val newName = 'foo'
		rootElement.entityName = newName
		propagate
		val model = getCorrespondingEObjects(rootElement, Model).claimOne
		assertEquals(newName, model.name)
	}
}
