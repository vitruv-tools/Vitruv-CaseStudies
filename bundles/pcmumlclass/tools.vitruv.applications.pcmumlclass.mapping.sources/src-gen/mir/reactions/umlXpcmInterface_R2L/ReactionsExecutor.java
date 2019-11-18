package mir.reactions.umlXpcmInterface_R2L;

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
    return new mir.routines.umlXpcmInterface_R2L.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceRepositoryInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceRepositoryDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceOperationInterfaceInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceOperationInterfaceRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceEntityNameReplacedAtOperationInterface_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceInsertedInOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceRemovedFromOperationInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_R2L.OnOperationInterfaceParentOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_R2L"))));
  }
}
