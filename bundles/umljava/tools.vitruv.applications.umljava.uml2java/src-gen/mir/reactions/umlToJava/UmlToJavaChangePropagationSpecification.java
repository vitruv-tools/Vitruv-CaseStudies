package mir.reactions.umlToJava;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlToJavaChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public UmlToJavaChangePropagationSpecification() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlToJava.ReactionsExecutor());
  }
}
