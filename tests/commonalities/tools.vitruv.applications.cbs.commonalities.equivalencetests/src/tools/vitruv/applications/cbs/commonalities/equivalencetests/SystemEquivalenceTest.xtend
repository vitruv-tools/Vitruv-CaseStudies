package tools.vitruv.applications.cbs.commonalities.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.SystemEquivalenceTemplate

@CommonalitiesEquivalenceTest
class SystemEquivalenceTest extends SystemEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
}
