package mir.routines.pcmSystemReactions;

import mir.routines.pcmSystemReactions.AddCorrespondenceForExistingSystemConstructorRoutine;
import mir.routines.pcmSystemReactions.AddCorrespondenceForExistingSystemImplementationRoutine;
import mir.routines.pcmSystemReactions.ChangeNameOfSystemCorrespondencesRoutine;
import mir.routines.pcmSystemReactions.CreateCorrespondingSystemConstructorRoutine;
import mir.routines.pcmSystemReactions.CreateCorrespondingSystemImplementationRoutine;
import mir.routines.pcmSystemReactions.CreateCorrespondingSystemPackageRoutine;
import mir.routines.pcmSystemReactions.DeleteCorrespondencToSystemConstructorRoutine;
import mir.routines.pcmSystemReactions.DeleteCorrespondencToSystemImplementationRoutine;
import mir.routines.pcmSystemReactions.DeleteCorrespondencToSystemPackageRoutine;
import mir.routines.pcmSystemReactions.DeleteCorrespondingSystemPackageRoutine;
import mir.routines.pcmSystemReactions.DetectOrCreateCorrespondingSystemConstructorRoutine;
import mir.routines.pcmSystemReactions.DetectOrCreateCorrespondingSystemImplementationRoutine;
import mir.routines.pcmSystemReactions.DetectOrCreateSystemCorrespondencesRoutine;
import org.eclipse.uml2.uml.Operation;
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
  
  public boolean detectOrCreateSystemCorrespondences(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateSystemCorrespondencesRoutine routine = new DetectOrCreateSystemCorrespondencesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingSystemPackageRoutine routine = new CreateCorrespondingSystemPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingSystemImplementationRoutine routine = new DetectOrCreateCorrespondingSystemImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem, final org.eclipse.uml2.uml.Class umlSystemImplementation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingSystemImplementationRoutine routine = new AddCorrespondenceForExistingSystemImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem, umlSystemImplementation);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingSystemImplementationRoutine routine = new CreateCorrespondingSystemImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingSystemConstructorRoutine routine = new DetectOrCreateCorrespondingSystemConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem, final Operation umlSystemConstructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingSystemConstructorRoutine routine = new AddCorrespondenceForExistingSystemConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem, umlSystemConstructor);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingSystemConstructorRoutine routine = new CreateCorrespondingSystemConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingSystemPackageRoutine routine = new DeleteCorrespondingSystemPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondencToSystemPackage(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondencToSystemPackageRoutine routine = new DeleteCorrespondencToSystemPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondencToSystemImplementation(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondencToSystemImplementationRoutine routine = new DeleteCorrespondencToSystemImplementationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondencToSystemConstructor(final org.palladiosimulator.pcm.system.System pcmSystem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondencToSystemConstructorRoutine routine = new DeleteCorrespondencToSystemConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfSystemCorrespondences(final org.palladiosimulator.pcm.system.System pcmSystem, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfSystemCorrespondencesRoutine routine = new ChangeNameOfSystemCorrespondencesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSystem, newName);
    return routine.applyRoutine();
  }
}
