package mir.routines.umlToJava;

import org.eclipse.uml2.uml.Feature;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createJavaClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.CreateJavaClassRoutine effect = new mir.routines.umlToJava.CreateJavaClassRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void renameJavaClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.RenameJavaClassRoutine effect = new mir.routines.umlToJava.RenameJavaClassRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void deleteJavaClass(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.DeleteJavaClassRoutine effect = new mir.routines.umlToJava.DeleteJavaClassRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void setJavaClassFinal(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.SetJavaClassFinalRoutine effect = new mir.routines.umlToJava.SetJavaClassFinalRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void setJavaClassAbstract(final org.eclipse.uml2.uml.Class umlClass) {
    mir.routines.umlToJava.SetJavaClassAbstractRoutine effect = new mir.routines.umlToJava.SetJavaClassAbstractRoutine(this.executionState, calledBy,
    	umlClass);
    effect.applyRoutine();
  }
  
  public void changeJavaClassVisibility(final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.ChangeJavaClassVisibilityRoutine effect = new mir.routines.umlToJava.ChangeJavaClassVisibilityRoutine(this.executionState, calledBy,
    	uClass);
    effect.applyRoutine();
  }
  
  public void changeJavaSuperClass(final org.eclipse.uml2.uml.Class superUMLClass, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.ChangeJavaSuperClassRoutine effect = new mir.routines.umlToJava.ChangeJavaSuperClassRoutine(this.executionState, calledBy,
    	superUMLClass, uClass);
    effect.applyRoutine();
  }
  
  public void deleteJavaSuperClass(final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.DeleteJavaSuperClassRoutine effect = new mir.routines.umlToJava.DeleteJavaSuperClassRoutine(this.executionState, calledBy,
    	uClass);
    effect.applyRoutine();
  }
  
  public void createJavaInterfaceImplement(final Interface uI, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.CreateJavaInterfaceImplementRoutine effect = new mir.routines.umlToJava.CreateJavaInterfaceImplementRoutine(this.executionState, calledBy,
    	uI, uClass);
    effect.applyRoutine();
  }
  
  public void deleteJavaInterfaceImplement(final Interface uI, final org.eclipse.uml2.uml.Class uClass) {
    mir.routines.umlToJava.DeleteJavaInterfaceImplementRoutine effect = new mir.routines.umlToJava.DeleteJavaInterfaceImplementRoutine(this.executionState, calledBy,
    	uI, uClass);
    effect.applyRoutine();
  }
  
  public void createJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJava.CreateJavaInterfaceRoutine effect = new mir.routines.umlToJava.CreateJavaInterfaceRoutine(this.executionState, calledBy,
    	umlInterface);
    effect.applyRoutine();
  }
  
  public void renameJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJava.RenameJavaInterfaceRoutine effect = new mir.routines.umlToJava.RenameJavaInterfaceRoutine(this.executionState, calledBy,
    	umlInterface);
    effect.applyRoutine();
  }
  
  public void deleteJavaInterface(final Interface umlInterface) {
    mir.routines.umlToJava.DeleteJavaInterfaceRoutine effect = new mir.routines.umlToJava.DeleteJavaInterfaceRoutine(this.executionState, calledBy,
    	umlInterface);
    effect.applyRoutine();
  }
  
  public void changeJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJava.ChangeJavaSuperInterfaceRoutine effect = new mir.routines.umlToJava.ChangeJavaSuperInterfaceRoutine(this.executionState, calledBy,
    	superUMLInterface, uI);
    effect.applyRoutine();
  }
  
  public void deleteJavaSuperInterface(final Interface superUMLInterface, final Interface uI) {
    mir.routines.umlToJava.DeleteJavaSuperInterfaceRoutine effect = new mir.routines.umlToJava.DeleteJavaSuperInterfaceRoutine(this.executionState, calledBy,
    	superUMLInterface, uI);
    effect.applyRoutine();
  }
  
  public void createJavaAttribute(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute) {
    mir.routines.umlToJava.CreateJavaAttributeRoutine effect = new mir.routines.umlToJava.CreateJavaAttributeRoutine(this.executionState, calledBy,
    	umlClass, umlAttribute);
    effect.applyRoutine();
  }
  
  public void renameJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJava.RenameJavaAttributeRoutine effect = new mir.routines.umlToJava.RenameJavaAttributeRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void deleteJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJava.DeleteJavaAttributeRoutine effect = new mir.routines.umlToJava.DeleteJavaAttributeRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void createJavaMethod(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp) {
    mir.routines.umlToJava.CreateJavaMethodRoutine effect = new mir.routines.umlToJava.CreateJavaMethodRoutine(this.executionState, calledBy,
    	uClass, umlOp);
    effect.applyRoutine();
  }
  
  public void renameJavaMethod(final Operation umlOp) {
    mir.routines.umlToJava.RenameJavaMethodRoutine effect = new mir.routines.umlToJava.RenameJavaMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void deleteJavaMethod(final Operation umlOp) {
    mir.routines.umlToJava.DeleteJavaMethodRoutine effect = new mir.routines.umlToJava.DeleteJavaMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    mir.routines.umlToJava.CreateJavaParameterRoutine effect = new mir.routines.umlToJava.CreateJavaParameterRoutine(this.executionState, calledBy,
    	uMeth, umlParam);
    effect.applyRoutine();
  }
  
  public void renameJavaParameter(final Parameter uParam) {
    mir.routines.umlToJava.RenameJavaParameterRoutine effect = new mir.routines.umlToJava.RenameJavaParameterRoutine(this.executionState, calledBy,
    	uParam);
    effect.applyRoutine();
  }
  
  public void deleteJavaParameter(final Parameter uParam) {
    mir.routines.umlToJava.DeleteJavaParameterRoutine effect = new mir.routines.umlToJava.DeleteJavaParameterRoutine(this.executionState, calledBy,
    	uParam);
    effect.applyRoutine();
  }
  
  public void changeJavaParameterType(final Parameter uParam, final Type uType) {
    mir.routines.umlToJava.ChangeJavaParameterTypeRoutine effect = new mir.routines.umlToJava.ChangeJavaParameterTypeRoutine(this.executionState, calledBy,
    	uParam, uType);
    effect.applyRoutine();
  }
  
  public void setJavaMethodReturnType(final Parameter uParam, final Integer changedToVoid) {
    mir.routines.umlToJava.SetJavaMethodReturnTypeRoutine effect = new mir.routines.umlToJava.SetJavaMethodReturnTypeRoutine(this.executionState, calledBy,
    	uParam, changedToVoid);
    effect.applyRoutine();
  }
  
  public void changeJavaAttributeType(final Property uAttr, final Type uType) {
    mir.routines.umlToJava.ChangeJavaAttributeTypeRoutine effect = new mir.routines.umlToJava.ChangeJavaAttributeTypeRoutine(this.executionState, calledBy,
    	uAttr, uType);
    effect.applyRoutine();
  }
  
  public void setJavaAttributeFinal(final Property umlAttr) {
    mir.routines.umlToJava.SetJavaAttributeFinalRoutine effect = new mir.routines.umlToJava.SetJavaAttributeFinalRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void setStatic(final Feature uFeat) {
    mir.routines.umlToJava.SetStaticRoutine effect = new mir.routines.umlToJava.SetStaticRoutine(this.executionState, calledBy,
    	uFeat);
    effect.applyRoutine();
  }
  
  public void setJavaMethodAbstract(final Operation umlOp) {
    mir.routines.umlToJava.SetJavaMethodAbstractRoutine effect = new mir.routines.umlToJava.SetJavaMethodAbstractRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void changeJavaAttributeVisibility(final Property umlAttr) {
    mir.routines.umlToJava.ChangeJavaAttributeVisibilityRoutine effect = new mir.routines.umlToJava.ChangeJavaAttributeVisibilityRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void changeJavaMethodVisibility(final Operation umlOp) {
    mir.routines.umlToJava.ChangeJavaMethodVisibilityRoutine effect = new mir.routines.umlToJava.ChangeJavaMethodVisibilityRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
  
  public void createJavaInterfaceMethod(final Operation umlOp) {
    mir.routines.umlToJava.CreateJavaInterfaceMethodRoutine effect = new mir.routines.umlToJava.CreateJavaInterfaceMethodRoutine(this.executionState, calledBy,
    	umlOp);
    effect.applyRoutine();
  }
}
