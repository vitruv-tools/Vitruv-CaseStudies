package mir.routines.pcmRequiredRoleReactions;

import mir.routines.pcmRequiredRoleReactions.ChangeNameOfCorrespondingRequiredElementsRoutine;
import mir.routines.pcmRequiredRoleReactions.ChangeTypeOfCorrespondingRequiredElementsRoutine;
import mir.routines.pcmRequiredRoleReactions.CreateCorrespondingRequiredConstructorParameterRoutine;
import mir.routines.pcmRequiredRoleReactions.CreateCorrespondingRequiredFieldRoutine;
import mir.routines.pcmRequiredRoleReactions.DeleteCorrespondingRequiredConstructorParameterRoutine;
import mir.routines.pcmRequiredRoleReactions.DeleteCorrespondingRequiredElementsRoutine;
import mir.routines.pcmRequiredRoleReactions.DeleteCorrespondingRequiredFieldRoutine;
import mir.routines.pcmRequiredRoleReactions.DetectOrCreateCorrespondingRequiredConstructorParameterRoutine;
import mir.routines.pcmRequiredRoleReactions.DetectOrCreateCorrespondingRequiredFieldRoutine;
import mir.routines.pcmRequiredRoleReactions.InsertCorrespondingRequiredElementsRoutine;
import mir.routines.pcmRequiredRoleReactions.MoveCorrespondingRequiredElementsRoutine;
import mir.routines.pcmRequiredRoleReactions.RemoveCorrespondingRequiredElementsRoutine;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
  
  public boolean insertCorrespondingRequiredElements(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingRequiredElementsRoutine routine = new InsertCorrespondingRequiredElementsRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingRequiredConstructorParameter(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingRequiredConstructorParameterRoutine routine = new DetectOrCreateCorrespondingRequiredConstructorParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingRequiredConstructorParameter(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingRequiredConstructorParameterRoutine routine = new CreateCorrespondingRequiredConstructorParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingRequiredField(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingRequiredFieldRoutine routine = new DetectOrCreateCorrespondingRequiredFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingRequiredField(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingRequiredFieldRoutine routine = new CreateCorrespondingRequiredFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingRequiredElements(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingRequiredElementsRoutine routine = new MoveCorrespondingRequiredElementsRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingRequiredElements(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingRequiredElementsRoutine routine = new RemoveCorrespondingRequiredElementsRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRequiredElements(final OperationRequiredRole pcmRequired) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRequiredElementsRoutine routine = new DeleteCorrespondingRequiredElementsRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRequiredConstructorParameter(final OperationRequiredRole pcmRequired) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRequiredConstructorParameterRoutine routine = new DeleteCorrespondingRequiredConstructorParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRequiredField(final OperationRequiredRole pcmRequired) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRequiredFieldRoutine routine = new DeleteCorrespondingRequiredFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingRequiredElements(final OperationRequiredRole pcmRequired, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingRequiredElementsRoutine routine = new ChangeNameOfCorrespondingRequiredElementsRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingRequiredElements(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingRequiredElementsRoutine routine = new ChangeTypeOfCorrespondingRequiredElementsRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRequired, pcmInterface);
    return routine.applyRoutine();
  }
}
