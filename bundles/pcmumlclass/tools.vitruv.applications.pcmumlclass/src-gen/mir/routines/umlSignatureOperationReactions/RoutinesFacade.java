package mir.routines.umlSignatureOperationReactions;

import mir.routines.umlSignatureOperationReactions.AddCorrespondenceForExistingSignatureRoutine;
import mir.routines.umlSignatureOperationReactions.CreateCorrespondingSignatureRoutine;
import mir.routines.umlSignatureOperationReactions.DeleteCorrespondingSignatureRoutine;
import mir.routines.umlSignatureOperationReactions.DetectOrCreateCorrespondingSignatureRoutine;
import mir.routines.umlSignatureOperationReactions.InsertCorrespondingSignatureRoutine;
import mir.routines.umlSignatureOperationReactions.MoveCorrespondingSignatureRoutine;
import mir.routines.umlSignatureOperationReactions.RemoveCorrespondingSignatureRoutine;
import mir.routines.umlSignatureOperationReactions.RenameCorrespondingSignatureRoutine;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
  
  public boolean insertCorrespondingSignature(final Operation umlOperation, final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingSignatureRoutine routine = new InsertCorrespondingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingSignature(final Operation umlOperation, final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingSignatureRoutine routine = new DetectOrCreateCorrespondingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingSignature(final Operation umlOperation, final OperationSignature pcmSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingSignatureRoutine routine = new AddCorrespondenceForExistingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, pcmSignature);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingSignature(final Operation umlOperation, final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingSignatureRoutine routine = new CreateCorrespondingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingSignature(final Operation umlOperation, final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingSignatureRoutine routine = new MoveCorrespondingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingSignature(final Operation umlOperation, final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingSignatureRoutine routine = new RemoveCorrespondingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingSignature(final Operation umlOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingSignatureRoutine routine = new DeleteCorrespondingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation);
    return routine.applyRoutine();
  }
  
  public boolean renameCorrespondingSignature(final Operation umlOperation, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCorrespondingSignatureRoutine routine = new RenameCorrespondingSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, umlOperation, newName);
    return routine.applyRoutine();
  }
}
