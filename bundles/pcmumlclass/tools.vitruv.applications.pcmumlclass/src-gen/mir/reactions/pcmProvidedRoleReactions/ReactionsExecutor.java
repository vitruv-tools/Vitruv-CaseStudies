package mir.reactions.pcmProvidedRoleReactions;

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
    return new mir.routines.pcmProvidedRoleReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmProvidedRoleReactions"))));
    this.addReaction(new mir.reactions.pcmProvidedRoleReactions.ProvidedRoleInterfaceChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmProvidedRoleReactions"))));
  }
}
