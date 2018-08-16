package mir.reactions.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlToPcm.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlToPcm.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedMultiplicityLowerValueReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedMultiplicityUpperValueReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedUmlModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedPrimitiveDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedPropertyForDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedPropertyFromDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedPropertyTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedInterfaceOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.AddedInterfaceOperationParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedParameterNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedParameterDirectionReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.AddedUsesRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedUsesRelationshipInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedUsesRelationshipInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedUsesRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedInterfaceRealizationRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedInterfaceRealizationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedInterfaceRealizationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedInterfaceRealizationRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlToPcm"))));
  }
}
