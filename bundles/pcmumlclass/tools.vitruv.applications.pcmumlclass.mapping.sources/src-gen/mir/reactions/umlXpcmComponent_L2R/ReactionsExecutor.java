package mir.reactions.umlXpcmComponent_L2R;

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
    return new mir.routines.umlXpcmComponent_L2R.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentPackageInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentPackageRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentPackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentClassInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentClassRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentOperationInsertedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentOperationRemovedFromClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentOperationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentNameReplacedAtOperation_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentNameReplacedAtClass_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmComponent_L2R.OnRepositoryComponentNameReplacedAtPackage_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmComponent_L2R"))));
  }
}
