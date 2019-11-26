package mir.reactions.umlReturnAndRegularParameterTypeReactions;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;
import tools.vitruv.framework.change.processing.ChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlReturnAndRegularParameterTypeReactionsChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification implements ChangePropagationSpecification {
  public UmlReturnAndRegularParameterTypeReactionsChangePropagationSpecification() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlReturnAndRegularParameterTypeReactions.ReactionsExecutor());
  }
}
