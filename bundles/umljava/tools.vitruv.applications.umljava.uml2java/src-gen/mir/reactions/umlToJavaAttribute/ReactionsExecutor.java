package mir.reactions.umlToJavaAttribute;

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
    return new mir.routines.umlToJavaAttribute.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeCreatedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeCreatedInDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeDeletedFromClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeDeletedFromDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlLowerMultiplicityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlUpperMultiplicityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlAttributeCreatedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
    this.addReaction(new mir.reactions.umlToJavaAttribute.UmlInterfaceMadeFinalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToJavaAttribute"))));
  }
}
