package mir.reactions.pcm2EjbJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class Pcm2EjbJavaChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public Pcm2EjbJavaChangePropagationSpecification() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.pcm2EjbJava.ReactionsExecutor());
  }
}
