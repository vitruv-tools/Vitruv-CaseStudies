package tools.vitruv.applications.cbs.commonalities.tests.oo.uml.java

import org.emftext.language.java.modifiers.ModifiersFactory
import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractClassTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassTestModels

class Uml2JavaClassTest extends AbstractClassTest {

	override protected getSourceModels() {
		return new UmlClassTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaClassTestModels(vitruvApplicationTestAdapter) {
			// UML creates classes with no visibility by default, which maps to public visibility in Java.
			override defaultClassVisibility() {
				return ModifiersFactory.eINSTANCE.createPublic
			}
		}
	}
}
