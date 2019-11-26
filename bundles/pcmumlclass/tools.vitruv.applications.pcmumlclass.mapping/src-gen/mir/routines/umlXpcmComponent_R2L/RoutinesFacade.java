package mir.routines.umlXpcmComponent_R2L;

import mir.routines.umlXpcmComponent_R2L.OnRepositoryComponentEntityNameReplacedAtRepositoryComponent_entityNameBidirectionalRepairRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_BidirectionalCheckRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_BidirectionalUpdateRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_CreateMappingRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_DeleteMappingRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_ElementCreatedCheckRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_ElementDeletedCheckRoutine;
import mir.routines.umlXpcmComponent_R2L.RepositoryComponent_ElementUpdatedCheckRoutine;
import org.eclipse.emf.ecore.EObject;
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
