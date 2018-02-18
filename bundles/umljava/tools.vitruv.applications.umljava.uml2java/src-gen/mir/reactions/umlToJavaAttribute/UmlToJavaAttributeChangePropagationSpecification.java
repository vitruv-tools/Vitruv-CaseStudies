package mir.reactions.umlToJavaAttribute;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlToJavaAttributeChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public UmlToJavaAttributeChangePropagationSpecification() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlToJavaAttribute.ReactionsExecutor());
  }
}
