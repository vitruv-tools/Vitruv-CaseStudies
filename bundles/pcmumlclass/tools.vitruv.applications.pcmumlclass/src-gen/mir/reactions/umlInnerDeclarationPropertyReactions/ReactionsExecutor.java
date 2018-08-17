package mir.reactions.umlInnerDeclarationPropertyReactions;

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
    return new mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyLowerChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyLowerChanged2Reaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyUpperChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
    this.addReaction(new mir.reactions.umlInnerDeclarationPropertyReactions.InnerDeclarationPropertyUpperChanged2Reaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlInnerDeclarationPropertyReactions"))));
  }
}
