package mir.routines.umlXpcmRoutines;

import mir.routines.umlXpcmRoutines.ChangeTypeOfCorrespondingRequiredRoleRoutine;
import mir.routines.umlXpcmRoutines.CreateRepositoryRootRoutine;
import mir.routines.umlXpcmRoutines.CreateUmlModelRootRoutine;
import mir.routines.umlXpcmRoutines.UpdateClassNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateComponentCorrespondingUmlNamesRoutine;
import mir.routines.umlXpcmRoutines.UpdateComponentNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateCompositeDataTypeNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateInterfaceNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateInterfaceRealizationNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateOperationInterfaceNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateProvidedRoleNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateRepoNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateRepoPackageNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateRequiredRoleCorrespondingUmlNamesRoutine;
import mir.routines.umlXpcmRoutines.UpdateRequiredRoleNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateSignaturePcmNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateSignaturePcmReturnTypeRoutine;
import mir.routines.umlXpcmRoutines.UpdateSignatureUmlNameRoutine;
import mir.routines.umlXpcmRoutines.UpdateSignatureUmlReturnTypeRoutine;
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
    this.pcmDataTypePropagationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmDataTypePropagationReactions")));
    this.umlReturnAndRegularParameterTypeReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlReturnAndRegularParameterTypeReactions")));
  }
  
  public final mir.routines.pcmDataTypePropagationReactions.RoutinesFacade pcmDataTypePropagationReactions;
  
  public final mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade umlReturnAndRegularParameterTypeReactions;
  
  public boolean createRepositoryRoot(final org.eclipse.uml2.uml.Package repositoryPkg, final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRepositoryRootRoutine routine = new CreateRepositoryRootRoutine(_routinesFacade, _reactionExecutionState, _caller, repositoryPkg, repository);
    return routine.applyRoutine();
  }
  
  public boolean createUmlModelRoot(final Repository repository, final org.eclipse.uml2.uml.Package repositoryPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlModelRootRoutine routine = new CreateUmlModelRootRoutine(_routinesFacade, _reactionExecutionState, _caller, repository, repositoryPkg);
    return routine.applyRoutine();
  }
  
  public boolean updateRepoName(final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateRepoNameRoutine routine = new UpdateRepoNameRoutine(_routinesFacade, _reactionExecutionState, _caller, repositoryPkg, contractsPkg, datatypesPkg);
    return routine.applyRoutine();
  }
  
  public boolean updateRepoPackageName(final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateRepoPackageNameRoutine routine = new UpdateRepoPackageNameRoutine(_routinesFacade, _reactionExecutionState, _caller, repository);
    return routine.applyRoutine();
  }
  
  public boolean updateComponentName(final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateComponentNameRoutine routine = new UpdateComponentNameRoutine(_routinesFacade, _reactionExecutionState, _caller, componentPackage, repositoryPackage, implementation, constructor);
    return routine.applyRoutine();
  }
  
  public boolean updateComponentCorrespondingUmlNames(final RepositoryComponent component, final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateComponentCorrespondingUmlNamesRoutine routine = new UpdateComponentCorrespondingUmlNamesRoutine(_routinesFacade, _reactionExecutionState, _caller, component, repository);
    return routine.applyRoutine();
  }
  
  public boolean updateClassName(final CompositeDataType type, final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateClassNameRoutine routine = new UpdateClassNameRoutine(_routinesFacade, _reactionExecutionState, _caller, type, repository);
    return routine.applyRoutine();
  }
  
  public boolean updateCompositeDataTypeName(final org.eclipse.uml2.uml.Class class_, final org.eclipse.uml2.uml.Package datatypesPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateCompositeDataTypeNameRoutine routine = new UpdateCompositeDataTypeNameRoutine(_routinesFacade, _reactionExecutionState, _caller, class_, datatypesPackage);
    return routine.applyRoutine();
  }
  
  public boolean updateOperationInterfaceName(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateOperationInterfaceNameRoutine routine = new UpdateOperationInterfaceNameRoutine(_routinesFacade, _reactionExecutionState, _caller, interface_, contractsPackage);
    return routine.applyRoutine();
  }
  
  public boolean updateInterfaceName(final OperationInterface operationInterface, final Repository repository) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateInterfaceNameRoutine routine = new UpdateInterfaceNameRoutine(_routinesFacade, _reactionExecutionState, _caller, operationInterface, repository);
    return routine.applyRoutine();
  }
  
  public boolean updateRequiredRoleName(final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateRequiredRoleNameRoutine routine = new UpdateRequiredRoleNameRoutine(_routinesFacade, _reactionExecutionState, _caller, property, parameter, implementation, interface_, operation);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingRequiredRole(final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingRequiredRoleRoutine routine = new ChangeTypeOfCorrespondingRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, parameter, implementation, interface_, operation);
    return routine.applyRoutine();
  }
  
  public boolean updateRequiredRoleCorrespondingUmlNames(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateRequiredRoleCorrespondingUmlNamesRoutine routine = new UpdateRequiredRoleCorrespondingUmlNamesRoutine(_routinesFacade, _reactionExecutionState, _caller, role, operationInterface, requiringEntity);
    return routine.applyRoutine();
  }
  
  public boolean updateProvidedRoleName(final InterfaceRealization interfaceRealization, final org.eclipse.uml2.uml.Class implementation, final Interface interface_) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateProvidedRoleNameRoutine routine = new UpdateProvidedRoleNameRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceRealization, implementation, interface_);
    return routine.applyRoutine();
  }
  
  public boolean updateInterfaceRealizationName(final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateInterfaceRealizationNameRoutine routine = new UpdateInterfaceRealizationNameRoutine(_routinesFacade, _reactionExecutionState, _caller, role, operationInterface, providingEntity);
    return routine.applyRoutine();
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
  
  public boolean updateSignatureUmlName(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateSignatureUmlNameRoutine routine = new UpdateSignatureUmlNameRoutine(_routinesFacade, _reactionExecutionState, _caller, operationSignature, returnType, operationInterface);
    return routine.applyRoutine();
  }
  
  public boolean updateSignatureUmlReturnType(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UpdateSignatureUmlReturnTypeRoutine routine = new UpdateSignatureUmlReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, operationSignature, returnType, operationInterface);
    return routine.applyRoutine();
  }
}
