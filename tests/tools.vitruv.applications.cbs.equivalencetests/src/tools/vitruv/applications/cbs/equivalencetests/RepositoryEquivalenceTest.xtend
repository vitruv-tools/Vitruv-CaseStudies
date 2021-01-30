package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.RepositoryEquivalenceTemplate
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.Order

@ReactionsEquivalenceTest
class RepositoryEquivalenceTest extends RepositoryEquivalenceTemplate {
	
	@TestFactory
	@Order(0)
	override creation(extension EquivalenceTestBuilder builder) {
		userInteractions [
			// TODO unify the interactions
			onTextInput [message.contains("path for the UML root model")]
				.respondWith("model/")
			onTextInput [message.contains("name for the UML root model")]
				.respondWith("umlrootmodel")
			onTextInput [message.contains("where to save the corresponding Uml-Model")]
				.respondWith("model/umlrootmodel")
			onTextInput [message.contains("where to save the corresponding PCM model")]
				.respondWith("model/Test")
			onMultipleChoiceSingleSelection [message.contains("a PCM::Repository or a PCM::System")]
				.respondWithChoiceMatching [contains("Repository")]
		]
		
		super.creation(builder)
	}
	
	@TestFactory
	override deletion(extension EquivalenceTestBuilder builder) {
		userInteractions [
			onConfirmation [message.contains("delete the UML model")]
				.respondWith(true)	
		]
		
		super.deletion(builder)
	}
}
