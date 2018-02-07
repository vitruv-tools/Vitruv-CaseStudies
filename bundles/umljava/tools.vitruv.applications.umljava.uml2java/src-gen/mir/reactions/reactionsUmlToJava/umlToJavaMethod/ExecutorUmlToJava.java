package mir.reactions.reactionsUmlToJava.umlToJavaMethod;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorUmlToJava extends AbstractReactionsExecutor {
  public ExecutorUmlToJava() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.java.JavaDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlToJavaMethod.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodCreatedInDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlClassMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlDataTypeMethodDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodReturnTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlFeatureMadeStaticReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeAbstractReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlNamedElementRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterCreatedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlParameterDirectionKindChangedInvalidReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
    this.addReaction(new mir.reactions.reactionsUmlToJava.umlToJavaMethod.UmlInterfaceMethodMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"))));
  }
}
