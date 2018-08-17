package mir.reactions.pcmRequiredRoleReactions;

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
    return new mir.routines.pcmRequiredRoleReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmRequiredRoleReactions"))));
    this.addReaction(new mir.reactions.pcmRequiredRoleReactions.RequiredRoleInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmRequiredRoleReactions"))));
  }
}
