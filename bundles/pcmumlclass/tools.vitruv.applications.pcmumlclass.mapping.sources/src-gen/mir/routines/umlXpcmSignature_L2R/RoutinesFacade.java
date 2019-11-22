package mir.routines.umlXpcmSignature_L2R;

import mir.routines.umlXpcmSignature_L2R.OnSignatureElementReplacedAtParameter_typeBidirectionalRepairRoutine;
import mir.routines.umlXpcmSignature_L2R.OnSignatureNameReplacedAtOperation_nameBidirectionalRepairRoutine;
import mir.routines.umlXpcmSignature_L2R.Signature_BidirectionalCheckRoutine;
import mir.routines.umlXpcmSignature_L2R.Signature_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmSignature_L2R.Signature_CreateMappingRoutine;
import mir.routines.umlXpcmSignature_L2R.Signature_DeleteMappingRoutine;
import mir.routines.umlXpcmSignature_L2R.Signature_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmSignature_L2R.Signature_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmSignature_L2R.Signature_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmSignature_L2R.UpdateSignaturePcmNameRoutine;
import mir.routines.umlXpcmSignature_L2R.UpdateSignaturePcmReturnTypeRoutine;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.TypedElement;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
  
  public boolean updateSignaturePcmName(final Operation operation, final Parameter returnParameter, final Interface interface_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateSignaturePcmNameRoutine routine = new UpdateSignaturePcmNameRoutine(_routinesFacade, _reactionExecutionState, _caller, operation, returnParameter, interface_);
    return routine.applyRoutine();
  }
  
  public boolean updateSignaturePcmReturnType(final Operation operation, final Parameter returnParameter, final Interface interface_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateSignaturePcmReturnTypeRoutine routine = new UpdateSignaturePcmReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, operation, returnParameter, interface_);
    return routine.applyRoutine();
  }
  
  public boolean signature_BidirectionalUpdate(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Signature_BidirectionalUpdateRoutine routine = new Signature_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, operation_, returnParameter_, interface_);
    return routine.applyRoutine();
  }
  
  public boolean signature_CreateMapping(final Operation operation_, final Parameter returnParameter_, final Interface interface_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Signature_CreateMappingRoutine routine = new Signature_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, operation_, returnParameter_, interface_);
    return routine.applyRoutine();
  }
  
  public boolean signature_DeleteMapping(final OperationSignature operationSignature_, final DataType returnType_, final OperationInterface operationInterface_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Signature_DeleteMappingRoutine routine = new Signature_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, operationSignature_, returnType_, operationInterface_);
    return routine.applyRoutine();
  }
  
  public boolean signature_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Signature_BidirectionalCheckRoutine routine = new Signature_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean signature_ElementCreatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Signature_ElementCreatedCheckRoutine routine = new Signature_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean signature_ElementDeletedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Signature_ElementDeletedCheckRoutine routine = new Signature_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean signature_ElementUpdatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Signature_ElementUpdatedCheckRoutine routine = new Signature_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onSignatureNameReplacedAtOperation_nameBidirectionalRepair(final Operation affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnSignatureNameReplacedAtOperation_nameBidirectionalRepairRoutine routine = new OnSignatureNameReplacedAtOperation_nameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onSignatureElementReplacedAtParameter_typeBidirectionalRepair(final TypedElement affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnSignatureElementReplacedAtParameter_typeBidirectionalRepairRoutine routine = new OnSignatureElementReplacedAtParameter_typeBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
