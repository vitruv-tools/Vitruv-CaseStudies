package mir.reactions.umlXpcmSignature_L2R;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlXpcmSignature_L2RChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public UmlXpcmSignature_L2RChangePropagationSpecification() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlXpcmSignature_L2R.ReactionsExecutor());
  }
}
