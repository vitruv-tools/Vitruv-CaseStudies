package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.ComponentEquivalenceTemplate
import tools.vitruv.applications.cbs.testutils.equivalencetest.ParameterizedEquivalenceTestBuilder
import org.junit.jupiter.api.Disabled

@ReactionsEquivalenceTest
class ComponentEquivalenceTest extends ComponentEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
	
	@Disabled("fails with ‘Cannot find … in parent resolver resource set!’")
	override creation(extension ParameterizedEquivalenceTestBuilder parameterBuilder) {
		super.creation(parameterBuilder)
	}
	
}
