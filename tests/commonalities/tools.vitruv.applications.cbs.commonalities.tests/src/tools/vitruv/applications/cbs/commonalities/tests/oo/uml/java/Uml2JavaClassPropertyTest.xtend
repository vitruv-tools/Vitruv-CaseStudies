package tools.vitruv.applications.cbs.commonalities.tests.oo.uml.java

import org.emftext.language.java.modifiers.ModifiersFactory
import org.junit.Ignore
import org.junit.Test
import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractClassPropertyTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassPropertyTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassPropertyTestModels

class Uml2JavaClassPropertyTest extends AbstractClassPropertyTest {

	override protected getSourceModels() {
		return new UmlClassPropertyTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new JavaClassPropertyTestModels(vitruvApplicationTestAdapter) {
			// UML creates properties with no visibility by default, which maps to public visibility in Java.
			override defaultPropertyVisibility() {
				return ModifiersFactory.eINSTANCE.createPublic
			}
		}
	}

	@Ignore // TODO The comparison of classifier references in the expected and loaded Java models does not work yet.
	@Test
	override void stringClassPropertyCreation() {
		super.stringClassPropertyCreation()
	}
}
