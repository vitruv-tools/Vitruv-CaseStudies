package mir.reactions.pcm2java;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class Pcm2javaChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public Pcm2javaChangePropagationSpecification() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.pcm2java.ReactionsExecutor());
  }
}
