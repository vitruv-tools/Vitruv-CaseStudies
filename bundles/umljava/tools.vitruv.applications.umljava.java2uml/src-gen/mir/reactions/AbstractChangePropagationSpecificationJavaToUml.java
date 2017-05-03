package mir.reactions;

import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels Java and UML.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationJavaToUml extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationJavaToUml() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor(),
			new JavaDomainProvider().getDomain(), 
			new UmlDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationJavaToUml}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.ExecutorJavaToUml(getUserInteracting()));
		this.addChangeMainprocessor(new mir.reactions.reactionsJavaToUml.javaToUmlClassifier.ExecutorJavaToUml(getUserInteracting()));
		this.addChangeMainprocessor(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.ExecutorJavaToUml(getUserInteracting()));
	}
	
}
