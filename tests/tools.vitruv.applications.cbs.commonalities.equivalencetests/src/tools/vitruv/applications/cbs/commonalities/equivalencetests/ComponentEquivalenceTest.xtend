package tools.vitruv.applications.cbs.commonalities.equivalencetests

import tools.vitruv.applications.cbs.equivalencetesttemplates.ComponentEquivalenceTemplate
import tools.vitruv.applications.cbs.commonalities.equivalencetests.CommonalitiesEquivalenceTest

@CommonalitiesEquivalenceTest
class ComponentEquivalenceTest extends ComponentEquivalenceTemplate {
	override protected getRepository() {
		new RepositoryEquivalenceTest
	}
}
