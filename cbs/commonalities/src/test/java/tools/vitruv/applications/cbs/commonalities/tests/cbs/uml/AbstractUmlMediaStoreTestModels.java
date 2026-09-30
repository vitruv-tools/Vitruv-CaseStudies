package tools.vitruv.applications.cbs.commonalities.tests.cbs.uml;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.applications.cbs.commonalities.tests.cbs.MediaStoreTest;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModel;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlTestModelsBase;

abstract class AbstractUmlMediaStoreTestModels extends UmlTestModelsBase implements MediaStoreTest.DomainModels {

	protected AbstractUmlMediaStoreTestModels(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		super(vitruvApplicationTestAdapter);
	}

	protected abstract String getUmlMediaStoreModelPath();

	@Override
	public DomainModel mediaStoreCreation() {
		return newModel(() -> {
			Resource umlMediaStoreResource = vitruvApplicationTestAdapter.getTestResource(getUmlMediaStoreModelPath());
			Model umlMediaStoreModel = umlTestHelper.getUmlRootModel(umlMediaStoreResource);
			assertNotNull(umlMediaStoreModel, "Could not find UML MediaStore model!");
			return List.of(umlMediaStoreModel);
		});
	}
}
