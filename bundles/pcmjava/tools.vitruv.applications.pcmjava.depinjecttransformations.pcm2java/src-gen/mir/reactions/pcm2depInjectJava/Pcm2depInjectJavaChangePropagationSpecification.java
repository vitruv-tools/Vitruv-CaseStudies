package mir.reactions.pcm2depInjectJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class Pcm2depInjectJavaChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public Pcm2depInjectJavaChangePropagationSpecification() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.pcm2depInjectJava.ReactionsExecutor());
  }
}
