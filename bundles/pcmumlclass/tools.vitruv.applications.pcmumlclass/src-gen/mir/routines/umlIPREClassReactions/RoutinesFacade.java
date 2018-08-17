package mir.routines.umlIPREClassReactions;

import mir.routines.umlIPREClassReactions.ChangeNameOfCorrespondingIPRE_ImplementationRoutine;
import mir.routines.umlIPREClassReactions.WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean warnUserAboutImplRemoveIfIPRECorrespondenceExists(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine routine = new WarnUserAboutImplRemoveIfIPRECorrespondenceExistsRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingIPRE_Implementation(final org.eclipse.uml2.uml.Class umlClass, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingIPRE_ImplementationRoutine routine = new ChangeNameOfCorrespondingIPRE_ImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, newName);
    return routine.applyRoutine();
  }
}
