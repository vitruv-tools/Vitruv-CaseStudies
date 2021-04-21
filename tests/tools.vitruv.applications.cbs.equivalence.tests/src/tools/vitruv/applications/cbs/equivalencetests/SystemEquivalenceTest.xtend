package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.SystemEquivalenceTemplate
import tools.vitruv.applications.cbs.testutils.equivalencetest.EquivalenceTestBuilder

@ReactionsEquivalenceTest
class SystemEquivalenceTest extends SystemEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
	
	override creation(extension EquivalenceTestBuilder builder) {
		super.creation(builder)
	}
	
}
