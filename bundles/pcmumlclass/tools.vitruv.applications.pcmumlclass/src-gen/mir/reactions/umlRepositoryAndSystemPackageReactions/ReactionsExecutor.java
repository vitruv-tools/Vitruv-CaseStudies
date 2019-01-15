package mir.reactions.umlRepositoryAndSystemPackageReactions;

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
    return new mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlRepositoryAndSystemPackageReactions.RepositoryOrSystemPackageInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRepositoryAndSystemPackageReactions"))));
    this.addReaction(new mir.reactions.umlRepositoryAndSystemPackageReactions.RepositoryOrSystemPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRepositoryAndSystemPackageReactions"))));
    this.addReaction(new mir.reactions.umlRepositoryAndSystemPackageReactions.RepositoryOrSystemPackageRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRepositoryAndSystemPackageReactions"))));
  }
}
