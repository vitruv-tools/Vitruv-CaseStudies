package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.ComponentEquivalenceTemplate
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder

@ReactionsEquivalenceTest
class ComponentEquivalenceTest extends ComponentEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
	
	override creation(extension ParameterizedEquivalenceTestBuilder parameterBuilder) {
		super.creation(parameterBuilder)
	}
	
}
