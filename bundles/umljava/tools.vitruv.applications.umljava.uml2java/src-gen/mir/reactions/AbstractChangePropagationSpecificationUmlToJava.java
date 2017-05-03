package mir.reactions;

import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels UML and Java.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationUmlToJava extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationUmlToJava() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor(),
			new UmlDomainProvider().getDomain(), 
			new JavaDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationUmlToJava}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.ExecutorUmlToJava(getUserInteracting()));
		this.addChangeMainprocessor(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.ExecutorUmlToJava(getUserInteracting()));
		this.addChangeMainprocessor(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.ExecutorUmlToJava(getUserInteracting()));
	}
	
}
