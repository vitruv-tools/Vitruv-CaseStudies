package tools.vitruv.applications.cbs.commonalities.tests.oo.java.uml

import org.eclipse.uml2.uml.VisibilityKind
import tools.vitruv.applications.cbs.commonalities.tests.oo.AbstractClassTest
import tools.vitruv.applications.cbs.commonalities.tests.oo.java.JavaClassTestModels
import tools.vitruv.applications.cbs.commonalities.tests.oo.uml.UmlClassTestModels

class Java2UmlClassTest extends AbstractClassTest {

	override protected getSourceModels() {
		return new JavaClassTestModels(vitruvApplicationTestAdapter)
	}

	override protected getTargetModels() {
		return new UmlClassTestModels(vitruvApplicationTestAdapter) {
			// Java creates classes with package-private visibility by default.
			override defaultClassVisibility() {
				return VisibilityKind.PACKAGE_LITERAL
			}
		}
	}
}
