package tools.vitruv.applications.simulinkautosar.tests;

import static tools.vitruv.applications.simulinkautosar.tests.util.AutoSARQueryUtil.claimAutoSARModel;
import static tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkQueryUtil.claimSimuLinkModel;

import edu.kit.ipd.sdq.metamodels.autosar.AutoSARModel;
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkModel;
import java.nio.file.Path;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import tools.vitruv.applications.simulinkautosar.AutoSARToSimuLinkChangePropagationSpecification;
import tools.vitruv.applications.simulinkautosar.SimuLinkToAutoSARChangePropagationSpecification;
import tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkAutoSARClassifierEqualityValidation;
import tools.vitruv.applications.simulinkautosar.tests.util.SimuLinkAutoSARViewFactory;
import tools.vitruv.change.propagation.ChangePropagationSpecification;
import tools.vitruv.change.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.framework.testutils.integration.ViewBasedVitruvApplicationTest;
import tools.vitruv.framework.views.View;

@ExtendWith(RegisterMetamodelsInStandalone.class)
public abstract class SimuLinkAutoSARTransformationTest extends ViewBasedVitruvApplicationTest {

	protected static final String MODEL_FILE_EXTENSION = "arxml";
	protected static final String SIMULINK_MODEL_NAME = "Model";
	protected static final String AUTOSAR_MODEL_NAME = "Model";
	protected static final String MODEL_FOLDER_NAME = "model";

	protected SimuLinkAutoSARViewFactory viewFactory;

	// A lambda (not a method reference) is required, because the view factory is only created in setupViewFactory()
	protected final SimuLinkAutoSARClassifierEqualityValidation equalityValidation = new SimuLinkAutoSARClassifierEqualityValidation(
			SIMULINK_MODEL_NAME,
			viewApplication -> this.viewFactory.validateAutoSARAndSimuLinkClassesView(viewApplication));

	@BeforeAll
	public static void setupSimuLinkFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
	}

	@BeforeEach
	public final void setupViewFactory() {
		viewFactory = new SimuLinkAutoSARViewFactory(getVirtualModel());
	}

	@Override
	protected boolean enableTransitiveCyclicChangePropagation() {
		return false;
	}

	protected SimulinkModel getDefaultSimuLinkModel(View view) {
		return claimSimuLinkModel(view, SIMULINK_MODEL_NAME);
	}

	protected AutoSARModel getDefaultAutoSARModel(View view) {
		return claimAutoSARModel(view, AUTOSAR_MODEL_NAME);
	}

	protected Path getProjectModelPath(String modelName) {
		return Path.of(MODEL_FOLDER_NAME).resolve(modelName + "." + MODEL_FILE_EXTENSION);
	}

	@Override
	protected Iterable<ChangePropagationSpecification> getChangePropagationSpecifications() {
		return List.of(new AutoSARToSimuLinkChangePropagationSpecification(),
				new SimuLinkToAutoSARChangePropagationSpecification());
	}

	protected void createAndRegisterRoot(View view, EObject rootObject, URI persistenceUri) {
		view.registerRoot(rootObject, persistenceUri);
	}
}
