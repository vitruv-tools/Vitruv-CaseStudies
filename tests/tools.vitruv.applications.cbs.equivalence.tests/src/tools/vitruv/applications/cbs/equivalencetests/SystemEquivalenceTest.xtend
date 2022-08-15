package tools.vitruv.applications.cbs.equivalencetests

import org.junit.jupiter.api.TestFactory
import tools.vitruv.applications.cbs.equivalencetesttemplates.SystemEquivalenceTemplate
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder

@ReactionsEquivalenceTest
class SystemEquivalenceTest extends SystemEquivalenceTemplate {
	@TestFactory
	override creation(extension EquivalenceTestBuilder builder) {
		userInteractions [
			// TODO unify the interactions
			onTextInput [message.contains("path for the UML root model")]
				.respondWith("model/")
			onTextInput [message.contains("name for the UML root model")]
				.respondWith("model")
			onTextInput [message.contains("where to save the corresponding Uml-Model")]
				.respondWith("model/model")
			onTextInput [message.contains("where to save the corresponding PCM model")]
				.respondWith("model/Test")
			onMultipleChoiceSingleSelection [message.contains("a pcm::Repository or a pcm::System")]
				.respondWithChoiceMatching [contains("System")]
		]
		
		super.creation(builder)
	}
}
