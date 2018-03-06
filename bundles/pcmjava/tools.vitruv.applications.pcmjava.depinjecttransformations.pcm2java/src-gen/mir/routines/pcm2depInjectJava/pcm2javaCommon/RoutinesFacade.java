package mir.routines.pcm2depInjectJava.pcm2javaCommon;

import mir.routines.pcm2depInjectJava.pcm2javaCommon.AddAssemblyContextToComposedStructureRoutine;
import mir.routines.pcm2depInjectJava.pcm2javaCommon.AddRequiredRoleRoutine;
import mir.routines.pcm2depInjectJava.pcm2javaCommon.CreateJavaClassRoutine;
import mir.routines.pcm2depInjectJava.pcm2javaCommon.CreateParameterRoutine;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.Parameter;
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
  
  public boolean addAssemblyContextToComposedStructure(final ComposedStructure composedStructure, final AssemblyContext assemblyContext) {
    mir.routines.pcm2depInjectJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2depInjectJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddAssemblyContextToComposedStructureRoutine routine = new AddAssemblyContextToComposedStructureRoutine(_routinesFacade, _reactionExecutionState, _caller, composedStructure, assemblyContext);
    return routine.applyRoutine();
  }
  
  public boolean createJavaClass(final NamedElement sourceElementMappedToClass, final org.emftext.language.java.containers.Package containingPackage, final String className) {
    mir.routines.pcm2depInjectJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2depInjectJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaClassRoutine routine = new CreateJavaClassRoutine(_routinesFacade, _reactionExecutionState, _caller, sourceElementMappedToClass, containingPackage, className);
    return routine.applyRoutine();
  }
  
  public boolean addRequiredRole(final OperationRequiredRole requiredRole) {
    mir.routines.pcm2depInjectJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2depInjectJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddRequiredRoleRoutine routine = new AddRequiredRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, requiredRole);
    return routine.applyRoutine();
  }
  
  public boolean createParameter(final Parameter parameter) {
    mir.routines.pcm2depInjectJava.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().subPathTo("pcm2depInjectJava"));
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateParameterRoutine routine = new CreateParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, parameter);
    return routine.applyRoutine();
  }
}
