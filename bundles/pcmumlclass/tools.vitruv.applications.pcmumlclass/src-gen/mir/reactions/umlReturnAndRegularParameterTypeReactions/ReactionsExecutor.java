package mir.reactions.umlReturnAndRegularParameterTypeReactions;

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
    return new mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlReturnAndRegularParameterTypeReactions.RegularOrReturnParameterTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions"))));
    this.addReaction(new mir.reactions.umlReturnAndRegularParameterTypeReactions.RegularOrReturnParameterLowerChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions"))));
    this.addReaction(new mir.reactions.umlReturnAndRegularParameterTypeReactions.RegularOrReturnParameterLowerChanged2Reaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions"))));
    this.addReaction(new mir.reactions.umlReturnAndRegularParameterTypeReactions.RegularOrReturnParameterUpperChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions"))));
    this.addReaction(new mir.reactions.umlReturnAndRegularParameterTypeReactions.RegularOrReturnParameterUpperChanged2Reaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions"))));
  }
}
