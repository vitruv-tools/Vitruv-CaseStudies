package mir.routines.javaToUmlmethod;

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
  
  public void createUmlClassMethod(final ClassMethod jMeth, final org.emftext.language.java.classifiers.Class jClass) {
    mir.routines.javaToUmlmethod.CreateUmlClassMethodRoutine effect = new mir.routines.javaToUmlmethod.CreateUmlClassMethodRoutine(this.executionState, calledBy,
    	jMeth, jClass);
    effect.applyRoutine();
  }
  
  public void createUmlInterfaceMethod(final InterfaceMethod jMeth, final Interface jI) {
    mir.routines.javaToUmlmethod.CreateUmlInterfaceMethodRoutine effect = new mir.routines.javaToUmlmethod.CreateUmlInterfaceMethodRoutine(this.executionState, calledBy,
    	jMeth, jI);
    effect.applyRoutine();
  }
  
  public void deleteUmlMethod(final Member jMem) {
    mir.routines.javaToUmlmethod.DeleteUmlMethodRoutine effect = new mir.routines.javaToUmlmethod.DeleteUmlMethodRoutine(this.executionState, calledBy,
    	jMem);
    effect.applyRoutine();
  }
  
  public void setUmlMethodAbstract(final ClassMethod jMeth, final Integer isAbstract) {
    mir.routines.javaToUmlmethod.SetUmlMethodAbstractRoutine effect = new mir.routines.javaToUmlmethod.SetUmlMethodAbstractRoutine(this.executionState, calledBy,
    	jMeth, isAbstract);
    effect.applyRoutine();
  }
  
  public void setUmlFeatureStatic(final AnnotableAndModifiable jElem, final Integer isStatic) {
    mir.routines.javaToUmlmethod.SetUmlFeatureStaticRoutine effect = new mir.routines.javaToUmlmethod.SetUmlFeatureStaticRoutine(this.executionState, calledBy,
    	jElem, isStatic);
    effect.applyRoutine();
  }
  
  public void createUmlParameter(final Method jMeth, final OrdinaryParameter jParam) {
    mir.routines.javaToUmlmethod.CreateUmlParameterRoutine effect = new mir.routines.javaToUmlmethod.CreateUmlParameterRoutine(this.executionState, calledBy,
    	jMeth, jParam);
    effect.applyRoutine();
  }
  
  public void deleteJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUmlmethod.DeleteJavaParameterRoutine effect = new mir.routines.javaToUmlmethod.DeleteJavaParameterRoutine(this.executionState, calledBy,
    	jParam);
    effect.applyRoutine();
  }
  
  public void changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    mir.routines.javaToUmlmethod.ChangeUmlParameterTypeRoutine effect = new mir.routines.javaToUmlmethod.ChangeUmlParameterTypeRoutine(this.executionState, calledBy,
    	jParam, jType);
    effect.applyRoutine();
  }
  
  public void changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.javaToUmlmethod.ChangeUmlReturnTypeRoutine effect = new mir.routines.javaToUmlmethod.ChangeUmlReturnTypeRoutine(this.executionState, calledBy,
    	jMeth, jType);
    effect.applyRoutine();
  }
}
