package mir.reactions.umlCompositeDataTypeGeneralizationReactions;

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
    return new mir.routines.umlCompositeDataTypeGeneralizationReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlCompositeDataTypeGeneralizationReactions.CompositeDataTypeGeneralizationAddedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlCompositeDataTypeGeneralizationReactions"))));
    this.addReaction(new mir.reactions.umlCompositeDataTypeGeneralizationReactions.CompositeDataTypeGeneralizationRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlCompositeDataTypeGeneralizationReactions"))));
    this.addReaction(new mir.reactions.umlCompositeDataTypeGeneralizationReactions.CompositeDataTypeGeneralizationTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlCompositeDataTypeGeneralizationReactions"))));
  }
}
