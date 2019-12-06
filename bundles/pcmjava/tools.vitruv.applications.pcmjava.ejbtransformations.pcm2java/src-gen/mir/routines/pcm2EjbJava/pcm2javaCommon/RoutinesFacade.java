package mir.routines.pcm2EjbJava.pcm2javaCommon;

import mir.routines.pcm2EjbJava.pcm2javaCommon.AddRequiredRoleRoutine;
import mir.routines.pcm2EjbJava.pcm2javaCommon.CreateJavaClassRoutine;
import mir.routines.pcm2EjbJava.pcm2javaCommon.CreateJavaInterfaceRoutine;
import mir.routines.pcm2EjbJava.pcm2javaCommon.CreateParameterRoutine;
import mir.routines.pcm2EjbJava.pcm2javaCommon.RemoveRequiredRoleRoutine;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends mir.routines.pcm2javaCommon.RoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2EjbJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2EjbJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaClassRoutine routine = new CreateJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToClass, containingPackage, className);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterface(final Interface pcmInterface, final org.emftext.language.java.containers.Package containingPackage) {
    mir.routines.pcm2EjbJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2EjbJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaInterfaceRoutine routine = new CreateJavaInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInterface, containingPackage);
    return routine.applyRoutine();
  }
  
  public boolean addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2EjbJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2EjbJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddRequiredRoleRoutine routine = new AddRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean removeRequiredRole(final RequiredRole requiredRole, final InterfaceRequiringEntity requiringEntity) {
    mir.routines.pcm2EjbJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2EjbJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveRequiredRoleRoutine routine = new RemoveRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole, requiringEntity);
    return routine.applyRoutine();
  }
  
  public boolean createParameter(final Parameter parameter) {
    mir.routines.pcm2EjbJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2EjbJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateParameterRoutine routine = new CreateParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, parameter);
    return routine.applyRoutine();
  }
}
