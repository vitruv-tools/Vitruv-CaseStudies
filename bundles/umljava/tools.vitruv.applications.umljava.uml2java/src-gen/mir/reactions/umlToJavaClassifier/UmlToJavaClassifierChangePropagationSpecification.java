package mir.reactions.umlToJavaClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class UmlToJavaClassifierChangePropagationSpecification extends AbstractReactionsChangePropagationSpecification {
  public UmlToJavaClassifierChangePropagationSpecification() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.umlToJavaClassifier.ReactionsExecutor());
  }
}
