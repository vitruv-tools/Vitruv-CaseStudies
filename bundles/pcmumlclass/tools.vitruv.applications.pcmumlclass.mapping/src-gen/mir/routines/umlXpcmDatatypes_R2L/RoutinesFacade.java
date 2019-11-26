package mir.routines.umlXpcmDatatypes_R2L;

import mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_BidirectionalCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_CreateMappingRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_DeleteMappingRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_BidirectionalCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_CreateMappingRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_DeleteMappingRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_ElementUpdatedCheckRoutine;
import mir.routines.umlXpcmDatatypes_R2L.OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
    this.pcmDataTypePropagationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines.pcmDataTypePropagationReactions")));
    this.umlReturnAndRegularParameterTypeReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines.umlReturnAndRegularParameterTypeReactions")));
  }
  
  public final mir.routines.pcmDataTypePropagationReactions.RoutinesFacade pcmDataTypePropagationReactions;
  
  public final mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade umlReturnAndRegularParameterTypeReactions;
  
  public boolean compositeDataype_BidirectionalUpdate(final CompositeDataType type_, final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_BidirectionalUpdateRoutine routine = new CompositeDataype_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, type_, repository_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_CreateMapping(final CompositeDataType type_, final Repository repository_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_CreateMappingRoutine routine = new CompositeDataype_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, type_, repository_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDataype_DeleteMapping(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDataype_DeleteMappingRoutine routine = new CompositeDataype_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, datatypesPackage_);
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
  
  public boolean compositeDatatypeParent_BidirectionalUpdate(final CompositeDataType type_, final CompositeDataType parentType_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_BidirectionalUpdateRoutine routine = new CompositeDatatypeParent_BidirectionalUpdateRoutine(_routinesFacade, _reactionExecutionState, _caller, type_, parentType_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_CreateMapping(final CompositeDataType type_, final CompositeDataType parentType_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_CreateMappingRoutine routine = new CompositeDatatypeParent_CreateMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, type_, parentType_);
    return routine.applyRoutine();
  }
  
  public boolean compositeDatatypeParent_DeleteMapping(final org.eclipse.uml2.uml.Class class_, final Generalization generalization_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CompositeDatatypeParent_DeleteMappingRoutine routine = new CompositeDatatypeParent_DeleteMappingRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, generalization_);
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
  
  public boolean onCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepair(final CompositeDataType affectedEObject) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine routine = new OnCompositeDataypeEntityNameReplacedAtCompositeDataType_entityNameBidirectionalRepairRoutine(_routinesFacade, _reactionExecutionState, _caller, affectedEObject);
    return routine.applyRoutine();
  }
  
  public boolean createRepositoryRoot(final org.eclipse.uml2.uml.Package repositoryPkg, final Repository repository) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.createRepositoryRoot(repositoryPkg, repository);
  }
  
  public boolean createUmlModelRoot(final Repository repository, final org.eclipse.uml2.uml.Package repositoryPkg) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.createUmlModelRoot(repository, repositoryPkg);
  }
  
  public boolean updateRepoName(final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateRepoName(repositoryPkg, contractsPkg, datatypesPkg);
  }
  
  public boolean updateRepoPackageName(final Repository repository) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateRepoPackageName(repository);
  }
  
  public boolean updateComponentName(final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateComponentName(componentPackage, repositoryPackage, implementation, constructor);
  }
  
  public boolean updateComponentCorrespondingUmlNames(final RepositoryComponent component, final Repository repository) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateComponentCorrespondingUmlNames(component, repository);
  }
  
  public boolean updateClassName(final CompositeDataType type, final Repository repository) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateClassName(type, repository);
  }
  
  public boolean updateCompositeDataTypeName(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateCompositeDataTypeName(class_, datatypesPackage);
  }
  
  public boolean updateOperationInterfaceName(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateOperationInterfaceName(interface_, contractsPackage);
  }
  
  public boolean updateInterfaceName(final OperationInterface operationInterface, final Repository repository) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateInterfaceName(operationInterface, repository);
  }
  
  public boolean updateRequiredRoleName(final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateRequiredRoleName(property, parameter, implementation, interface_, operation);
  }
  
  public boolean changeTypeOfCorrespondingRequiredRole(final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.changeTypeOfCorrespondingRequiredRole(umlProperty, parameter, implementation, interface_, operation);
  }
  
  public boolean updateRequiredRoleCorrespondingUmlNames(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateRequiredRoleCorrespondingUmlNames(role, operationInterface, requiringEntity);
  }
  
  public boolean updateProvidedRoleName(final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateProvidedRoleName(interfaceRealization, implementation, interface_);
  }
  
  public boolean updateInterfaceRealizationName(final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateInterfaceRealizationName(role, operationInterface, providingEntity);
  }
  
  public boolean updateSignaturePcmName(final Operation operation, final Parameter returnParameter, final Interface interface_) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateSignaturePcmName(operation, returnParameter, interface_);
  }
  
  public boolean updateSignaturePcmReturnType(final Operation operation, final Parameter returnParameter, final Interface interface_) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateSignaturePcmReturnType(operation, returnParameter, interface_);
  }
  
  public boolean updateSignatureUmlName(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateSignatureUmlName(operationSignature, returnType, operationInterface);
  }
  
  public boolean updateSignatureUmlReturnType(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
    mir.routines.umlXpcmRoutines.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlXpcmRoutines")));
    return _routinesFacade.updateSignatureUmlReturnType(operationSignature, returnType, operationInterface);
  }
}
