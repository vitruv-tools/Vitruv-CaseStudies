package mir.reactions.reactionsJavaToUml.javaToUmlAttribute;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml(final UserInteracting userInteracting) {
    super(userInteracting,
    	new JavaDomainProvider().getDomain(), 
    	new UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction(userInteracting));
  }
}
