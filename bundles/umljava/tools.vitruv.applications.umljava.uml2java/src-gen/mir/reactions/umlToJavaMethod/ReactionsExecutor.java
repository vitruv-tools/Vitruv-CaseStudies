package mir.reactions.umlToJavaMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlToJavaMethod.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlClassMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlDataTypeMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlFeatureMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlReturnParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedFromInReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedFromReturnReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedToInReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionChangedToReturnReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJava.umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
    this.addReaction(new mir.reactions.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaMethod"))));
  }
}
