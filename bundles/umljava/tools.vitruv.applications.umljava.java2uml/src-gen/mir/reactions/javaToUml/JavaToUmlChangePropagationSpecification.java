package mir.reactions.javaToUml;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class JavaToUmlChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public JavaToUmlChangePropagationSpecification() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.javaToUml.ReactionsExecutor());
  }
}
