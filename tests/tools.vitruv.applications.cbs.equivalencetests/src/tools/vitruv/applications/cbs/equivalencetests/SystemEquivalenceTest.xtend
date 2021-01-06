package tools.vitruv.applications.cbs.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.SystemEquivalenceTemplate

@ReactionsEquivalenceTest
class SystemEquivalenceTest extends SystemEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
}
