package tools.vitruv.applications.demo.familiespersons.tests;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.propagation.ChangePropagationMode;
import tools.vitruv.change.testutils.TestProject;
import tools.vitruv.change.testutils.TestUserInteraction;
import tools.vitruv.change.testutils.views.UriMode;
import tools.vitruv.framework.testutils.integration.DefaultVirtualModelBasedTestView;
import tools.vitruv.framework.testutils.integration.VirtualModelBasedTestView;
import tools.vitruv.framework.vsum.VirtualModel;

public class PersonsToFamiliesApplicationTest extends PersonsToFamiliesTest implements VirtualModelBasedTestView {
	private VirtualModelBasedTestView virtualModelBasedTestView;

	@BeforeEach
	public void prepareVirtualModel(@TestProject Path testProjectPath, @TestProject(variant = "vsum") Path vsumPath) {
		virtualModelBasedTestView = new DefaultVirtualModelBasedTestView(
			testProjectPath,
			vsumPath,
			getChangePropagationSpecifications(),
			UriMode.FILE_URIS
		);
		virtualModelBasedTestView.getVirtualModel().setChangePropagationMode(ChangePropagationMode.SINGLE_STEP);
		setTestView(virtualModelBasedTestView);
	}

	// Delegation of the VirtualModelBasedTestView methods to the virtual model based test view

	@Override
	public void close() throws Exception {
		virtualModelBasedTestView.close();
	}

	@Override
	public <T extends EObject> T from(Class<T> clazz, Path viewRelativePath) {
		return virtualModelBasedTestView.from(clazz, viewRelativePath);
	}

	@Override
	public <T extends EObject> T from(Class<T> clazz, Resource resource) {
		return virtualModelBasedTestView.from(clazz, resource);
	}

	@Override
	public <T extends EObject> T from(Class<T> clazz, URI modelUri) {
		return virtualModelBasedTestView.from(clazz, modelUri);
	}

	@Override
	public URI getUri(Path viewRelativePath) {
		return virtualModelBasedTestView.getUri(viewRelativePath);
	}

	@Override
	public TestUserInteraction getUserInteraction() {
		return virtualModelBasedTestView.getUserInteraction();
	}

	@Override
	public VirtualModel getVirtualModel() {
		return virtualModelBasedTestView.getVirtualModel();
	}

	@Override
	public void moveTo(Resource resource, Path newViewRelativePath) {
		virtualModelBasedTestView.moveTo(resource, newViewRelativePath);
	}

	@Override
	public void moveTo(Resource resource, URI newUri) {
		virtualModelBasedTestView.moveTo(resource, newUri);
	}

	@Override
	public <T extends Notifier> List<PropagatedChange> propagate(T notifier, Consumer<T> consumer) {
		return virtualModelBasedTestView.propagate(notifier, consumer);
	}

	@Override
	public <T extends Notifier> T record(T notifier, Consumer<T> consumer) {
		return virtualModelBasedTestView.record(notifier, consumer);
	}

	@Override
	public Resource resourceAt(Path viewRelativePath) {
		return virtualModelBasedTestView.resourceAt(viewRelativePath);
	}

	@Override
	public Resource resourceAt(URI modelUri) {
		return virtualModelBasedTestView.resourceAt(modelUri);
	}
}
