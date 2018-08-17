package mir.reactions.umlRequiredRolePropertyReactions;

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
    return new mir.routines.umlRequiredRolePropertyReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlRequiredRolePropertyReactions.RequiredRolePropertyInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRequiredRolePropertyReactions"))));
    this.addReaction(new mir.reactions.umlRequiredRolePropertyReactions.RequiredRolePropertyRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRequiredRolePropertyReactions"))));
    this.addReaction(new mir.reactions.umlRequiredRolePropertyReactions.RequiredRolePropertyDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRequiredRolePropertyReactions"))));
    this.addReaction(new mir.reactions.umlRequiredRolePropertyReactions.RequiredRolePropertyNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRequiredRolePropertyReactions"))));
    this.addReaction(new mir.reactions.umlRequiredRolePropertyReactions.RequiredRolePropertyTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRequiredRolePropertyReactions"))));
  }
}
