package tools.vitruv.applications.umlclassumlcomponents.tests.class2comp

import tools.vitruv.applications.umlclassumlcomponents.class2comp.UmlClass2UmlCompChangePropagation

import tools.vitruv.applications.umlclassumlcomponents.tests.util.AbstractUmlClassCompTest

abstract class AbstractClass2CompTest extends AbstractUmlClassCompTest {

	override protected getChangePropagationSpecifications() {
		return #[new UmlClass2UmlCompChangePropagation()]
	}

}
