package mir.reactions.pcmInnerDeclarationReactions;

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
    return new mir.routines.pcmInnerDeclarationReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmInnerDeclarationReactions"))));
    this.addReaction(new mir.reactions.pcmInnerDeclarationReactions.InnerDeclarationTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmInnerDeclarationReactions"))));
  }
}
