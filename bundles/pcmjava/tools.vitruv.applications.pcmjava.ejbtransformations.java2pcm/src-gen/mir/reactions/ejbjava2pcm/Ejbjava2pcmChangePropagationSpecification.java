package mir.reactions.ejbjava2pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class Ejbjava2pcmChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public Ejbjava2pcmChangePropagationSpecification() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.ejbjava2pcm.ReactionsExecutor());
  }
}
