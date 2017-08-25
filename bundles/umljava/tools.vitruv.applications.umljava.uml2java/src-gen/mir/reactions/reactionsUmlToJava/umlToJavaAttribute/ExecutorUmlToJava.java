package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInClassReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromDataTypeReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeRenamedReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInInterfaceReaction());
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlInterfaceMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlInterfaceMadeFinalReaction());
  }
}
