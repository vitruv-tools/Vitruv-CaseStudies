package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.RepositoryEquivalenceTemplate
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import org.junit.jupiter.api.TestFactory

@ReactionsEquivalenceTest
class RepositoryEquivalenceTest extends RepositoryEquivalenceTemplate {
	
	@TestFactory
	override creation(extension EquivalenceTestBuilder builder) {
		userInteractions [ extension interactions |
			// TODO unify the interactions
			onTextInput [message.contains("path for the UML root model")]
				.respondWith("model/")
			onTextInput [message.contains("name for the UML root model")]
				.respondWith("Test")
			onTextInput [message.contains("where to save the corresponding Uml-Model")]
				.respondWith("model/Test")
			onTextInput [message.contains("where to save the corresponding PCM model")]
				.respondWith("model/Test")
			onMultipleChoiceSingleSelection [message.contains("a PCM::Repository or a PCM::System")]
				.respondWithChoiceMatching[_, it | contains("Repository")]
		]
		
		super.creation(builder)
	}
	
}
