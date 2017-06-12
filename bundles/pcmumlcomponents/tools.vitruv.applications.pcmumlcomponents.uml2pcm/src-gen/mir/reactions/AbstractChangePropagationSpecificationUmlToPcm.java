package mir.reactions;

import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification;
import tools.vitruv.domains.pcm.PcmDomainProvider;

/**
 * The {@link class tools.vitruv.framework.change.processing.impl.CompositeChangePropagationSpecification} for transformations between the metamodels UML and PCM.
 * To add further change processors overwrite the setup method.
 */
public abstract class AbstractChangePropagationSpecificationUmlToPcm extends CompositeChangePropagationSpecification {
	public AbstractChangePropagationSpecificationUmlToPcm() {
		super(new UmlDomainProvider().getDomain(), 
			new PcmDomainProvider().getDomain());
		setup();
	}
	
	/**
	 * Adds the reactions change processors to this {@link AbstractChangePropagationSpecificationUmlToPcm}.
	 * For adding further change processors overwrite this method and call the super method at the right place.
	 */
	protected void setup() {
		this.addChangeMainprocessor(new mir.reactions.reactionsUmlToPcm.umlToPcm.ExecutorUmlToPcm());
	}
	
}
