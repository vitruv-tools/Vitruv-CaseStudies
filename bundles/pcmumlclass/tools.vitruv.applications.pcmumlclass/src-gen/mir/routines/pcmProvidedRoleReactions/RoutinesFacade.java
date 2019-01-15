package mir.routines.pcmProvidedRoleReactions;

import mir.routines.pcmProvidedRoleReactions.AddCorrespondenceForExistingRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.ChangeInterfaceOfCorrespondingProvidedRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.ChangeNameOfCorrespondingProvidedRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.CreateCorrespondingProvidedRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.DeleteCorrespondingProvidedRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.DetectOrCreateCorrespondingProvidedRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.InsertCorrespondingProvidedRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.MoveCorrespondingProvidedRealizationRoutine;
import mir.routines.pcmProvidedRoleReactions.RemoveCorrespondingProvidedRealizationRoutine;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
  
  public boolean insertCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingProvidedRealizationRoutine routine = new InsertCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingProvidedRealizationRoutine routine = new DetectOrCreateCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingRealization(final OperationProvidedRole pcmProvided, final InterfaceRealization umlRealization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingRealizationRoutine routine = new AddCorrespondenceForExistingRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, umlRealization);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingProvidedRealizationRoutine routine = new CreateCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingProvidedRealizationRoutine routine = new MoveCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingProvidedRealizationRoutine routine = new RemoveCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingProvidedRealizationRoutine routine = new DeleteCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingProvidedRealizationRoutine routine = new ChangeNameOfCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeInterfaceOfCorrespondingProvidedRealization(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeInterfaceOfCorrespondingProvidedRealizationRoutine routine = new ChangeInterfaceOfCorrespondingProvidedRealizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmInterface);
    return routine.applyRoutine();
  }
}
