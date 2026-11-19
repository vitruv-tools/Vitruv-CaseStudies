package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;

import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelTester;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlModelTester extends DomainModelTester {

    private final UmlTestHelper umlTestHelper;

    public UmlModelTester(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
        super(vitruvApplicationTestAdapter);
        this.umlTestHelper = new UmlTestHelper(vitruvApplicationTestAdapter);
    }

    @Override
    public void createAndSynchronizeModel(EObject rootObject) {
        switch (rootObject) {
            case Model model -> umlTestHelper.createAndSynchronizeUmlModel(model);
            case null, default -> throw new IllegalStateException("Unhandled UML root object: " + rootObject);
        }
    }

    @Override
    public void assertModelExists(EObject rootObject) {
        switch (rootObject) {
            case Model model -> umlTestHelper.assertUmlModelExists(model);
            case null, default -> throw new IllegalStateException("Unhandled UML root object: " + rootObject);
        }
    }
}
