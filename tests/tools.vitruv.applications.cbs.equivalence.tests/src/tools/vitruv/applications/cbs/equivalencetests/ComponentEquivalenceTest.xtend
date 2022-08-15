package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.ComponentEquivalenceTemplate

@ReactionsEquivalenceTest
class ComponentEquivalenceTest extends ComponentEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
}
