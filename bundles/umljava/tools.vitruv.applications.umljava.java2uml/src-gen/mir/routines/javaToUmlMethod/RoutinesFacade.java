package mir.routines.javaToUmlMethod;

import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createUmlClassMethod(final ClassMethod jMeth, final ConcreteClassifier jClassifier) {
    mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine effect = new mir.routines.javaToUmlMethod.CreateUmlClassMethodRoutine(this.executionState, calledBy,
    	jMeth, jClassifier);
    effect.applyRoutine();
  }
  
  public void createUmlInterfaceMethod(final InterfaceMethod jMeth, final Interface jI) {
    mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine effect = new mir.routines.javaToUmlMethod.CreateUmlInterfaceMethodRoutine(this.executionState, calledBy,
    	jMeth, jI);
    effect.applyRoutine();
  }
  
  public void deleteUmlMethod(final Member jMem) {
    mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine effect = new mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine(this.executionState, calledBy,
    	jMem);
    effect.applyRoutine();
  }
  
  public void setUmlMethodAbstract(final ClassMethod jMeth, final Boolean isAbstract) {
    mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine effect = new mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine(this.executionState, calledBy,
    	jMeth, isAbstract);
    effect.applyRoutine();
  }
  
  public void setUmlFeatureStatic(final AnnotableAndModifiable jElem, final Boolean isStatic) {
    mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine effect = new mir.routines.javaToUmlMethod.SetUmlFeatureStaticRoutine(this.executionState, calledBy,
    	jElem, isStatic);
    effect.applyRoutine();
  }
  
  public void createUmlParameter(final Method jMeth, final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.CreateUmlParameterRoutine effect = new mir.routines.javaToUmlMethod.CreateUmlParameterRoutine(this.executionState, calledBy,
    	jMeth, jParam);
    effect.applyRoutine();
  }
  
  public void deleteJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine effect = new mir.routines.javaToUmlMethod.DeleteJavaParameterRoutine(this.executionState, calledBy,
    	jParam);
    effect.applyRoutine();
  }
  
  public void changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine effect = new mir.routines.javaToUmlMethod.ChangeUmlParameterTypeRoutine(this.executionState, calledBy,
    	jParam, jType);
    effect.applyRoutine();
  }
  
  public void changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine effect = new mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine(this.executionState, calledBy,
    	jMeth, jType);
    effect.applyRoutine();
  }
}
