package mir.reactions.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class PcmToUmlChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public PcmToUmlChangePropagationSpecification() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.pcmToUml.ReactionsExecutor());
  }
}
