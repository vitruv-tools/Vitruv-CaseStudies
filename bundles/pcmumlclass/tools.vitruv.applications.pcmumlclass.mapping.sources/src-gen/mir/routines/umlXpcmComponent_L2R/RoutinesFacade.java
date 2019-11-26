package mir.routines.umlXpcmComponent_L2R;

import mir.routines.umlXpcmComponent_L2R.OnRepositoryComponentNameReplacedAtClass_nameBidirectionalRepairRoutine;
import mir.routines.umlXpcmComponent_L2R.OnRepositoryComponentNameReplacedAtOperation_nameBidirectionalRepairRoutine;
import mir.routines.umlXpcmComponent_L2R.OnRepositoryComponentNameReplacedAtPackage_nameBidirectionalRepairRoutine;
import mir.routines.umlXpcmComponent_L2R.RepositoryComponent_BidirectionalCheckRoutine;
import mir.routines.umlXpcmComponent_L2R.RepositoryComponent_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmComponent_L2R.RepositoryComponent_CreateMappingRoutine;
import mir.routines.umlXpcmComponent_L2R.RepositoryComponent_DeleteMappingRoutine;
import mir.routines.umlXpcmComponent_L2R.RepositoryComponent_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmComponent_L2R.RepositoryComponent_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmComponent_L2R.RepositoryComponent_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmComponent_L2R.UpdateComponentNameRoutine;
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
  
  public boolean updateComponentName(final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateComponentNameRoutine routine = new UpdateComponentNameRoutine(_routinesFacade, _reactionExecutionState, _caller, componentPackage, repositoryPackage, implementation, constructor);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_BidirectionalUpdate(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_BidirectionalUpdateRoutine routine = new RepositoryComponent_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, componentPackage_, repositoryPackage_, implementation_, constructor_);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_CreateMapping(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_CreateMappingRoutine routine = new RepositoryComponent_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, componentPackage_, repositoryPackage_, implementation_, constructor_);
    return routine.applyRoutine();
  }
  
  public boolean repositoryComponent_DeleteMapping(final RepositoryComponent component_, final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RepositoryComponent_DeleteMappingRoutine routine = new RepositoryComponent_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, component_, repository_);
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
  
  public boolean onRepositoryComponentNameReplacedAtOperation_nameBidirectionalRepair(final Operation affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnRepositoryComponentNameReplacedAtOperation_nameBidirectionalRepairRoutine routine = new OnRepositoryComponentNameReplacedAtOperation_nameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onRepositoryComponentNameReplacedAtClass_nameBidirectionalRepair(final org.eclipse.uml2.uml.Class affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnRepositoryComponentNameReplacedAtClass_nameBidirectionalRepairRoutine routine = new OnRepositoryComponentNameReplacedAtClass_nameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onRepositoryComponentNameReplacedAtPackage_nameBidirectionalRepair(final org.eclipse.uml2.uml.Package affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnRepositoryComponentNameReplacedAtPackage_nameBidirectionalRepairRoutine routine = new OnRepositoryComponentNameReplacedAtPackage_nameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
