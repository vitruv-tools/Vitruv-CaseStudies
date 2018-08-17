package mir.routines.pcmInterfaceReactions;

import mir.routines.pcmInterfaceReactions.AddParentInterfaceToCorrespondingInterfaceRoutine;
import mir.routines.pcmInterfaceReactions.AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine;
import mir.routines.pcmInterfaceReactions.CreateInterfaceCorrespondenceRoutine;
import mir.routines.pcmInterfaceReactions.DeleteCorrespondingInterfaceRoutine;
import mir.routines.pcmInterfaceReactions.DetectOrCreateInterfaceCandidateRoutine;
import mir.routines.pcmInterfaceReactions.InsertCorrespondingInterfaceRoutine;
import mir.routines.pcmInterfaceReactions.MoveCorrespondingInterfaceRoutine;
import mir.routines.pcmInterfaceReactions.RemoveCorrespondingInterfaceRoutine;
import mir.routines.pcmInterfaceReactions.RemoveParentInterfaceFromCorrespondingInterfaceRoutine;
import mir.routines.pcmInterfaceReactions.RenameCorrespondingInterfaceRoutine;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
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
  
  public boolean insertCorrespondingInterface(final OperationInterface pcmInterface, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingInterfaceRoutine routine = new InsertCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateInterfaceCandidate(final OperationInterface pcmInterface, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateInterfaceCandidateRoutine routine = new DetectOrCreateInterfaceCandidateRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean attemptAddingCorrespondenceForExistingInterfaceCandidate(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface umlInterfaceCandidate) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine routine = new AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmRepository, umlInterfaceCandidate);
    return routine.applyRoutine();
  }
  
  public boolean createInterfaceCorrespondence(final OperationInterface pcmInterface, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateInterfaceCorrespondenceRoutine routine = new CreateInterfaceCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingInterface(final OperationInterface pcmInterface, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingInterfaceRoutine routine = new MoveCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingInterface(final OperationInterface pcmInterface, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingInterfaceRoutine routine = new RemoveCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingInterface(final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingInterfaceRoutine routine = new DeleteCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean renameCorrespondingInterface(final OperationInterface pcmInterface, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCorrespondingInterfaceRoutine routine = new RenameCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, newName);
    return routine.applyRoutine();
  }
  
  public boolean addParentInterfaceToCorrespondingInterface(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddParentInterfaceToCorrespondingInterfaceRoutine routine = new AddParentInterfaceToCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmParentInterface);
    return routine.applyRoutine();
  }
  
  public boolean removeParentInterfaceFromCorrespondingInterface(final OperationInterface pcmInterface, final OperationInterface pcmParentInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveParentInterfaceFromCorrespondingInterfaceRoutine routine = new RemoveParentInterfaceFromCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, pcmParentInterface);
    return routine.applyRoutine();
  }
}
