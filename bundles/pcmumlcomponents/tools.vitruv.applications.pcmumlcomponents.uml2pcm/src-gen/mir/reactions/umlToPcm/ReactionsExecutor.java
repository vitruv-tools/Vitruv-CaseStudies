package mir.reactions.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

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
    this.addReaction(new mir.reactions.umlToPcm.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedMultiplicityLowerValueReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedMultiplicityUpperValueReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedMultiplicityLowerReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedMultiplicityUpperReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedUmlModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedPrimitiveDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedPropertyForDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedPropertyFromDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedPropertyTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedInterfaceOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.AddedInterfaceOperationParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedParameterNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedParameterDirectionReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.DeletedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.AddedUsesRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedUsesRelationshipInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedUsesRelationshipInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedUsesRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.CreatedInterfaceRealizationRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.ChangedInterfaceRealizationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedInterfaceRealizationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.umlToPcm.RemovedInterfaceRealizationRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
  }
}
