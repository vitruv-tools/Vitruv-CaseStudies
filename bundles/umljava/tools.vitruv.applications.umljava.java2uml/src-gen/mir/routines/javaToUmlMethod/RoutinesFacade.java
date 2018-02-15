package mir.routines.javaToUmlMethod;

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
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createUmlClassMethod(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean addUmlOperationToClass(final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine routine = new mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine(_routinesFacade, _reactionExecutionState, _caller, uClass, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean addUmlOperationToEnum(final Enumeration uEnum, final Operation uOperation) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine routine = new mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, uEnum, uOperation);
    return routine.applyRoutine();
  }
  
  public boolean createUmlInterfaceMethod(final InterfaceMethod jMeth, final Interface jInterface) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jInterface);
    return routine.applyRoutine();
  }
  
  public boolean createUmlConstructor(final Constructor jConstructor, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine(_routinesFacade, _reactionExecutionState, _caller, jConstructor, jClassifier);
    return routine.applyRoutine();
  }
  
  public boolean deleteUmlMethod(final Member jMem) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine routine = new mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine(_routinesFacade, _reactionExecutionState, _caller, jMem);
    return routine.applyRoutine();
  }
  
  public boolean setUmlMethodAbstract(final ClassMethod jMeth, final Boolean isAbstract) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine routine = new mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, isAbstract);
    return routine.applyRoutine();
  }
  
  public boolean setUmlMethodFinal(final Method jMethod, final Boolean isFinal) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine routine = new mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jMethod, isFinal);
    return routine.applyRoutine();
  }
  
  public boolean setUmlFeatureStatic(final AnnotableAndModifiable jElem, final Boolean isStatic) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine routine = new mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine(_routinesFacade, _reactionExecutionState, _caller, jElem, isStatic);
    return routine.applyRoutine();
  }
  
  public boolean createUmlParameter(final Parametrizable jMeth, final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.CreateUmlParameterRoutine routine = new mir.routines.javaToUmlMethod.CreateUmlParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jParam);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine routine = new mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, jParam);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine routine = new mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jParam, jType);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine routine = new mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jMeth, jType);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlNamedElementVisibility(final AnnotableAndModifiable jElem, final Modifier mod) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine routine = new mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine(_routinesFacade, _reactionExecutionState, _caller, jElem, mod);
    return routine.applyRoutine();
  }
  
  public boolean renameUmlNamedElement(final NamedElement jElement) {
    mir.routines.javaToUmlMethod.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlMethod").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine routine = new mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine(_routinesFacade, _reactionExecutionState, _caller, jElement);
    return routine.applyRoutine();
  }
}
