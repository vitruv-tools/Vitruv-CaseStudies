package mir.reactions.reactionsUmlToPcm.umlToPcm;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
public class ExecutorUmlToPcm extends AbstractReactionsExecutor {
  public ExecutorUmlToPcm() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlToPcm.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerValueReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperValueReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityLowerReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedMultiplicityUpperReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedUmlModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPrimitiveDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedPropertyForDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedPropertyFromDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedPropertyTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.AddedInterfaceOperationParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterNameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedParameterDirectionReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.DeletedParameterReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.AddedUsesRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedUsesRelationshipInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedUsesRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.CreatedInterfaceRealizationRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.ChangedInterfaceRealizationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
    this.addReaction(new mir.reactions.reactionsUmlToPcm.umlToPcm.RemovedInterfaceRealizationRelationshipReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToPcm"))));
  }
}
