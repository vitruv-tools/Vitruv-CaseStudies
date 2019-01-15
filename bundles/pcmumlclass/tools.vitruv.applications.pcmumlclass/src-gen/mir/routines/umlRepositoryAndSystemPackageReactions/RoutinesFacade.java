package mir.routines.umlRepositoryAndSystemPackageReactions;

import mir.routines.umlRepositoryAndSystemPackageReactions.CreateCorrespondingRepositoryRoutine;
import mir.routines.umlRepositoryAndSystemPackageReactions.CreateCorrespondingSystemRoutine;
import mir.routines.umlRepositoryAndSystemPackageReactions.DeleteCorrespondingRepositoryOrSystemRoutine;
import mir.routines.umlRepositoryAndSystemPackageReactions.DeleteCorrespondingRepositoryRoutine;
import mir.routines.umlRepositoryAndSystemPackageReactions.DeleteCorrespondingSystemRoutine;
import mir.routines.umlRepositoryAndSystemPackageReactions.InsertCorrespondingRepositoryOrSystemRoutine;
import mir.routines.umlRepositoryAndSystemPackageReactions.RenameCorrespondingRepositoryOrSystemRoutine;
import mir.routines.umlRepositoryAndSystemPackageReactions.UserDisambiguateRepositoryOrSystemCreationRoutine;
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
  
  public boolean insertCorrespondingRepositoryOrSystem(final org.eclipse.uml2.uml.Package umlPackage, final org.eclipse.uml2.uml.Package umlParentPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingRepositoryOrSystemRoutine routine = new InsertCorrespondingRepositoryOrSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPackage, umlParentPackage);
    return routine.applyRoutine();
  }
  
  public boolean userDisambiguateRepositoryOrSystemCreation(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UserDisambiguateRepositoryOrSystemCreationRoutine routine = new UserDisambiguateRepositoryOrSystemCreationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingRepository(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingRepositoryRoutine routine = new CreateCorrespondingRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingSystem(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingSystemRoutine routine = new CreateCorrespondingSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRepositoryOrSystem(final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRepositoryOrSystemRoutine routine = new DeleteCorrespondingRepositoryOrSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRepository(final org.eclipse.uml2.uml.Package umlRepositoryPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRepositoryRoutine routine = new DeleteCorrespondingRepositoryRoutine(_routinesFacade, _reactionExecutionState, _caller, umlRepositoryPkg);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingSystem(final org.eclipse.uml2.uml.Package umlSystemPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingSystemRoutine routine = new DeleteCorrespondingSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, umlSystemPkg);
    return routine.applyRoutine();
  }
  
  public boolean renameCorrespondingRepositoryOrSystem(final org.eclipse.uml2.uml.Package umlPkg, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameCorrespondingRepositoryOrSystemRoutine routine = new RenameCorrespondingRepositoryOrSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, newName);
    return routine.applyRoutine();
  }
}
