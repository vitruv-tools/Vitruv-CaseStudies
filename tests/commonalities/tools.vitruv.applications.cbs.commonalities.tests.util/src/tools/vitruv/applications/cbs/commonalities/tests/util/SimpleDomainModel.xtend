package tools.vitruv.applications.cbs.commonalities.tests.util

import java.util.List
import java.util.function.Supplier
import org.eclipse.emf.ecore.EObject

import static com.google.common.base.Preconditions.*

/**
 * An implementation of {@link DomainModel} which uses a {@link Supplier} for
 * the construction of the model and a domain specific but test case
 * independent {@link DomainModelTester} to create and check the model.
 * <p>
 * This can be used if there are no differences in the construction of the
 * created (source) and checked (target) models and if the creation and
 * checking of the model is independent of the current test case.
 */
class SimpleDomainModel implements DomainModel {

	val DomainModelTester modelTester
	val Supplier<List<? extends EObject>> modelCreator

	new(DomainModelTester modelTester, Supplier<List<? extends EObject>> modelCreator) {
		checkNotNull(modelTester, "modelTester is null")
		this.modelTester = modelTester
		checkNotNull(modelCreator, "modelCreator is null")
		this.modelCreator = modelCreator
	}

	override createAndSynchronize() {
		modelTester.createAndSynchronizeModels(modelCreator.get)
	}

	override check() {
		modelTester.assertModelsExist(modelCreator.get)
	}
}
