package mir.routines.umlXpcmInterface_L2R;

import mir.routines.umlXpcmInterface_L2R.OnOperationInterfaceNameReplacedAtInterface_nameBidirectionalRepairRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_BidirectionalCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_CreateMappingRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_DeleteMappingRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterface_BidirectionalCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterface_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterface_CreateMappingRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterface_DeleteMappingRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterface_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterface_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.OperationInterface_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmInterface_L2R.UpdateOperationInterfaceNameRoutine;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
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
  
  public boolean updateOperationInterfaceName(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateOperationInterfaceNameRoutine routine = new UpdateOperationInterfaceNameRoutine(_routinesFacade, _reactionExecutionState, _caller, interface_, contractsPackage);
    return routine.applyRoutine();
  }
  
  public boolean operationInterface_BidirectionalUpdate(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterface_BidirectionalUpdateRoutine routine = new OperationInterface_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, interface_, contractsPackage_);
    return routine.applyRoutine();
  }
  
  public boolean operationInterface_CreateMapping(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterface_CreateMappingRoutine routine = new OperationInterface_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, interface_, contractsPackage_);
    return routine.applyRoutine();
  }
  
  public boolean operationInterface_DeleteMapping(final OperationInterface operationInterface_, final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterface_DeleteMappingRoutine routine = new OperationInterface_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, operationInterface_, repository_);
    return routine.applyRoutine();
  }
  
  public boolean operationInterface_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterface_BidirectionalCheckRoutine routine = new OperationInterface_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean operationInterface_ElementCreatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterface_ElementCreatedCheckRoutine routine = new OperationInterface_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean operationInterface_ElementDeletedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterface_ElementDeletedCheckRoutine routine = new OperationInterface_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean operationInterface_ElementUpdatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterface_ElementUpdatedCheckRoutine routine = new OperationInterface_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean operationInterfaceParent_BidirectionalUpdate(final Interface interface_, final Generalization generalization_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterfaceParent_BidirectionalUpdateRoutine routine = new OperationInterfaceParent_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, interface_, generalization_);
    return routine.applyRoutine();
  }
  
  public boolean operationInterfaceParent_CreateMapping(final Interface interface_, final Generalization generalization_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterfaceParent_CreateMappingRoutine routine = new OperationInterfaceParent_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, interface_, generalization_);
    return routine.applyRoutine();
  }
  
  public boolean operationInterfaceParent_DeleteMapping(final OperationInterface operationInterface_, final OperationInterface parentInterface_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterfaceParent_DeleteMappingRoutine routine = new OperationInterfaceParent_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, operationInterface_, parentInterface_);
    return routine.applyRoutine();
  }
  
  public boolean operationInterfaceParent_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterfaceParent_BidirectionalCheckRoutine routine = new OperationInterfaceParent_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean operationInterfaceParent_ElementCreatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterfaceParent_ElementCreatedCheckRoutine routine = new OperationInterfaceParent_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean operationInterfaceParent_ElementDeletedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterfaceParent_ElementDeletedCheckRoutine routine = new OperationInterfaceParent_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean operationInterfaceParent_ElementUpdatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OperationInterfaceParent_ElementUpdatedCheckRoutine routine = new OperationInterfaceParent_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onOperationInterfaceNameReplacedAtInterface_nameBidirectionalRepair(final Interface affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnOperationInterfaceNameReplacedAtInterface_nameBidirectionalRepairRoutine routine = new OnOperationInterfaceNameReplacedAtInterface_nameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
