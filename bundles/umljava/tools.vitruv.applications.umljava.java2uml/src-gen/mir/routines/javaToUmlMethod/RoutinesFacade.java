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
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean createUmlClassMethod(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine effect = new mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine(this.executionState, calledBy, jMeth, jClassifier);
    return effect.applyRoutine();
  }
  
  public boolean addUmlOperationToClass(final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
    mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine effect = new mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine(this.executionState, calledBy, uClass, uOperation);
    return effect.applyRoutine();
  }
  
  public boolean addUmlOperationToEnum(final Enumeration uEnum, final Operation uOperation) {
    mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine effect = new mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine(this.executionState, calledBy, uEnum, uOperation);
    return effect.applyRoutine();
  }
  
  public boolean createUmlInterfaceMethod(final InterfaceMethod jMeth, final Interface jInterface) {
    mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine effect = new mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine(this.executionState, calledBy, jMeth, jInterface);
    return effect.applyRoutine();
  }
  
  public boolean createUmlConstructor(final Constructor jConstructor, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine effect = new mir.routines.javaToUmlMethod.CreateUmlConstructorRoutine(this.executionState, calledBy, jConstructor, jClassifier);
    return effect.applyRoutine();
  }
  
  public boolean deleteUmlMethod(final Member jMem) {
    mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine effect = new mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine(this.executionState, calledBy, jMem);
    return effect.applyRoutine();
  }
  
  public boolean setUmlMethodAbstract(final ClassMethod jMeth, final Boolean isAbstract) {
    mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine effect = new mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine(this.executionState, calledBy, jMeth, isAbstract);
    return effect.applyRoutine();
  }
  
  public boolean setUmlMethodFinal(final Method jMethod, final Boolean isFinal) {
    mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine effect = new mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine(this.executionState, calledBy, jMethod, isFinal);
    return effect.applyRoutine();
  }
  
  public boolean setUmlFeatureStatic(final AnnotableAndModifiable jElem, final Boolean isStatic) {
    mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine effect = new mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine(this.executionState, calledBy, jElem, isStatic);
    return effect.applyRoutine();
  }
  
  public boolean createUmlParameter(final Parametrizable jMeth, final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.CreateUmlParameterRoutine effect = new mir.routines.javaToUmlMethod.CreateUmlParameterRoutine(this.executionState, calledBy, jMeth, jParam);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine effect = new mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine(this.executionState, calledBy, jParam);
    return effect.applyRoutine();
  }
  
  public boolean changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine effect = new mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine(this.executionState, calledBy, jParam, jType);
    return effect.applyRoutine();
  }
  
  public boolean changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine effect = new mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine(this.executionState, calledBy, jMeth, jType);
    return effect.applyRoutine();
  }
  
  public boolean changeUmlNamedElementVisibility(final AnnotableAndModifiable jElem, final Modifier mod) {
    mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine effect = new mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine(this.executionState, calledBy, jElem, mod);
    return effect.applyRoutine();
  }
  
  public boolean renameUmlNamedElement(final NamedElement jElement) {
    mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine effect = new mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine(this.executionState, calledBy, jElement);
    return effect.applyRoutine();
  }
}
