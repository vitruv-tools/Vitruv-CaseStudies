package mir.reactions.umlXpcmInterface_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlXpcmInterface_R2LChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public UmlXpcmInterface_R2LChangePropagationSpecification() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlXpcmInterface_R2L.ReactionsExecutor());
  }
}
