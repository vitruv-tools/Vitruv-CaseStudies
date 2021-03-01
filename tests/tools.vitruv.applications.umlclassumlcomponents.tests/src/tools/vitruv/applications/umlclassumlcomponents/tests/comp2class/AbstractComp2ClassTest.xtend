package tools.vitruv.applications.umlclassumlcomponents.tests.comp2class

import tools.vitruv.applications.umlclassumlcomponents.comp2class.UmlComp2UmlClassChangePropagation

import tools.vitruv.applications.umlclassumlcomponents.tests.util.AbstractUmlClassCompTest

abstract class AbstractComp2ClassTest extends AbstractUmlClassCompTest {

	override protected getChangePropagationSpecifications() {
		return #[new UmlComp2UmlClassChangePropagation()]
	}

}
