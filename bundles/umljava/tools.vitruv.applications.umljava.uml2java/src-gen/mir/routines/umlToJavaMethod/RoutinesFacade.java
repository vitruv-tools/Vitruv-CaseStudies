package mir.routines.umlToJavaMethod;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createJavaMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaMethodRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaClassMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaConstructor(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaMethod(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine routine = new mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodReturnType(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine routine = new mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setStatic(final Feature uFeat) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetStaticRoutine routine = new mir.routines.umlToJavaMethod.SetStaticRoutine(_routinesFacade, _reactionExecutionState, _caller, uFeat);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodAbstract(final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine routine = new mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterfaceMethod(final Interface uInterface, final Operation uOperation) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodFinal(final Operation uOperation, final Boolean isFinal) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine routine = new mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaElementVisibility(final NamedElement uElem) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.ChangeJavaElementVisibilityRoutine routine = new mir.routines.umlToJavaMethod.ChangeJavaElementVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, uElem);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaNamedElement(final NamedElement uElem, final String name) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine routine = new mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, uElem, name);
    return routine.applyRoutine();
  }
  
  public boolean createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.CreateJavaParameterRoutine routine = new mir.routines.umlToJavaMethod.CreateJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, uMeth, umlParam);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaParameter(final Parameter uParam) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine routine = new mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, uParam);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaParameterType(final Parameter uParam, final Type uType) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine routine = new mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uParam, uType);
    return routine.applyRoutine();
  }
  
  public boolean adaptJavaParametertoDirectionChange(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
    mir.routines.umlToJavaMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("UmlToJavaMethod"));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine routine = new mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation, uParam, oldDirection, newDirection);
    return routine.applyRoutine();
  }
}
