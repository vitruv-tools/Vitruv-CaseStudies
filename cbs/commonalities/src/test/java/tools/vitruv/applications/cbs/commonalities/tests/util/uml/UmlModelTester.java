package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlModelTester extends DomainModelTester {

	private final UmlTestHelper umlTestHelper = new UmlTestHelper(vitruvApplicationTestAdapter);

	public UmlModelTester(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	@Override
	public void createAndSynchronizeModel(EObject rootObject) {
		if (rootObject instanceof Model umlModel) {
			umlTestHelper.createAndSynchronizeUmlModel(umlModel);
		} else {
			throw new IllegalStateException("Unhandled UML root object: " + rootObject);
		}
	}

	@Override
	public void assertModelExists(EObject rootObject) {
		if (rootObject instanceof Model umlModel) {
			umlTestHelper.assertUmlModelExists(umlModel);
		} else {
			throw new IllegalStateException("Unhandled UML root object: " + rootObject);
		}
	}
}
