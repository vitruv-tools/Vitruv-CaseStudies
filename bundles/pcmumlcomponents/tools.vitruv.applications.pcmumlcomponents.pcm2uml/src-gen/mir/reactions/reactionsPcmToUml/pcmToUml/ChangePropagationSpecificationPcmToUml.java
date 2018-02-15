package mir.reactions.reactionsPcmToUml.pcmToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationPcmToUml extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationPcmToUml() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsPcmToUml.pcmToUml.ExecutorPcmToUml());
  }
}
