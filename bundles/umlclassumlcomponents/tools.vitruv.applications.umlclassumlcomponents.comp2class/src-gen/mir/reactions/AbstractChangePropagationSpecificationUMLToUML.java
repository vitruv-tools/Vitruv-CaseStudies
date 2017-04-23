package mir.reactions;

import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels UML and UML.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationUmlToUml extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationUmlToUml() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor(),
			new UmlDomainProvider().getDomain(), 
			new UmlDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationUmlToUml}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsUmlToUml.comp2class.ExecutorUmlToUml(getUserInteracting()));
	}
	
}
