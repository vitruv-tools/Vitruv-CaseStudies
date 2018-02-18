package mir.reactions.java2PcmClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class Java2PcmClassifierChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public Java2PcmClassifierChangePropagationSpecification() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.java2PcmClassifier.ReactionsExecutor());
  }
}
