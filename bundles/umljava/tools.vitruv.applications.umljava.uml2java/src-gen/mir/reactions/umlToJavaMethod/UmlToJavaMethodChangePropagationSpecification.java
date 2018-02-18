package mir.reactions.umlToJavaMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlToJavaMethodChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public UmlToJavaMethodChangePropagationSpecification() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlToJavaMethod.ReactionsExecutor());
  }
}
