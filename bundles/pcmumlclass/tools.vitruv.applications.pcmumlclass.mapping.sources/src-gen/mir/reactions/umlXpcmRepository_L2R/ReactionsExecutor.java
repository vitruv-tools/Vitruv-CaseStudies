package mir.reactions.umlXpcmRepository_L2R;

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
    return new mir.routines.umlXpcmRepository_L2R.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmRepository_L2R.OnRepositoryPackageInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRepository_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRepository_L2R.OnRepositoryPackageRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRepository_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRepository_L2R.OnRepositoryPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRepository_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRepository_L2R.OnRepositoryNameReplacedAtPackage_nameReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRepository_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmRepository_L2R.OnRepositoryNameReplacedAtPackage_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmRepository_L2R"))));
  }
}
