package mir.routines.umlRequiredRoleParameterReactions;

import mir.routines.umlRequiredRoleParameterReactions.Parameter_addCorrespondenceForExistingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_changeNameOfCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_changeTypeOfCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_createCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_deleteCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_detectOrCreateCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_insertCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_moveCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRoleParameterReactions.Parameter_removeCorrespondingRequiredRoleRoutine;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
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
  
  public boolean parameter_insertCorrespondingRequiredRole(final Parameter umlParameter, final Operation umlConstructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_insertCorrespondingRequiredRoleRoutine routine = new Parameter_insertCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, umlConstructor);
    return routine.applyRoutine();
  }
  
  public boolean parameter_detectOrCreateCorrespondingRequiredRole(final Parameter umlParameter, final Operation umlConstructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_detectOrCreateCorrespondingRequiredRoleRoutine routine = new Parameter_detectOrCreateCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, umlConstructor);
    return routine.applyRoutine();
  }
  
  public boolean parameter_addCorrespondenceForExistingRequiredRole(final Parameter umlParameter, final OperationRequiredRole pcmRequired) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_addCorrespondenceForExistingRequiredRoleRoutine routine = new Parameter_addCorrespondenceForExistingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, pcmRequired);
    return routine.applyRoutine();
  }
  
  public boolean parameter_createCorrespondingRequiredRole(final Parameter umlParameter, final Operation umlConstructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_createCorrespondingRequiredRoleRoutine routine = new Parameter_createCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, umlConstructor);
    return routine.applyRoutine();
  }
  
  public boolean parameter_moveCorrespondingRequiredRole(final Parameter umlParameter, final Operation umlConstructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_moveCorrespondingRequiredRoleRoutine routine = new Parameter_moveCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, umlConstructor);
    return routine.applyRoutine();
  }
  
  public boolean parameter_removeCorrespondingRequiredRole(final Parameter umlParameter, final Operation umlConstructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_removeCorrespondingRequiredRoleRoutine routine = new Parameter_removeCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, umlConstructor);
    return routine.applyRoutine();
  }
  
  public boolean parameter_deleteCorrespondingRequiredRole(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_deleteCorrespondingRequiredRoleRoutine routine = new Parameter_deleteCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean parameter_changeNameOfCorrespondingRequiredRole(final Parameter umlParameter, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_changeNameOfCorrespondingRequiredRoleRoutine routine = new Parameter_changeNameOfCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, newName);
    return routine.applyRoutine();
  }
  
  public boolean parameter_changeTypeOfCorrespondingRequiredRole(final Parameter umlParameter, final Interface umlNewInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Parameter_changeTypeOfCorrespondingRequiredRoleRoutine routine = new Parameter_changeTypeOfCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, umlNewInterface);
    return routine.applyRoutine();
  }
}
