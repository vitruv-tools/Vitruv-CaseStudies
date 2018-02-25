package mir.reactions.javaToUmlMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.java.JavaDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.javaToUmlMethod.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaClassMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaInterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaConstructorCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMemberDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeNonAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeNonFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaElementMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaMethodMadeNonStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaElementMadePackagePrivateReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaClassMethodCreatedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
    this.addReaction(new mir.reactions.javaToUmlMethod.JavaParameterMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("JavaToUmlMethod"))));
  }
}
