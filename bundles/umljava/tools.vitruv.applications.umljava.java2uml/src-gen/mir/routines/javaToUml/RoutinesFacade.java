package mir.routines.javaToUml;

import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
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
  
  public void createUmlClass(final org.emftext.language.java.classifiers.Class jClass) {
    mir.routines.javaToUml.CreateUmlClassRoutine effect = new mir.routines.javaToUml.CreateUmlClassRoutine(this.executionState, calledBy,
    	jClass);
    effect.applyRoutine();
  }
  
  public void renameUmlClassifier(final ConcreteClassifier jClass) {
    mir.routines.javaToUml.RenameUmlClassifierRoutine effect = new mir.routines.javaToUml.RenameUmlClassifierRoutine(this.executionState, calledBy,
    	jClass);
    effect.applyRoutine();
  }
  
  public void deleteUmlClassifier(final ConcreteClassifier jClass, final CompilationUnit jCompUnit) {
    mir.routines.javaToUml.DeleteUmlClassifierRoutine effect = new mir.routines.javaToUml.DeleteUmlClassifierRoutine(this.executionState, calledBy,
    	jClass, jCompUnit);
    effect.applyRoutine();
  }
  
  public void changeUmlClassVisibility(final org.emftext.language.java.classifiers.Class jClass, final Modifier mod) {
    mir.routines.javaToUml.ChangeUmlClassVisibilityRoutine effect = new mir.routines.javaToUml.ChangeUmlClassVisibilityRoutine(this.executionState, calledBy,
    	jClass, mod);
    effect.applyRoutine();
  }
  
  public void setUmlClassAbstract(final org.emftext.language.java.classifiers.Class jClass, final Integer isAbstract) {
    mir.routines.javaToUml.SetUmlClassAbstractRoutine effect = new mir.routines.javaToUml.SetUmlClassAbstractRoutine(this.executionState, calledBy,
    	jClass, isAbstract);
    effect.applyRoutine();
  }
  
  public void addUmlSuperClassifier(final ConcreteClassifier jClass, final TypeReference jSuper) {
    mir.routines.javaToUml.AddUmlSuperClassifierRoutine effect = new mir.routines.javaToUml.AddUmlSuperClassifierRoutine(this.executionState, calledBy,
    	jClass, jSuper);
    effect.applyRoutine();
  }
  
  public void clearUmlSuperClassifiers(final ConcreteClassifier jClass) {
    mir.routines.javaToUml.ClearUmlSuperClassifiersRoutine effect = new mir.routines.javaToUml.ClearUmlSuperClassifiersRoutine(this.executionState, calledBy,
    	jClass);
    effect.applyRoutine();
  }
  
  public void removeUmlSuperInterface(final Interface jI, final TypeReference jSuper) {
    mir.routines.javaToUml.RemoveUmlSuperInterfaceRoutine effect = new mir.routines.javaToUml.RemoveUmlSuperInterfaceRoutine(this.executionState, calledBy,
    	jI, jSuper);
    effect.applyRoutine();
  }
  
  public void addUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Interface jI) {
    mir.routines.javaToUml.AddUmlClassImplementRoutine effect = new mir.routines.javaToUml.AddUmlClassImplementRoutine(this.executionState, calledBy,
    	jClass, jI);
    effect.applyRoutine();
  }
  
  public void removeUmlClassImplement(final org.emftext.language.java.classifiers.Class jClass, final Interface jI) {
    mir.routines.javaToUml.RemoveUmlClassImplementRoutine effect = new mir.routines.javaToUml.RemoveUmlClassImplementRoutine(this.executionState, calledBy,
    	jClass, jI);
    effect.applyRoutine();
  }
  
  public void setUmlClassFinal(final org.emftext.language.java.classifiers.Class jClass, final Integer isFinal) {
    mir.routines.javaToUml.SetUmlClassFinalRoutine effect = new mir.routines.javaToUml.SetUmlClassFinalRoutine(this.executionState, calledBy,
    	jClass, isFinal);
    effect.applyRoutine();
  }
  
  public void createUmlMethod(final Method jMeth, final ConcreteClassifier jClass) {
    mir.routines.javaToUml.CreateUmlMethodRoutine effect = new mir.routines.javaToUml.CreateUmlMethodRoutine(this.executionState, calledBy,
    	jMeth, jClass);
    effect.applyRoutine();
  }
  
  public void renameUmlMethod(final Method jMeth) {
    mir.routines.javaToUml.RenameUmlMethodRoutine effect = new mir.routines.javaToUml.RenameUmlMethodRoutine(this.executionState, calledBy,
    	jMeth);
    effect.applyRoutine();
  }
  
  public void deleteUmlMethod(final Method jMeth) {
    mir.routines.javaToUml.DeleteUmlMethodRoutine effect = new mir.routines.javaToUml.DeleteUmlMethodRoutine(this.executionState, calledBy,
    	jMeth);
    effect.applyRoutine();
  }
  
  public void changeUmlMethodVisibility(final Modifier mod, final ClassMethod jMeth) {
    mir.routines.javaToUml.ChangeUmlMethodVisibilityRoutine effect = new mir.routines.javaToUml.ChangeUmlMethodVisibilityRoutine(this.executionState, calledBy,
    	mod, jMeth);
    effect.applyRoutine();
  }
  
  public void setUmlMethodAbstract(final ClassMethod jMeth, final Integer isAbstract) {
    mir.routines.javaToUml.SetUmlMethodAbstractRoutine effect = new mir.routines.javaToUml.SetUmlMethodAbstractRoutine(this.executionState, calledBy,
    	jMeth, isAbstract);
    effect.applyRoutine();
  }
  
  public void setUmlMethodStatic(final ClassMethod jMeth, final Integer isStatic) {
    mir.routines.javaToUml.SetUmlMethodStaticRoutine effect = new mir.routines.javaToUml.SetUmlMethodStaticRoutine(this.executionState, calledBy,
    	jMeth, isStatic);
    effect.applyRoutine();
  }
  
  public void createUmlParameter(final Method jMeth, final OrdinaryParameter jParam) {
    mir.routines.javaToUml.CreateUmlParameterRoutine effect = new mir.routines.javaToUml.CreateUmlParameterRoutine(this.executionState, calledBy,
    	jMeth, jParam);
    effect.applyRoutine();
  }
  
  public void renameJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUml.RenameJavaParameterRoutine effect = new mir.routines.javaToUml.RenameJavaParameterRoutine(this.executionState, calledBy,
    	jParam);
    effect.applyRoutine();
  }
  
  public void deleteJavaParameter(final OrdinaryParameter jParam) {
    mir.routines.javaToUml.DeleteJavaParameterRoutine effect = new mir.routines.javaToUml.DeleteJavaParameterRoutine(this.executionState, calledBy,
    	jParam);
    effect.applyRoutine();
  }
  
  public void changeUmlParameterType(final OrdinaryParameter jParam, final TypeReference jType) {
    mir.routines.javaToUml.ChangeUmlParameterTypeRoutine effect = new mir.routines.javaToUml.ChangeUmlParameterTypeRoutine(this.executionState, calledBy,
    	jParam, jType);
    effect.applyRoutine();
  }
  
  public void changeUmlReturnType(final Method jMeth, final TypeReference jType) {
    mir.routines.javaToUml.ChangeUmlReturnTypeRoutine effect = new mir.routines.javaToUml.ChangeUmlReturnTypeRoutine(this.executionState, calledBy,
    	jMeth, jType);
    effect.applyRoutine();
  }
  
  public void createUmlAttribute(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    mir.routines.javaToUml.CreateUmlAttributeRoutine effect = new mir.routines.javaToUml.CreateUmlAttributeRoutine(this.executionState, calledBy,
    	jClass, jAttr);
    effect.applyRoutine();
  }
  
  public void renameUmlAttribute(final Field jAttr) {
    mir.routines.javaToUml.RenameUmlAttributeRoutine effect = new mir.routines.javaToUml.RenameUmlAttributeRoutine(this.executionState, calledBy,
    	jAttr);
    effect.applyRoutine();
  }
  
  public void deleteUmlAttribute(final Field jAttr) {
    mir.routines.javaToUml.DeleteUmlAttributeRoutine effect = new mir.routines.javaToUml.DeleteUmlAttributeRoutine(this.executionState, calledBy,
    	jAttr);
    effect.applyRoutine();
  }
  
  public void changeUmlAttributeVisibility(final Field jAttr, final Modifier mod) {
    mir.routines.javaToUml.ChangeUmlAttributeVisibilityRoutine effect = new mir.routines.javaToUml.ChangeUmlAttributeVisibilityRoutine(this.executionState, calledBy,
    	jAttr, mod);
    effect.applyRoutine();
  }
  
  public void setUmlAttributeStatic(final Field jAttr, final Integer isStatic) {
    mir.routines.javaToUml.SetUmlAttributeStaticRoutine effect = new mir.routines.javaToUml.SetUmlAttributeStaticRoutine(this.executionState, calledBy,
    	jAttr, isStatic);
    effect.applyRoutine();
  }
  
  public void setUmlAttributeFinal(final Field jAttr, final Integer isFinal) {
    mir.routines.javaToUml.SetUmlAttributeFinalRoutine effect = new mir.routines.javaToUml.SetUmlAttributeFinalRoutine(this.executionState, calledBy,
    	jAttr, isFinal);
    effect.applyRoutine();
  }
}
