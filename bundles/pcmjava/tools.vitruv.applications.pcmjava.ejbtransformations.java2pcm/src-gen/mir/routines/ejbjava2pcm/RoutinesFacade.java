package mir.routines.ejbjava2pcm;

import mir.routines.ejbjava2pcm.CreateBasicComponentRoutine;
import mir.routines.ejbjava2pcm.CreateOperationInterfaceRoutine;
import mir.routines.ejbjava2pcm.CreateOperationRequiredRoleRoutine;
import mir.routines.ejbjava2pcm.CreateOperationSignatureRoutine;
import mir.routines.ejbjava2pcm.CreatePCMParameterRoutine;
import mir.routines.ejbjava2pcm.CreatePCMReturnTypeRoutine;
import mir.routines.ejbjava2pcm.CreateRepositoryForFirstPackageRoutine;
import mir.routines.ejbjava2pcm.CreateSEFFForClassMethodRoutine;
import mir.routines.ejbjava2pcm.CreatedAnnotationForFieldRoutine;
import mir.routines.ejbjava2pcm.CreatedClassMethodInEjbClassRoutine;
import mir.routines.ejbjava2pcm.CreatedFieldInDatatypeClassRoutine;
import mir.routines.ejbjava2pcm.CreatedFieldRoutine;
import mir.routines.ejbjava2pcm.CreatedImplementsRoutine;
import mir.routines.ejbjava2pcm.CreatedInterfaceMethodRoutine;
import mir.routines.ejbjava2pcm.CreatedParameterInInterfaceMethodRoutine;
import mir.routines.ejbjava2pcm.CreatedReturnTypeRoutine;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
  
  public boolean createRepositoryForFirstPackage(final org.emftext.language.java.containers.Package javaPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateRepositoryForFirstPackageRoutine routine = new CreateRepositoryForFirstPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createBasicComponent(final Repository repo, final NamedElement namedElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateBasicComponentRoutine routine = new CreateBasicComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, repo, namedElement);
    return routine.applyRoutine();
  }
  
  public boolean createOperationInterface(final Repository repo, final NamedElement namedElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationInterfaceRoutine routine = new CreateOperationInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, repo, namedElement);
    return routine.applyRoutine();
  }
  
  public boolean createdAnnotationForField(final Field annotatedField) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedAnnotationForFieldRoutine routine = new CreatedAnnotationForFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, annotatedField);
    return routine.applyRoutine();
  }
  
  public boolean createdField(final Field field) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedFieldRoutine routine = new CreatedFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, field);
    return routine.applyRoutine();
  }
  
  public boolean createOperationRequiredRole(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationRequiredRoleRoutine routine = new CreateOperationRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, basicComponent, opInterface, field);
    return routine.applyRoutine();
  }
  
  public boolean createdImplements(final org.emftext.language.java.classifiers.Class clazz, final TypeReference implementz) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedImplementsRoutine routine = new CreatedImplementsRoutine(_routinesFacade, _reactionExecutionState, _caller, clazz, implementz);
    return routine.applyRoutine();
  }
  
  public boolean createdInterfaceMethod(final Interface interf, final InterfaceMethod method) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedInterfaceMethodRoutine routine = new CreatedInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, interf, method);
    return routine.applyRoutine();
  }
  
  public boolean createOperationSignature(final InterfaceMethod interfaceMethod, final OperationInterface opInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateOperationSignatureRoutine routine = new CreateOperationSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceMethod, opInterface);
    return routine.applyRoutine();
  }
  
  public boolean createdParameterInInterfaceMethod(final InterfaceMethod method, final Parameter parameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedParameterInInterfaceMethodRoutine routine = new CreatedParameterInInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, method, parameter);
    return routine.applyRoutine();
  }
  
  public boolean createPCMParameter(final Parameter jaMoPPParam, final OperationSignature opSignature) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePCMParameterRoutine routine = new CreatePCMParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jaMoPPParam, opSignature);
    return routine.applyRoutine();
  }
  
  public boolean createdReturnType(final InterfaceMethod method, final TypeReference type) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedReturnTypeRoutine routine = new CreatedReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, method, type);
    return routine.applyRoutine();
  }
  
  public boolean createPCMReturnType(final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatePCMReturnTypeRoutine routine = new CreatePCMReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, returnType, opSignature, javaMethod);
    return routine.applyRoutine();
  }
  
  public boolean createdFieldInDatatypeClass(final org.emftext.language.java.classifiers.Class clazz, final Field field) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedFieldInDatatypeClassRoutine routine = new CreatedFieldInDatatypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, clazz, field);
    return routine.applyRoutine();
  }
  
  public boolean createdClassMethodInEjbClass(final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreatedClassMethodInEjbClassRoutine routine = new CreatedClassMethodInEjbClassRoutine(_routinesFacade, _reactionExecutionState, _caller, clazz, classMethod);
    return routine.applyRoutine();
  }
  
  public boolean createSEFFForClassMethod(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateSEFFForClassMethodRoutine routine = new CreateSEFFForClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, basicComponent, opSignature, classMethod);
    return routine.applyRoutine();
  }
}
