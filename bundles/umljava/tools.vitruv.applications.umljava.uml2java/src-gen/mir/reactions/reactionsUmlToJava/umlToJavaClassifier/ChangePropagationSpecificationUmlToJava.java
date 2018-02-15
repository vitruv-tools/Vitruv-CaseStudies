package mir.reactions.reactionsUmlToJava.umlToJavaClassifier;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsChangePropagationSpecification;

@SuppressWarnings("all")
public class ChangePropagationSpecificationUmlToJava extends AbstractReactionsChangePropagationSpecification {
  public ChangePropagationSpecificationUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addChangeMainprocessor(new mir.reactions.reactionsUmlToJava.umlToJavaClassifier.ExecutorUmlToJava());
  }
}
