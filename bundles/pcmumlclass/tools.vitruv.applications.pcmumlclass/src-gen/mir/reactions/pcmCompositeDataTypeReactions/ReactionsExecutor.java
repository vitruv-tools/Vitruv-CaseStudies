package mir.reactions.pcmCompositeDataTypeReactions;

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
    return new mir.routines.pcmCompositeDataTypeReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeInsertedInRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeRemovedFromRepositoryReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeParentAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmCompositeDataTypeReactions"))));
    this.addReaction(new mir.reactions.pcmCompositeDataTypeReactions.CompositeDataTypeParentRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmCompositeDataTypeReactions"))));
  }
}
