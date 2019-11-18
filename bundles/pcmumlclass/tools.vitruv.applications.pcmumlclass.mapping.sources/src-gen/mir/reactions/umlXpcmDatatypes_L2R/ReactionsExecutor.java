package mir.reactions.umlXpcmDatatypes_L2R;

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
    return new mir.routines.umlXpcmDatatypes_L2R.RoutinesFacadesProvider();
  }
  
  protected void setup() {
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDataypePackageInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDataypePackageDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDataypeClassInsertedInPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDataypeClassRemovedFromPackageReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDataypeClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDataypeNameReplacedAtClass_nameBidirectionalReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDatatypeParentClassInsertedAsRootReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDatatypeParentClassDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDatatypeParentGeneralizationInsertedInClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDatatypeParentGeneralizationRemovedFromClassReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
    this.addReaction(new mir.reactions.umlXpcmDatatypes_L2R.OnCompositeDatatypeParentGeneralizationDeletedReaction(this.getRoutinesFacadesProvider().getRoutinesFacade(ReactionsImportPath.fromPathString("umlXpcmDatatypes_L2R"))));
  }
}
