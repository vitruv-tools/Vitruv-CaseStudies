package mir.reactions.umlXpcmDatatypes_R2L;

import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionsExecutor;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
class ReactionsExecutor extends AbstractReactionsExecutor {
  public ReactionsExecutor() {
    super(new tools.vitruv.domains.pcm.PcmDomainProvider().getDomain(), 
    	new tools.vitruv.domains.uml.UmlDomainProvider().getDomain());
  }
  
  protected RoutinesFacadesProvider createRoutinesFacadesProvider() {
    return new mir.routines.umlXpcmDatatypes_R2L.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeRepositoryInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeRepositoryDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeCompositeDataTypeInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeCompositeDataTypeRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeCompositeDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDatatypeParentCompositeDataTypeInsertedInCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDatatypeParentCompositeDataTypeRemovedFromCompositeDataTypeReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_R2L.OnCompositeDatatypeParentCompositeDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_R2L"))));
  }
}
