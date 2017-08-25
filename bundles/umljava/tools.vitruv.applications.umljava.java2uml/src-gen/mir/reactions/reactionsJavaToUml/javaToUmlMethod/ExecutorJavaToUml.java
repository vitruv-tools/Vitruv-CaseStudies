package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaInterfaceMethodCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaInterfaceMethodCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaConstructorCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaConstructorCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMemberDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMemberDeletedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeAbstractReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonAbstractReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonAbstractReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeFinalReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonFinalReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadeStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadeStaticReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonStaticReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonStaticReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterDeletedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterDeletedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaReturnTypeChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaReturnTypeChangedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementVisibilityChangedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementVisibilityChangedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadePackagePrivateReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadePackagePrivateReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaNamedElementRenamedReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaNamedElementRenamedReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedInInterfaceReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedInInterfaceReaction());
    this.addReaction(mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterMadeFinalReaction.getExpectedChangeType(), new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterMadeFinalReaction());
  }
}
