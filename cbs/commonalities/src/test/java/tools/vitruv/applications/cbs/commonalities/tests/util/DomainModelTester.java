package tools.vitruv.applications.cbs.commonalities.tests.util;

import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.emf.ecore.EObject;

/**
 * Handles the domain specific but test case independent aspects of creating
 * and checking models for test cases.
 */
public abstract class DomainModelTester {

	protected final VitruvApplicationTestAdapter vitruvApplicationTestAdapter;

	protected DomainModelTester(VitruvApplicationTestAdapter vitruvApplicationTestAdapter) {
		checkNotNull(vitruvApplicationTestAdapter, "vitruvApplicationTestAdapter is null");
		this.vitruvApplicationTestAdapter = vitruvApplicationTestAdapter;
	}

	public abstract void createAndSynchronizeModel(EObject rootObject);

	public void createAndSynchronizeModels(Iterable<? extends EObject> rootObjects) {
		rootObjects.forEach(this::createAndSynchronizeModel);
	}

	public abstract void assertModelExists(EObject model);

	public void assertModelsExist(Iterable<? extends EObject> rootObjects) {
		rootObjects.forEach(this::assertModelExists);
	}
}
