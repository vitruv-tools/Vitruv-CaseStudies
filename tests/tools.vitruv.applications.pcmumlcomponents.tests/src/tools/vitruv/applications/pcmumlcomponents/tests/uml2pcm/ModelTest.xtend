package tools.vitruv.applications.pcmumlcomponents.tests.uml2pcm

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.hamcrest.MatcherAssert.assertThat;
import static tools.vitruv.testutils.matchers.ModelMatchers.isResource
import java.nio.file.Path

class ModelTest extends AbstractUmlPcmTest {

	@Test
	def void testRepositoryCreation() {
		val modelUri = getUri(Path.of("repository").resolve(MODEL_NAME + ".repository"))
		assertThat(modelUri, isResource);
		val pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(MODEL_NAME, pcmRepository.entityName);
	}

	@Test
	def void testRepositoryRename() {
		val newName = 'foo';
		rootElement.name = newName;
		propagate
		val pcmRepository = rootElement.claimCorrespondingRepository
		assertEquals(newName, pcmRepository.entityName);
	}
}
