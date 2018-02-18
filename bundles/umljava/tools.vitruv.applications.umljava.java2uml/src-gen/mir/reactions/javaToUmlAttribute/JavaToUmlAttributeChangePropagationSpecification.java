package mir.reactions.javaToUmlAttribute;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class JavaToUmlAttributeChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public JavaToUmlAttributeChangePropagationSpecification() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.javaToUmlAttribute.ReactionsExecutor());
  }
}
