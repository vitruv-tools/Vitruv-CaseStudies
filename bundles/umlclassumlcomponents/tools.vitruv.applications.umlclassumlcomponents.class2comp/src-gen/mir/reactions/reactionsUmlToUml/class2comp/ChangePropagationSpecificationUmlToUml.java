package mir.reactions.reactionsUmlToUml.class2comp;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationUmlToUml extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationUmlToUml() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsUmlToUml.class2comp.ExecutorUmlToUml());
  }
}
