package mir.routines.pcmRepositoryComponentReactions;

import mir.routines.pcmRepositoryComponentReactions.AddCorrespondenceForExistingComponentConstructorRoutine;
import mir.routines.pcmRepositoryComponentReactions.AddCorrespondenceForExistingComponentImplementationRoutine;
import mir.routines.pcmRepositoryComponentReactions.AddCorrespondenceForExistingComponentPackageRoutine;
import mir.routines.pcmRepositoryComponentReactions.ChangeNameOfComponentCorrespondencesRoutine;
import mir.routines.pcmRepositoryComponentReactions.CreateCorrespondingComponentConstructorRoutine;
import mir.routines.pcmRepositoryComponentReactions.CreateCorrespondingComponentImplementationRoutine;
import mir.routines.pcmRepositoryComponentReactions.CreateCorrespondingComponentPackageRoutine;
import mir.routines.pcmRepositoryComponentReactions.DeleteCorrespondingComponentPackageRoutine;
import mir.routines.pcmRepositoryComponentReactions.DetectOrCreateCorrespondingComponentConstructorRoutine;
import mir.routines.pcmRepositoryComponentReactions.DetectOrCreateCorrespondingComponentImplementationRoutine;
import mir.routines.pcmRepositoryComponentReactions.DetectOrCreateCorrespondingComponentPackageRoutine;
import mir.routines.pcmRepositoryComponentReactions.InsertCorrespondingComponentPackageRoutine;
import mir.routines.pcmRepositoryComponentReactions.MoveCorrespondingComponentPackageRoutine;
import mir.routines.pcmRepositoryComponentReactions.RemoveCorrespondingComponentPackageRoutine;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
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
  
  public boolean insertCorrespondingComponentPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingComponentPackageRoutine routine = new InsertCorrespondingComponentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingComponentPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingComponentPackageRoutine routine = new DetectOrCreateCorrespondingComponentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingComponentPackage(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Package umlComponentPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingComponentPackageRoutine routine = new AddCorrespondenceForExistingComponentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, umlComponentPackage);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingComponentPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingComponentPackageRoutine routine = new CreateCorrespondingComponentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingComponentImplementation(final RepositoryComponent pcmComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingComponentImplementationRoutine routine = new DetectOrCreateCorrespondingComponentImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingComponentImplementation(final RepositoryComponent pcmComponent, final org.eclipse.uml2.uml.Class umlComponentImplementation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingComponentImplementationRoutine routine = new AddCorrespondenceForExistingComponentImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, umlComponentImplementation);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingComponentImplementation(final RepositoryComponent pcmComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingComponentImplementationRoutine routine = new CreateCorrespondingComponentImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingComponentConstructor(final RepositoryComponent pcmComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingComponentConstructorRoutine routine = new DetectOrCreateCorrespondingComponentConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingComponentConstructor(final RepositoryComponent pcmComponent, final Operation umlComponentConstructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingComponentConstructorRoutine routine = new AddCorrespondenceForExistingComponentConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, umlComponentConstructor);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingComponentConstructor(final RepositoryComponent pcmComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingComponentConstructorRoutine routine = new CreateCorrespondingComponentConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingComponentPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingComponentPackageRoutine routine = new MoveCorrespondingComponentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingComponentPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingComponentPackageRoutine routine = new RemoveCorrespondingComponentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, pcmRepository);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingComponentPackage(final RepositoryComponent pcmComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingComponentPackageRoutine routine = new DeleteCorrespondingComponentPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfComponentCorrespondences(final RepositoryComponent pcmComponent, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfComponentCorrespondencesRoutine routine = new ChangeNameOfComponentCorrespondencesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmComponent, newName);
    return routine.applyRoutine();
  }
}
