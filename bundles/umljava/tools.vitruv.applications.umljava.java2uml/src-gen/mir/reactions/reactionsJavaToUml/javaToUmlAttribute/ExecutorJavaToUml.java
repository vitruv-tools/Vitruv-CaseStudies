package mir.reactions.reactionsJavaToUml.javaToUmlAttribute;

import tools.vitruv.domains.java.JavaDomainProvider;
import tools.vitruv.domains.uml.UmlDomainProvider;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new JavaDomainProvider().getDomain(), 
    	new UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    tools.vitruv.framework.userinteraction.UserInteracting userInteracting = getUserInteracting();
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInClassReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInClassReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInEnumReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInEnumReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeMadeNonFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlAttribute.JavaAttributeCreatedInInterfaceReaction(userInteracting));
  }
}
