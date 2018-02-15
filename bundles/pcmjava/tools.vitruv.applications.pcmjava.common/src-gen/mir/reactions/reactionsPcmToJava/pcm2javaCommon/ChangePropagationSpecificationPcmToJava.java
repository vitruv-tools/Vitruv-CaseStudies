package mir.reactions.reactionsPcmToJava.pcm2javaCommon;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationPcmToJava extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationPcmToJava() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsPcmToJava.pcm2javaCommon.ExecutorPcmToJava());
  }
}
