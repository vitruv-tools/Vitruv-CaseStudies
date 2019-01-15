package mir.reactions.umlCompositeDataTypeClassReactions;

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
    return new mir.routines.umlCompositeDataTypeClassReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlCompositeDataTypeClassReactions.CompositeDatyTypeClassInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlCompositeDataTypeClassReactions"))));
    this.addReaction(new mir.reactions.umlCompositeDataTypeClassReactions.CompositeDataTypeClassRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlCompositeDataTypeClassReactions"))));
    this.addReaction(new mir.reactions.umlCompositeDataTypeClassReactions.CompositeDataTypeClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlCompositeDataTypeClassReactions"))));
    this.addReaction(new mir.reactions.umlCompositeDataTypeClassReactions.CompositeDataTypeClassNameChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlCompositeDataTypeClassReactions"))));
  }
}
