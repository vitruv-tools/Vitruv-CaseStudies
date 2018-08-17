package mir.reactions.umlRepositoryComponentPackageReactions;

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
    return new mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlRepositoryComponentPackageReactions.RepositoryComponentPackageInsertedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRepositoryComponentPackageReactions"))));
    this.addReaction(new mir.reactions.umlRepositoryComponentPackageReactions.RepositoryComponentPackageRemovedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRepositoryComponentPackageReactions"))));
    this.addReaction(new mir.reactions.umlRepositoryComponentPackageReactions.PackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRepositoryComponentPackageReactions"))));
    this.addReaction(new mir.reactions.umlRepositoryComponentPackageReactions.RepositoryComponentPackageRenamedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlRepositoryComponentPackageReactions"))));
  }
}
