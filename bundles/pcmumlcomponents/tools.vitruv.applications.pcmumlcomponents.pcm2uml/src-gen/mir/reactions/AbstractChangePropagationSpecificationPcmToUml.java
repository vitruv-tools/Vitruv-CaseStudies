package mir.reactions;

import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import tools.vitruv.domains.pcm.PcmDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels PCM and UML.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationPcmToUml extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationPcmToUml() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor(),
			new PcmDomainProvider().getDomain(), 
			new UmlDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationPcmToUml}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsPcmToUml.pcmToUml.ExecutorPcmToUml(getUserInteracting()));
	}
	
}
