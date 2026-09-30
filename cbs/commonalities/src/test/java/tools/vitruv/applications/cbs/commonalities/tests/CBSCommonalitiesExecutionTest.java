package tools.vitruv.applications.cbs.commonalities.tests;

import java.nio.file.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.cbs.commonalities.CbsCommonalitiesApplication;
import tools.vitruv.applications.cbs.commonalities.tests.util.DomainModelsProvider;
import tools.vitruv.applications.cbs.commonalities.tests.util.VitruvApplicationTestAdapter;
import tools.vitruv.applications.testutility.integration.NonTransactionalVitruvApplicationTest;
import tools.vitruv.applications.util.temporary.java.JavaSetup;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;

@ExtendWith(RegisterMetamodelsInStandalone.class)
public abstract class CBSCommonalitiesExecutionTest extends NonTransactionalVitruvApplicationTest {

	private ResourceSet validationResourceSet;

	private final VitruvApplicationTestAdapter vitruvApplicationTestAdapter = new VitruvApplicationTestAdapter() {
		/**
		 * Returns a resource from the runtime test project
		 */
		@Override
		public Resource getResourceAt(String modelPathInProject) {
			return validationResourceSet.getResource(getUri(Path.of(modelPathInProject)), true);
		}

		/**
		 * Returns a test resource from the test specification project (this project) rather than the runtime project
		 */
		@Override
		public Resource getTestResource(String resourcePathInExecutingProject) {
			return validationResourceSet.getResource(URI.createURI(resourcePathInExecutingProject), true);
		}

		@Override
		public <T extends EObject> T at(Class<T> type, URI uri) {
			return type.cast(validationResourceSet.getEObject(uri, true));
		}

		@Override
		public void createAndSynchronizeModel(String modelPathInProject, EObject rootElement) {
			Resource resource = startRecordingChanges(resourceAt(Path.of(modelPathInProject)));
			resource.getContents().add(rootElement);
			propagate();
			// This is a necessary hack, because the transformations recreate elements, such that the resulting models
			// are the same but new UUIDs are assigned.
			// Starting recording again reloads the UUIDs
			startRecordingChanges(resource);
		}
	};

	public <T> T getModels(DomainModelsProvider<T> modelsProvider) {
		return modelsProvider.getModels(vitruvApplicationTestAdapter);
	}

	@BeforeEach
	public void setupChangePropagationMode() {
		getVirtualModel().setChangePropagationMode(ChangePropagationMode.TRANSITIVE_EXCEPT_LEAVES);
	}

	@BeforeAll
	public static void setupJavaFactories() {
		JavaSetup.prepareFactories();
	}

	@BeforeEach
	protected void setupJaMoPP() {
		JavaSetup.resetClasspathAndRegisterStandardLibrary();
	}

	@Override
	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return new CbsCommonalitiesApplication().getChangePropagationSpecifications();
	}

	protected VitruvApplicationTestAdapter getVitruvApplicationTestAdapter() {
		return vitruvApplicationTestAdapter;
	}

	protected ResourceSet getValidationResourceSet() {
		return validationResourceSet;
	}

	@BeforeEach
	protected void setupResourceSets() {
		validationResourceSet = new ResourceSetImpl();
	}

	@AfterEach
	protected void cleanupResourceSets() {
		validationResourceSet.getResources().forEach(Resource::unload);
		validationResourceSet.getResources().clear();
		validationResourceSet = null;
	}
}
