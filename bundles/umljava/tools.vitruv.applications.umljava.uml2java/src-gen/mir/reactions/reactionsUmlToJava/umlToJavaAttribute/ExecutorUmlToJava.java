package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInClassReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromDataTypeReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeRenamedReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlInterfaceMadeFinalReaction());
  }
}
