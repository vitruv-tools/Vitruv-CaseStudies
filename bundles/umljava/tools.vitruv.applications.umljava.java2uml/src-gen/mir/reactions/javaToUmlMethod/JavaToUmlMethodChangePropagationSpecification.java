package mir.reactions.javaToUmlMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class JavaToUmlMethodChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public JavaToUmlMethodChangePropagationSpecification() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.javaToUmlMethod.ReactionsExecutor());
  }
}
