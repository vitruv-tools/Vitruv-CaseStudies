package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new UmlDomainProvider().getDomain(), 
    	new JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromDataTypeReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromDataTypeReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeRenamedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInInterfaceReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlInterfaceMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlInterfaceMadeFinalReaction(userInteracting));
  }
}
