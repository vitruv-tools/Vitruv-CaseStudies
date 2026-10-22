package tools.vitruv.applications.simulinkautosar.tests.autosar2simulink;

import edu.kit.ipd.sdq.metamodels.autosar.AutoSARFactory;
import edu.kit.ipd.sdq.metamodels.autosar.AutoSARModel;
import edu.kit.ipd.sdq.metamodels.autosar.SwComponent;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import tools.vitruv.applications.simulinkautosar.tests.SimuLinkAutoSARTransformationTest;

public abstract class AbstractAutoSARToSimuLinkTest extends SimuLinkAutoSARTransformationTest {

	@BeforeEach
	protected void setup() throws Exception {
		createAutoSARModel(model -> model.setName(AUTOSAR_MODEL_NAME));
	}

	protected void createAutoSARModel(Consumer<AutoSARModel> autoSARModelInitialization) throws Exception {
		viewFactory.changeSimuLinkView(view -> {
			AutoSARModel autosarModel = AutoSARFactory.eINSTANCE.createAutoSARModel();
			createAndRegisterRoot(view, autosarModel, getUri(getProjectModelPath(AUTOSAR_MODEL_NAME)));
			autoSARModelInitialization.accept(autosarModel);
		});
	}

	protected void changeAutoSARModel(Consumer<AutoSARModel> modelModification) throws Exception {
		viewFactory.changeAutoSARView(view -> modelModification.accept(getDefaultAutoSARModel(view)));
	}

	private void addComponentToRootModel(SwComponent newComponent, String name) throws Exception {
		changeAutoSARModel(model -> {
			newComponent.setName(name);
			model.getSwcomponent().add(newComponent);
		});
	}

	protected void createAtomicSWComponentInModel(String componentName) throws Exception {
		addComponentToRootModel(AutoSARFactory.eINSTANCE.createAtomicSwComponent(), componentName);
		equalityValidation.assertSwComponentWithNameInRootModel(componentName);
	}

	protected void createCompositeSWComponentInModel(String componentName) throws Exception {
		addComponentToRootModel(AutoSARFactory.eINSTANCE.createCompositeSwComponent(), componentName);
	}
}
