package mir.reactions.reactionsJavaToUml.javaToUmlMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
class ExecutorJavaToUml extends AbstractReactionsExecutor {
  public ExecutorJavaToUml() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.javaToUmlMethod.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaInterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaConstructorCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMemberDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaMethodMadeNonStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaElementMadePackagePrivateReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaClassMethodCreatedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.reactionsJavaToUml.javaToUmlMethod.JavaParameterMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
  }
}
