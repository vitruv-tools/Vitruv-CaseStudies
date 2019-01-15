package mir.routines.umlRequiredRolePropertyReactions;

import mir.routines.umlRequiredRolePropertyReactions.AddCorrespondenceForExistingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.ChangeNameOfCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.ChangeTypeOfCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.CreateCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.DeleteCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.DetectOrCreateCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.InsertCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.MoveCorrespondingRequiredRoleRoutine;
import mir.routines.umlRequiredRolePropertyReactions.RemoveCorrespondingRequiredRoleRoutine;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
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
  
  public boolean insertCorrespondingRequiredRole(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingRequiredRoleRoutine routine = new InsertCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingRequiredRole(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingRequiredRoleRoutine routine = new DetectOrCreateCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingRequiredRole(final Property umlProperty, final OperationRequiredRole pcmRequired) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingRequiredRoleRoutine routine = new AddCorrespondenceForExistingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, pcmRequired);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingRequiredRole(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingRequiredRoleRoutine routine = new CreateCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingRequiredRole(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingRequiredRoleRoutine routine = new MoveCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingRequiredRole(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingRequiredRoleRoutine routine = new RemoveCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRequiredRole(final Property umlProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRequiredRoleRoutine routine = new DeleteCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingRequiredRole(final Property umlProperty, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingRequiredRoleRoutine routine = new ChangeNameOfCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingRequiredRole(final Property umlProperty, final Interface umlNewInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingRequiredRoleRoutine routine = new ChangeTypeOfCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlNewInterface);
    return routine.applyRoutine();
  }
}
