package mir.reactions.umlAssemblyContextPropertyReactions;

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
    return new mir.routines.umlAssemblyContextPropertyReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlAssemblyContextPropertyReactions.AssemblyContextPropertyInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlAssemblyContextPropertyReactions"))));
    this.addReaction(new mir.reactions.umlAssemblyContextPropertyReactions.AssemblyContextPropertyRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlAssemblyContextPropertyReactions"))));
    this.addReaction(new mir.reactions.umlAssemblyContextPropertyReactions.AssemblyContextPropertyDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlAssemblyContextPropertyReactions"))));
    this.addReaction(new mir.reactions.umlAssemblyContextPropertyReactions.AssemblyContextPropertyTypeChangedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlAssemblyContextPropertyReactions"))));
  }
}
