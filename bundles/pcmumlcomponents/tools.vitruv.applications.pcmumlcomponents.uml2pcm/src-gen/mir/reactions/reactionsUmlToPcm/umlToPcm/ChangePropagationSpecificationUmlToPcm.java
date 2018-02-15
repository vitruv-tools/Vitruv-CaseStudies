package mir.reactions.reactionsUmlToPcm.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationUmlToPcm extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationUmlToPcm() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsUmlToPcm.umlToPcm.ExecutorUmlToPcm());
  }
}
