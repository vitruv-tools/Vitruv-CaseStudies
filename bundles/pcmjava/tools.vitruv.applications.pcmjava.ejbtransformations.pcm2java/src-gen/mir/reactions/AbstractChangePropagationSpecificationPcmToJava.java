package mir.reactions;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import tools.vitruv.domains.pcm.PcmDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels PCM and Java.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationPcmToJava extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationPcmToJava() {
		super(new tools.vitruv.framework.userinteraction.impl.UserInteractor(),
			new PcmDomainProvider().getDomain(), 
			new JavaDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationPcmToJava}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsPcmToJava.pcm2EjbJava.ExecutorPcmToJava(getUserInteracting()));
	}
	
}
