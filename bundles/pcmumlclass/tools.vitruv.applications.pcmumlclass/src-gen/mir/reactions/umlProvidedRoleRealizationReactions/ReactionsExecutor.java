package mir.reactions.umlProvidedRoleRealizationReactions;

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
    return new mir.routines.umlProvidedRoleRealizationReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlProvidedRoleRealizationReactions.ProvidedRoleRealizationAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleRealizationReactions"))));
    this.addReaction(new mir.reactions.umlProvidedRoleRealizationReactions.ProvidedRoleRealizationRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleRealizationReactions"))));
    this.addReaction(new mir.reactions.umlProvidedRoleRealizationReactions.ProvidedRoleRealizationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleRealizationReactions"))));
    this.addReaction(new mir.reactions.umlProvidedRoleRealizationReactions.ProvidedRoleRealizationNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleRealizationReactions"))));
    this.addReaction(new mir.reactions.umlProvidedRoleRealizationReactions.ProvidedRoleRealizationTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlProvidedRoleRealizationReactions"))));
  }
}
