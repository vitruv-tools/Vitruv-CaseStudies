package tools.vitruv.applications.cbs.commonalities.tests.util.uml;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.contains;
import static tools.vitruv.applications.cbs.commonalities.tests.util.ModelMatchers.ignoringUnsetFeatures;
import static tools.vitruv.applications.cbs.commonalities.tests.util.uml.UmlFilePathHelper.umlModelFilePath;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;

public class UmlTestHelper {

	private final VitruvApplicationTestAdapter vitruvApplicationTestAdapter;

	public UmlTestHelper(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null");
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter;
	}

	public Model createAndSynchronizeUmlModel(Model umlModel) {
		vitruvApplicationTestAdapter.createAndSynchronizeModel(umlModelFilePath(umlModel), umlModel);
		return umlModel;
	}

	public Resource umlModelResource(Model umlModel) {
		return vitruvApplicationTestAdapter.getResourceAt(umlModelFilePath(umlModel));
	}

	public Model getUmlRootModel(Resource umlModelResource) {
		assertTrue(umlModelResource.getContents().size() == 1,
				"Expecting resource to contain exactly 1 root object: " + umlModelResource.getURI());
		Model umlModel = (Model) (umlModelResource.getContents().isEmpty() ? null
				: umlModelResource.getContents().get(0));
		assertNotNull(umlModel, "Could not find UML root model in resource: " + umlModelResource.getURI());
		return umlModel;
	}

	public void assertUmlModelExists(Model umlModel) {
		assertThat(umlModelResource(umlModel), contains(umlModel, ignoringUnsetFeatures()));
	}
}
