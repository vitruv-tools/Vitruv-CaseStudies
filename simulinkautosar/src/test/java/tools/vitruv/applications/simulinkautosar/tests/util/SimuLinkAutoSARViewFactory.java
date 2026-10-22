package tools.vitruv.applications.simulinkautosar.tests.util;

import edu.kit.ipd.sdq.metamodels.autosar.AutoSARModel;
import edu.kit.ipd.sdq.metamodels.simulink.SimulinkModel;
import java.util.Set;
import java.util.function.Consumer;
import tools.vitruv.framework.testutils.integration.TestViewFactory;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewProvider;

public class SimuLinkAutoSARViewFactory extends TestViewFactory {

	public SimuLinkAutoSARViewFactory(ViewProvider viewProvider) {
		super(viewProvider);
	}

	private View createAutoSARView() {
		return createViewOfElements("AutoSAR", Set.of(AutoSARModel.class));
	}

	private View createSimuLinkView() {
		return createViewOfElements("SimuLink", Set.of(SimulinkModel.class));
	}

	private View createAutoSARAndSimuLinkModelView() {
		return createViewOfElements("AutoSAR and SimuLink classes", Set.of(AutoSARModel.class, SimulinkModel.class));
	}

	/**
	 * Changes the AutoSAR view containing all AutoSAR models as root elements
	 * according to the given modification function.
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 */
	public void changeAutoSARView(Consumer<CommittableView> modelModification) throws Exception {
		changeViewRecordingChanges(createAutoSARView(), modelModification);
	}

	/**
	 * Changes the SimuLink view containing all SimuLink packages and classes as root elements
	 * according to the given modification function.
	 * Records the performed changes, commits the recorded changes, and closes the view afterwards.
	 */
	public void changeSimuLinkView(Consumer<CommittableView> modelModification) throws Exception {
		changeViewRecordingChanges(createSimuLinkView(), modelModification);
	}

	/**
	 * Validates the AutoSAR view containing all AutoSAR models by applying the validation function
	 * and closes the view afterwards.
	 */
	public void validateAutoSARView(Consumer<View> viewValidation) throws Exception {
		validateView(createAutoSARView(), viewValidation);
	}

	/**
	 * Validates the SimuLink view containing all packages and classes by applying the validation function
	 * and closes the view afterwards.
	 */
	public void validateSimuLinkView(Consumer<View> viewValidation) throws Exception {
		validateView(createSimuLinkView(), viewValidation);
	}

	/**
	 * Validates the SimuLink and AutoSAR view containing all AutoSAR models and SimuLink models by applying the
	 * validation function and closes the view afterwards.
	 */
	public void validateAutoSARAndSimuLinkClassesView(Consumer<View> viewValidation) throws Exception {
		validateView(createAutoSARAndSimuLinkModelView(), viewValidation);
	}
}
