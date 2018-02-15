package mir.reactions.reactionsJavaToPcm.ejbjava2pcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationJavaToPcm extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationJavaToPcm() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsJavaToPcm.ejbjava2pcm.ExecutorJavaToPcm());
  }
}
