package mir.routines.umlInterfaceReactions;

import mir.routines.umlInterfaceReactions.AddCorrespondenceForExistingInterfaceRoutine;
import mir.routines.umlInterfaceReactions.CreateCorrespondingInterfaceRoutine;
import mir.routines.umlInterfaceReactions.DeleteCorrespondingInterfaceRoutine;
import mir.routines.umlInterfaceReactions.DetectOrCreateCorrespondingInterfaceRoutine;
import mir.routines.umlInterfaceReactions.InsertCorrespondingInterfaceRoutine;
import mir.routines.umlInterfaceReactions.MoveCorrespondingInterfaceRoutine;
import mir.routines.umlInterfaceReactions.RemoveCorrespondingInterfaceRoutine;
import mir.routines.umlInterfaceReactions.RenameCorrespondingInterfaceRoutine;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
  
  public boolean insertCorrespondingInterface(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingInterfaceRoutine routine = new InsertCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingInterface(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingInterfaceRoutine routine = new DetectOrCreateCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingInterface(final Interface umlInterface, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingInterfaceRoutine routine = new AddCorrespondenceForExistingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, pcmInterface);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingInterface(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingInterfaceRoutine routine = new CreateCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingInterface(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingInterfaceRoutine routine = new MoveCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingInterface(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingInterfaceRoutine routine = new RemoveCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingInterface(final Interface umlInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingInterfaceRoutine routine = new DeleteCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface);
    return routine.applyRoutine();
  }
  
  public boolean renameCorrespondingInterface(final Interface umlInterface, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCorrespondingInterfaceRoutine routine = new RenameCorrespondingInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, newName);
    return routine.applyRoutine();
  }
}
