package mir.routines.ejbjava2pcm;

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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createRepositoryForFirstPackage(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreateRepositoryForFirstPackageRoutine routine = new mir.routines.ejbjava2pcm.CreateRepositoryForFirstPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, javaPackage);
    return routine.applyRoutine();
  }
  
  public boolean createBasicComponent(final Repository repo, final NamedElement namedElement) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreateBasicComponentRoutine routine = new mir.routines.ejbjava2pcm.CreateBasicComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, repo, namedElement);
    return routine.applyRoutine();
  }
  
  public boolean createOperationInterface(final Repository repo, final NamedElement namedElement) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreateOperationInterfaceRoutine routine = new mir.routines.ejbjava2pcm.CreateOperationInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, repo, namedElement);
    return routine.applyRoutine();
  }
  
  public boolean createdAnnotationForField(final Field annotatedField) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedAnnotationForFieldRoutine routine = new mir.routines.ejbjava2pcm.CreatedAnnotationForFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, annotatedField);
    return routine.applyRoutine();
  }
  
  public boolean createdField(final Field field) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedFieldRoutine routine = new mir.routines.ejbjava2pcm.CreatedFieldRoutine(_routinesFacade, _reactionExecutionState, _caller, field);
    return routine.applyRoutine();
  }
  
  public boolean createOperationRequiredRole(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreateOperationRequiredRoleRoutine routine = new mir.routines.ejbjava2pcm.CreateOperationRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, basicComponent, opInterface, field);
    return routine.applyRoutine();
  }
  
  public boolean createdImplements(final org.emftext.language.java.classifiers.Class clazz, final TypeReference implementz) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedImplementsRoutine routine = new mir.routines.ejbjava2pcm.CreatedImplementsRoutine(_routinesFacade, _reactionExecutionState, _caller, clazz, implementz);
    return routine.applyRoutine();
  }
  
  public boolean createdInterfaceMethod(final Interface interf, final InterfaceMethod method) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedInterfaceMethodRoutine routine = new mir.routines.ejbjava2pcm.CreatedInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, interf, method);
    return routine.applyRoutine();
  }
  
  public boolean createOperationSignature(final InterfaceMethod interfaceMethod, final OperationInterface opInterface) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreateOperationSignatureRoutine routine = new mir.routines.ejbjava2pcm.CreateOperationSignatureRoutine(_routinesFacade, _reactionExecutionState, _caller, interfaceMethod, opInterface);
    return routine.applyRoutine();
  }
  
  public boolean createdParameterInInterfaceMethod(final InterfaceMethod method, final Parameter parameter) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedParameterInInterfaceMethodRoutine routine = new mir.routines.ejbjava2pcm.CreatedParameterInInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, method, parameter);
    return routine.applyRoutine();
  }
  
  public boolean createPCMParameter(final Parameter jaMoPPParam, final OperationSignature opSignature) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatePCMParameterRoutine routine = new mir.routines.ejbjava2pcm.CreatePCMParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jaMoPPParam, opSignature);
    return routine.applyRoutine();
  }
  
  public boolean createdReturnType(final InterfaceMethod method, final TypeReference type) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedReturnTypeRoutine routine = new mir.routines.ejbjava2pcm.CreatedReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, method, type);
    return routine.applyRoutine();
  }
  
  public boolean createPCMReturnType(final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatePCMReturnTypeRoutine routine = new mir.routines.ejbjava2pcm.CreatePCMReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, returnType, opSignature, javaMethod);
    return routine.applyRoutine();
  }
  
  public boolean createdFieldInDatatypeClass(final org.emftext.language.java.classifiers.Class clazz, final Field field) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedFieldInDatatypeClassRoutine routine = new mir.routines.ejbjava2pcm.CreatedFieldInDatatypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, clazz, field);
    return routine.applyRoutine();
  }
  
  public boolean createdClassMethodInEjbClass(final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreatedClassMethodInEjbClassRoutine routine = new mir.routines.ejbjava2pcm.CreatedClassMethodInEjbClassRoutine(_routinesFacade, _reactionExecutionState, _caller, clazz, classMethod);
    return routine.applyRoutine();
  }
  
  public boolean createSEFFForClassMethod(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod) {
    mir.routines.ejbjava2pcm.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("ejbjava2pcm").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.ejbjava2pcm.CreateSEFFForClassMethodRoutine routine = new mir.routines.ejbjava2pcm.CreateSEFFForClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, basicComponent, opSignature, classMethod);
    return routine.applyRoutine();
  }
}
