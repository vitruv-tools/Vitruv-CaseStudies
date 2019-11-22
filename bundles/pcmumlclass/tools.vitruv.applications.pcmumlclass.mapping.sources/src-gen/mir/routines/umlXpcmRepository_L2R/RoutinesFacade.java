package mir.routines.umlXpcmRepository_L2R;

import mir.routines.umlXpcmRepository_L2R.OnRepositoryNameReplacedAtPackage_nameBidirectionalRepairRoutine;
import mir.routines.umlXpcmRepository_L2R.Repository_BidirectionalCheckRoutine;
import mir.routines.umlXpcmRepository_L2R.Repository_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmRepository_L2R.Repository_CreateMappingRoutine;
import mir.routines.umlXpcmRepository_L2R.Repository_DeleteMappingRoutine;
import mir.routines.umlXpcmRepository_L2R.Repository_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmRepository_L2R.Repository_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmRepository_L2R.Repository_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmRepository_L2R.UpdateRepoNameRoutine;
import org.eclipse.emf.ecore.EObject;
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
  
  public boolean updateRepoName(final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateRepoNameRoutine routine = new UpdateRepoNameRoutine(_routinesFacade, _reactionExecutionState, _caller, repositoryPkg, contractsPkg, datatypesPkg);
    return routine.applyRoutine();
  }
  
  public boolean repository_BidirectionalUpdate(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Repository_BidirectionalUpdateRoutine routine = new Repository_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, repositoryPkg_, contractsPkg_, datatypesPkg_);
    return routine.applyRoutine();
  }
  
  public boolean repository_CreateMapping(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Repository_CreateMappingRoutine routine = new Repository_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, repositoryPkg_, contractsPkg_, datatypesPkg_);
    return routine.applyRoutine();
  }
  
  public boolean repository_DeleteMapping(final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Repository_DeleteMappingRoutine routine = new Repository_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, repository_);
    return routine.applyRoutine();
  }
  
  public boolean repository_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Repository_BidirectionalCheckRoutine routine = new Repository_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean repository_ElementCreatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Repository_ElementCreatedCheckRoutine routine = new Repository_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean repository_ElementDeletedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Repository_ElementDeletedCheckRoutine routine = new Repository_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean repository_ElementUpdatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Repository_ElementUpdatedCheckRoutine routine = new Repository_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onRepositoryNameReplacedAtPackage_nameBidirectionalRepair(final org.eclipse.uml2.uml.Package affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnRepositoryNameReplacedAtPackage_nameBidirectionalRepairRoutine routine = new OnRepositoryNameReplacedAtPackage_nameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
