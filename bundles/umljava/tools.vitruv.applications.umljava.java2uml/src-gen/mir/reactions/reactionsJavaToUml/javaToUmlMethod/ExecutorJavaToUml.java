package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

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
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaInterfaceMethodCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMemberDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMemberDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonAbstractReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadeStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonStaticReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterDeletedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaReturnTypeChangedReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeFinalReaction(userInteracting));
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonFinalReaction(userInteracting));
  }
}
