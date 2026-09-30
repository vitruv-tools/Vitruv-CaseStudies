package tools.vitruv.applications.cbs.testutils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * Creates model elements of a metamodel using its {@link EFactory}.
 * Replaces the {@code @ModelCreators} active annotation used by the former Xtend creators.
 */
public abstract class FactoryCreators {
	private final EFactory factory;

	protected FactoryCreators(EFactory factory) {
		this.factory = factory;
	}

	public EClassifier classifier(String classifierName) {
		return checkNotNull(factory.getEPackage().getEClassifier(classifierName),
				"There is no classifier called '%s' in '%s'!", classifierName, factory.getEPackage().getName());
	}

	public EObject create(String className) {
		EClassifier requestedClassifier = classifier(className);
		checkArgument(requestedClassifier instanceof EClass,
				"%s is not an EClass and can thus not be instantiated!", className);
		return factory.create((EClass) requestedClassifier);
	}

	public <M extends EObject> M create(Class<? extends M> clazz) {
		return clazz.cast(create(clazz.getSimpleName()));
	}
}
