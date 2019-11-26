package mir.reactions.umlXpcmRoles_R2L;

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
    return new mir.routines.umlXpcmRoles_R2L.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleInterfaceProvidingRequiringEntityInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleInterfaceProvidingRequiringEntityDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationRequiredRoleInsertedInInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationRequiredRoleRemovedFromInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationRequiredRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleOperationInterfaceReplacedAtOperationRequiredRole_requiredInterface__OperationRequiredRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnRequiredRoleEntityNameReplacedAtOperationRequiredRole_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleInterfaceProvidingRequiringEntityInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleInterfaceProvidingRequiringEntityDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationProvidedRoleInsertedInInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationProvidedRoleRemovedFromInterfaceProvidingRequiringEntityReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationProvidedRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleOperationInterfaceReplacedAtOperationProvidedRole_providedInterface__OperationProvidedRoleReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
    this.addReaction(new mir.reactions.umlXpcmRoles_R2L.OnProvidedRoleEntityNameReplacedAtOperationProvidedRole_entityNameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRoles_R2L"))));
  }
}
