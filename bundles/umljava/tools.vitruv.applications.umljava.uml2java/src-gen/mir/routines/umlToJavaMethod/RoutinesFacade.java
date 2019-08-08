package mir.routines.umlToJavaMethod;

import mir.routines.umlToJavaMethod.AdaptParameterDirectionChangedFromReturnRoutine;
import mir.routines.umlToJavaMethod.AdaptParameterDirectionChangedToReturnRoutine;
import mir.routines.umlToJavaMethod.ChangeJavaElementVisibilityRoutine;
import mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine;
import mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine;
import mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine;
import mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine;
import mir.routines.umlToJavaMethod.CreateJavaMethodRoutine;
import mir.routines.umlToJavaMethod.CreateJavaParameterRoutine;
import mir.routines.umlToJavaMethod.CreateMissingJavaParameterRoutine;
import mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine;
import mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine;
import mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine;
import mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine;
import mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine;
import mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine;
import mir.routines.umlToJavaMethod.SetStaticRoutine;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
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
    this.umlToJavaTypePropagation = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaTypePropagation")));
  }
  
  public final mir.routines.umlToJavaTypePropagation.RoutinesFacade umlToJavaTypePropagation;
  
  public boolean createJavaMethod(final Classifier uClassifier, final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaMethodRoutine routine = new CreateJavaMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaClassMethod(final Classifier uClassifier, final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaClassMethodRoutine routine = new CreateJavaClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaConstructor(final Classifier uClassifier, final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaConstructorRoutine routine = new CreateJavaConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaMethod(final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaMethodRoutine routine = new DeleteJavaMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodReturnType(final Operation uOperation, final Parameter uParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetJavaMethodReturnTypeRoutine routine = new SetJavaMethodReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation, uParam);
    return routine.applyRoutine();
  }
  
  public boolean setStatic(final Feature uFeat) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetStaticRoutine routine = new SetStaticRoutine(_routinesFacade, _reactionExecutionState, _caller, uFeat);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodAbstract(final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetJavaMethodAbstractRoutine routine = new SetJavaMethodAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createJavaInterfaceMethod(final Interface uInterface, final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaInterfaceMethodRoutine routine = new CreateJavaInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, uInterface, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean setJavaMethodFinal(final Operation uOperation, final Boolean isFinal) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetJavaMethodFinalRoutine routine = new SetJavaMethodFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaElementVisibility(final NamedElement uElem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeJavaElementVisibilityRoutine routine = new ChangeJavaElementVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, uElem);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaNamedElement(final NamedElement uElem, final String name) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameJavaNamedElementRoutine routine = new RenameJavaNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, uElem, name);
    return routine.applyRoutine();
  }
  
  public boolean createMissingJavaParameter(final Parameter uParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateMissingJavaParameterRoutine routine = new CreateMissingJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, uParameter);
    return routine.applyRoutine();
  }
  
  public boolean createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaParameterRoutine routine = new CreateJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, uMeth, umlParam);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaParameter(final Parameter uParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaParameterRoutine routine = new DeleteJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, uParam);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaParameterType(final Parameter uParam, final Type uType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeJavaParameterTypeRoutine routine = new ChangeJavaParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uParam, uType);
    return routine.applyRoutine();
  }
  
  public boolean adaptParameterDirectionChangedFromReturn(final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AdaptParameterDirectionChangedFromReturnRoutine routine = new AdaptParameterDirectionChangedFromReturnRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean adaptParameterDirectionChangedToReturn(final Operation uOperation, final Parameter uParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AdaptParameterDirectionChangedToReturnRoutine routine = new AdaptParameterDirectionChangedToReturnRoutine(_routinesFacade, _reactionExecutionState, _caller, uOperation, uParam);
    return routine.applyRoutine();
  }
}
