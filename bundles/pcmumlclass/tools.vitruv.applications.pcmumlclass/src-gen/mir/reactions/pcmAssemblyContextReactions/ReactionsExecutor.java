package mir.reactions.pcmAssemblyContextReactions;

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
    return new mir.routines.pcmAssemblyContextReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmAssemblyContextReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmAssemblyContextReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmAssemblyContextReactions"))));
    this.addReaction(new mir.reactions.pcmAssemblyContextReactions.AssemblyContextComponentChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("pcmAssemblyContextReactions"))));
  }
}
