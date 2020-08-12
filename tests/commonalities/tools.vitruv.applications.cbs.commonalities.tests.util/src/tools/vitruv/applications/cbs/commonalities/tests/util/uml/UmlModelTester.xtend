package tools.vitruv.applications.cbs.commonalities.tests.util.uml

import org.eclipse.emf.ecore.EObject
import org.eclipse.uml2.uml.Model
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestHelper

class UmlModelTester extends DomainModelTester {

	val extension UmlTestHelper umlTestHelper = new UmlTestHelper(vitruvApplicationTestAdapter)

	new(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter)
	}

	override createAndSynchronizeModel(EObject rootObject) {
		switch (rootObject) {
			Model: {
				rootObject.createAndSynchronizeUmlModel
			}
			default: {
				throw new IllegalStateException("Unhandled UML root object: " + rootObject)
			}
		}
	}

	override assertModelExists(EObject rootObject) {
		switch (rootObject) {
			Model: {
				rootObject.assertUmlModelExists
			}
			default: {
				throw new IllegalStateException("Unhandled UML root object: " + rootObject)
			}
		}
	}
}
