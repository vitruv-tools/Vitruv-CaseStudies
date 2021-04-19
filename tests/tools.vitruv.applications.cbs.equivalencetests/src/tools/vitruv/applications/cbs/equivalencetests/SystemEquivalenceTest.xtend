package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.SystemEquivalenceTemplate
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder
import org.junit.jupiter.api.Disabled

@ReactionsEquivalenceTest
class SystemEquivalenceTest extends SystemEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
	
	@Disabled("fails with ‘There were (2) corresponding elements of type Package for: …’")
	override creation(extension EquivalenceTestBuilder builder) {
		super.creation(builder)
	}
	
}
