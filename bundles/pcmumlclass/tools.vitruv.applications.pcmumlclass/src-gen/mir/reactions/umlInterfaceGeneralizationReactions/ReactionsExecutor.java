package mir.reactions.umlInterfaceGeneralizationReactions;

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
    return new mir.routines.umlInterfaceGeneralizationReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlInterfaceGeneralizationReactions.InterfaceGeneralizationAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInterfaceGeneralizationReactions"))));
    this.addReaction(new mir.reactions.umlInterfaceGeneralizationReactions.InterfaceGeneralizationRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInterfaceGeneralizationReactions"))));
    this.addReaction(new mir.reactions.umlInterfaceGeneralizationReactions.ParentInterfaceReplacedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInterfaceGeneralizationReactions"))));
  }
}
