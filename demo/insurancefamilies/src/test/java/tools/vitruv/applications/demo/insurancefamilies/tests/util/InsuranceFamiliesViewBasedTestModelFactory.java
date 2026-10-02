package tools.vitruv.applications.demo.insurancefamilies.tests.util;

import static tools.vitruv.applications.demo.insurancefamilies.tests.util.TestModelViewAdapter.createTestModelAdapter;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import edu.kit.ipd.sdq.metamodels.insurance.InsuranceDatabase;
import java.util.Set;
import java.util.function.Consumer;
import tools.vitruv.dsls.testutils.TestModel;
import tools.vitruv.framework.testutils.integration.TestViewFactory;
import tools.vitruv.framework.views.View;
import tools.vitruv.framework.views.ViewProvider;

public class InsuranceFamiliesViewBasedTestModelFactory extends TestViewFactory implements InsuranceFamiliesTestModelFactory {

	public InsuranceFamiliesViewBasedTestModelFactory(ViewProvider viewProvider) {
		super(viewProvider);
	}

	private View createInsuranceView() {
		return createViewOfElements("insurance", Set.<Class<?>>of(InsuranceDatabase.class));
	}

	private View createFamilyView() {
		return createViewOfElements("families", Set.<Class<?>>of(FamilyRegister.class));
	}

	@Override
	public void changeInsuranceModel(Consumer<TestModel<InsuranceDatabase>> modelModification) {
		View insuranceView = createInsuranceView();
		runRethrowingUnchecked(() -> changeViewRecordingChanges(insuranceView, view ->
			modelModification.accept(createTestModelAdapter(insuranceView, InsuranceDatabase.class))
		));
	}

	@Override
	public void changeFamilyModel(Consumer<TestModel<FamilyRegister>> modelModification) {
		View familyView = createFamilyView();
		runRethrowingUnchecked(() -> changeViewRecordingChanges(familyView, view ->
			modelModification.accept(createTestModelAdapter(familyView, FamilyRegister.class))
		));
	}

	@Override
	public void validateInsuranceModel(Consumer<TestModel<InsuranceDatabase>> viewValidation) {
		View insuranceView = createInsuranceView();
		runRethrowingUnchecked(() -> validateView(insuranceView,
			view -> viewValidation.accept(createTestModelAdapter(insuranceView, InsuranceDatabase.class))));
	}

	@Override
	public void validateFamilyModel(Consumer<TestModel<FamilyRegister>> viewValidation) {
		View familyView = createFamilyView();
		runRethrowingUnchecked(() -> validateView(familyView,
			view -> viewValidation.accept(createTestModelAdapter(familyView, FamilyRegister.class))));
	}

	@FunctionalInterface
	private interface ThrowingRunnable {
		void run() throws Exception;
	}

	/**
	 * Runs the given action. Unchecked exceptions are passed on unchanged, so that tests can inspect them,
	 * checked exceptions are wrapped into an {@link IllegalStateException}.
	 */
	private static void runRethrowingUnchecked(ThrowingRunnable action) {
		try {
			action.run();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
