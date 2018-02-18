package mir.reactions.java2Pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class Java2PcmChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public Java2PcmChangePropagationSpecification() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.java2Pcm.ReactionsExecutor());
  }
}
