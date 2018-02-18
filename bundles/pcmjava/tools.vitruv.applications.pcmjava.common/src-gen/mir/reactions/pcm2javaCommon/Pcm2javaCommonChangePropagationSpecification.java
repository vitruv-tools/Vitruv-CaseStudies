package mir.reactions.pcm2javaCommon;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class Pcm2javaCommonChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public Pcm2javaCommonChangePropagationSpecification() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.pcm2javaCommon.ReactionsExecutor());
  }
}
