package mir.reactions.umlProvidedRoleGeneralizationReactions;

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
    return new mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlProvidedRoleGeneralizationReactions.ProvidedRoleGeneralizationAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleGeneralizationReactions"))));
    this.addReaction(new mir.reactions.umlProvidedRoleGeneralizationReactions.ProvidedRoleGeneralizationRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleGeneralizationReactions"))));
    this.addReaction(new mir.reactions.umlProvidedRoleGeneralizationReactions.ProvidedRoleGeneralizationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleGeneralizationReactions"))));
    this.addReaction(new mir.reactions.umlProvidedRoleGeneralizationReactions.ProvidedRoleGeneralizationTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleGeneralizationReactions"))));
  }
}
