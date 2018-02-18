package mir.reactions.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlToPcmChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public UmlToPcmChangePropagationSpecification() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlToPcm.ReactionsExecutor());
  }
}
