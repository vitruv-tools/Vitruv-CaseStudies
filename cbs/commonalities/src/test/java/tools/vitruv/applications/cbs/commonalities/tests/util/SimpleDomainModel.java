package tools.vitruv.applications.cbs.commonalities.tests.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.function.Supplier;
import org.eclipse.emf.ecore.EObject;

/**
 * An implementation of {@link DomainModel} which uses a {@link Supplier} for
 * the construction of the model and a domain specific but test case
 * independent {@link DomainModelTester} to create and check the model.
 * <p>
 * This can be used if there are no differences in the construction of the
 * created (source) and checked (target) models and if the creation and
 * checking of the model is independent of the current test case.
 */
public class SimpleDomainModel implements DomainModel {

	private final DomainModelTester modelTester;
	private final Supplier<List<? extends EObject>> modelCreator;

	public SimpleDomainModel(DomainModelTester modelTester, Supplier<List<? extends EObject>> modelCreator) {
		checkNotNull(modelTester, "modelTester is null");
		this.modelTester = modelTester;
		checkNotNull(modelCreator, "modelCreator is null");
		this.modelCreator = modelCreator;
	}

	@Override
	public void createAndSynchronize() {
		modelTester.createAndSynchronizeModels(modelCreator.get());
	}

	@Override
	public void check() {
		modelTester.assertModelsExist(modelCreator.get());
	}
}
