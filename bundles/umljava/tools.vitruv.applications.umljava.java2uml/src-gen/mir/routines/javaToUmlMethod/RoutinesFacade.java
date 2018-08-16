package mir.routines.javaToUmlMethod;

import mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine;
import mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine;
import mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine;
import mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine;
import mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine;
import mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine;
import mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine;
import mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine;
import mir.routines.javaToUmlMethod.CreateUmlParameterRoutine;
import mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine;
import mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine;
import mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine;
import mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine;
import mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine;
import mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.TypeReference;
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
  
  public boolean createUmlClassMethod(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlClassMethodRoutine routine = new CreateUmlClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean addUmlOperationToClass(final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlOperationToClassRoutine routine = new AddUmlOperationToClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uClass, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean addUmlOperationToEnum(final Enumeration uEnum, final Operation uOperation) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlOperationToEnumRoutine routine = new AddUmlOperationToEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, uEnum, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterfaceMethod(final InterfaceMethod jMeth, final Interface jInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlInterfaceMethodRoutine routine = new CreateUmlInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean createUmlConstructor(final Constructor jConstructor, final ConcreteClassifier jClassifier) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlConstructorRoutine routine = new CreateUmlConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, jConstructor, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlMethod(final Member jMem) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteUmlMethodRoutine routine = new DeleteUmlMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMem);
    return routine.applyRoutine();
  }
  
  public boolean setUmlMethodAbstract(final ClassMethod jMeth, final Boolean isAbstract) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetUmlMethodAbstractRoutine routine = new SetUmlMethodAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, isAbstract);
    return routine.applyRoutine();
  }
  
  public boolean setUmlMethodFinal(final Method jMethod, final Boolean isFinal) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetUmlMethodFinalRoutine routine = new SetUmlMethodFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jMethod, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean setUmlFeatureStatic(final AnnotableAndModifiable jElem, final Boolean isStatic) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetUmlFeatureStaticRoutine routine = new SetUmlFeatureStaticRoutine(_routinesFacade, _reactionExecutionState, _caller, jElem, isStatic);
    return routine.applyRoutine();
  }
  
  public boolean createUmlParameter(final Parametrizable jMeth, final OrdinaryParameter jParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlParameterRoutine routine = new CreateUmlParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jParam);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaParameter(final OrdinaryParameter jParam) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaParameterRoutine routine = new DeleteJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jParam);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeUmlParameterTypeRoutine routine = new ChangeUmlParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jParam, jType);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeUmlReturnTypeRoutine routine = new ChangeUmlReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jType);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlNamedElementVisibility(final AnnotableAndModifiable jElem, final Modifier mod) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeUmlNamedElementVisibilityRoutine routine = new ChangeUmlNamedElementVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, jElem, mod);
    return routine.applyRoutine();
  }
  
  public boolean renameUmlNamedElement(final NamedElement jElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameUmlNamedElementRoutine routine = new RenameUmlNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, jElement);
    return routine.applyRoutine();
  }
}
