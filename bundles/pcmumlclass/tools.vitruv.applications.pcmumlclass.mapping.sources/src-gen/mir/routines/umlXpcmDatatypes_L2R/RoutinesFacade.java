package mir.routines.umlXpcmDatatypes_L2R;

import mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_BidirectionalCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_CreateMappingRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_DeleteMappingRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_BidirectionalCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_CreateMappingRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_DeleteMappingRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.CompositeDataype_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_L2R.OnCompositeDataypeNameReplacedAtClass_nameBidirectionalRepairRoutine;
import mir.routines.umlXpcmDatatypes_L2R.UpdateCompositeDataTypeNameRoutine;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.palladiosimulator.pcm.repository.CompositeDataType;
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
  
  public boolean updateCompositeDataTypeName(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateCompositeDataTypeNameRoutine routine = new UpdateCompositeDataTypeNameRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, datatypesPackage);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_BidirectionalUpdate(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_BidirectionalUpdateRoutine routine = new CompositeDataype_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, datatypesPackage_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_CreateMapping(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_CreateMappingRoutine routine = new CompositeDataype_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, datatypesPackage_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_DeleteMapping(final CompositeDataType type_, final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_DeleteMappingRoutine routine = new CompositeDataype_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, type_, repository_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_BidirectionalCheckRoutine routine = new CompositeDataype_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_ElementCreatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_ElementCreatedCheckRoutine routine = new CompositeDataype_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_ElementDeletedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_ElementDeletedCheckRoutine routine = new CompositeDataype_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_ElementUpdatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_ElementUpdatedCheckRoutine routine = new CompositeDataype_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_BidirectionalUpdate(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_BidirectionalUpdateRoutine routine = new CompositeDatatypeParent_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, generalization_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_CreateMapping(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_CreateMappingRoutine routine = new CompositeDatatypeParent_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, generalization_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_DeleteMapping(final CompositeDataType type_, final CompositeDataType parentType_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_DeleteMappingRoutine routine = new CompositeDatatypeParent_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, type_, parentType_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_BidirectionalCheck(final EObject affectedEObject, final String routineName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_BidirectionalCheckRoutine routine = new CompositeDatatypeParent_BidirectionalCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject, routineName);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_ElementCreatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_ElementCreatedCheckRoutine routine = new CompositeDatatypeParent_ElementCreatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_ElementDeletedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_ElementDeletedCheckRoutine routine = new CompositeDatatypeParent_ElementDeletedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_ElementUpdatedCheck(final EObject affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_ElementUpdatedCheckRoutine routine = new CompositeDatatypeParent_ElementUpdatedCheckRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean onCompositeDataypeNameReplacedAtClass_nameBidirectionalRepair(final org.eclipse.uml2.uml.Class affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnCompositeDataypeNameReplacedAtClass_nameBidirectionalRepairRoutine routine = new OnCompositeDataypeNameReplacedAtClass_nameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
}
