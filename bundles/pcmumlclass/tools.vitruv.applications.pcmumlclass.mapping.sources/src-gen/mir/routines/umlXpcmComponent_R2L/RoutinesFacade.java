package mir.routines.umlXpcmComponent_R2L;

import mir.routines.umlXpcmComponent_R2L.OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_BidirectionalCheckRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_CreateMappingRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_DeleteMappingRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmComponent_R2L.UpdateComponentCorrespondingUmlNamesRoutine;
import org.eclipse.emf.ecore.EObject;
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
  
  public boolean updateComponentCorrespondingUmlNames(final RepositoryComponent component, final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateComponentCorrespondingUmlNamesRoutine routine = new UpdateComponentCorrespondingUmlNamesRoutine(_routinesFacade, _reactionExecutionState, _caller, component, repository);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_BidirectionalUpdate(final RepositoryComponent component_, final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_BidirectionalUpdateRoutine routine = new RepositoryComponent_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, component_, repository_);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_CreateMapping(final RepositoryComponent component_, final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_CreateMappingRoutine routine = new RepositoryComponent_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, component_, repository_);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_DeleteMapping(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_DeleteMappingRoutine routine = new RepositoryComponent_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, componentPackage_, repositoryPackage_, implementation_, constructor_);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_BidirectionalCheckRoutine routine = new RepositoryComponent_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_ElementCreatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_ElementCreatedCheckRoutine routine = new RepositoryComponent_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_ElementDeletedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_ElementDeletedCheckRoutine routine = new RepositoryComponent_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_ElementUpdatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_ElementUpdatedCheckRoutine routine = new RepositoryComponent_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepair(final RepositoryComponent affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine routine = new OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
