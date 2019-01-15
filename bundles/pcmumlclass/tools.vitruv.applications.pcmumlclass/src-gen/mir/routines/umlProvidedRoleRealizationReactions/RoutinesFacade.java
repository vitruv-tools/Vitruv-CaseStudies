package mir.routines.umlProvidedRoleRealizationReactions;

import mir.routines.umlProvidedRoleRealizationReactions.AddCorrespondenceForExistingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.ChangeNameOfCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.ChangeTypeOfCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.CreateCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.DeleteCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.DetectOrCreateCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.InsertCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.MoveCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleRealizationReactions.RemoveCorrespondingProvidedRoleRoutine;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
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
  
  public boolean insertCorrespondingProvidedRole(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingProvidedRoleRoutine routine = new InsertCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingProvidedRole(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingProvidedRoleRoutine routine = new DetectOrCreateCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingProvidedRole(final InterfaceRealization umlRealization, final OperationProvidedRole pcmProvided) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingProvidedRoleRoutine routine = new AddCorrespondenceForExistingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, pcmProvided);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingProvidedRole(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingProvidedRoleRoutine routine = new CreateCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingProvidedRole(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingProvidedRoleRoutine routine = new MoveCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingProvidedRole(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingProvidedRoleRoutine routine = new RemoveCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingProvidedRole(final InterfaceRealization umlRealization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingProvidedRoleRoutine routine = new DeleteCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingProvidedRole(final InterfaceRealization umlRealization, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingProvidedRoleRoutine routine = new ChangeNameOfCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingProvidedRole(final InterfaceRealization umlRealization, final Interface umlNewInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingProvidedRoleRoutine routine = new ChangeTypeOfCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRealization, umlNewInterface);
    return routine.applyRoutine();
  }
}
