package mir.reactions.pcmCollectionDataTypeReactions;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class PcmCollectionDataTypeReactionsChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public PcmCollectionDataTypeReactionsChangePropagationSpecification() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.pcmCollectionDataTypeReactions.ReactionsExecutor());
  }
}
