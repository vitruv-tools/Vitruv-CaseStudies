package mir.reactions.umlXpcmInterface_L2R;

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
    return new mir.routines.umlXpcmInterface_L2R.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfacePackageInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfacePackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceInterfaceInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceInterfaceRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceNameReplacedAtInterface_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentInterfaceInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentInterfaceDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentGeneralizationInsertedInInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentGeneralizationRemovedFromInterfaceReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmInterface_L2R.OnOperationInterfaceParentGeneralizationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmInterface_L2R"))));
  }
}
