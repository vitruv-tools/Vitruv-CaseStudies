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
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.UmlAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.L2Reaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.L2Reaction(userInteracting));
    this.addReaction(mir.reactions.reactionsUmlToJava.umlToJavaAttribute.U2Reaction.getExpectedChangeType(), new mir.reactions.reactionsUmlToJava.umlToJavaAttribute.U2Reaction(userInteracting));
  }
}
