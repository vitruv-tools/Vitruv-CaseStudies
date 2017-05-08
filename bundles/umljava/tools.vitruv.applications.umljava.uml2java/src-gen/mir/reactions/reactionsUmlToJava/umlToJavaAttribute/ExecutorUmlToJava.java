package mir.reactions.reactionsUmlToJava.umlToJavaAttribute;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava(final UserInteracting userInteracting) {
    super(userInteracting,
    	new UmlDomainProvider().getDomain(), 
    	new JavaDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInEnumReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedInEnumReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromEnumReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedFromEnumReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction(userInteracting));
  }
}
