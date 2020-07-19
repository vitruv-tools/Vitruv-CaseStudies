package tools.vitruv.applications.cbs.commonalities.tests.oo.java.uml

import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractClassPropertyTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassPropertyTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassPropertyTestModels

class Java2UmlClassPropertyTest extends AbstractClassPropertyTest {

	override protected getSourceModels() {
		return new JavaClassPropertyTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlClassPropertyTestModels(vitruvApplicationTestAdapter) {
			// Java creates properties with package-private visibility by default.
			override defaultPropertyVisibility() {
				return VisibilityKind.PACKAGE_LITERAL
			}
		}
	}
}
