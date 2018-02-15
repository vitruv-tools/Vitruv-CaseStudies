package mir.reactions.reactionsUmlToUml.comp2class;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;

@SuppressWarnings("all")
class ExecutorUmlToUml extends AbstractReactionsExecutor {
  public ExecutorUmlToUml() {
    super(new tools.vitruv.domains.uml.UmlDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.comp2class.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.CreatedCompModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RenamedComponentModelReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RenamedElementReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.ElementVisibilityChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.CreatedUmlComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RenameComponentReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.DeletedCompReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.CreatedDatatypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypePropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypePropertyReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedDataTypeOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.ChangedDataTypeOperationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.AddedRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RemovedInterfaceRealizationReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
    this.addReaction(new mir.reactions.reactionsUmlToUml.comp2class.RemovedUsageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("comp2class"))));
  }
}
