package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;

@SuppressWarnings("all")
public class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaInterfaceMethodCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaConstructorCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMemberDeletedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeAbstractReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonAbstractReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeFinalReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonFinalReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadeStaticReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonStaticReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterDeletedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaReturnTypeChangedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementVisibilityChangedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadePackagePrivateReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaNamedElementRenamedReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedInInterfaceReaction());
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterMadeFinalReaction());
  }
}
