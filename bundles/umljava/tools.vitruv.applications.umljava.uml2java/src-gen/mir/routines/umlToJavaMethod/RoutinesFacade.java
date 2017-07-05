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
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createJavaMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.CreateJavaMethodRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaMethodRoutine(this.executionState, calledBy,
    	uClassifier, uOperation);
    effect.applyRoutine();
  }
  
  public void createJavaClassMethod(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine(this.executionState, calledBy,
    	uClassifier, uOperation);
    effect.applyRoutine();
  }
  
  public void createJavaConstructor(final Classifier uClassifier, final Operation uOperation) {
    mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine(this.executionState, calledBy,
    	uClassifier, uOperation);
    effect.applyRoutine();
  }
  
  public void deleteJavaMethod(final Operation uOperation) {
    mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine effect = new mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine(this.executionState, calledBy,
    	uOperation);
    effect.applyRoutine();
  }
  
  public void setJavaMethodReturnType(final Operation uOperation) {
    mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine effect = new mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine(this.executionState, calledBy,
    	uOperation);
    effect.applyRoutine();
  }
  
  public void setStatic(final Feature uFeat) {
    mir.routines.umlToJavaMethod.SetStaticRoutine effect = new mir.routines.umlToJavaMethod.SetStaticRoutine(this.executionState, calledBy,
    	uFeat);
    effect.applyRoutine();
  }
  
  public void setJavaMethodAbstract(final Operation uOperation) {
    mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine effect = new mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine(this.executionState, calledBy,
    	uOperation);
    effect.applyRoutine();
  }
  
  public void createJavaInterfaceMethod(final Interface uInterface, final Operation uOperation) {
    mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine(this.executionState, calledBy,
    	uInterface, uOperation);
    effect.applyRoutine();
  }
  
  public void setJavaMethodFinal(final Operation uOperation, final Boolean isFinal) {
    mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine effect = new mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine(this.executionState, calledBy,
    	uOperation, isFinal);
    effect.applyRoutine();
  }
  
  public void changeJavaElementVisibility(final NamedElement uElem) {
    mir.routines.umlToJavaMethod.ChangeJavaElementVisibilityRoutine effect = new mir.routines.umlToJavaMethod.ChangeJavaElementVisibilityRoutine(this.executionState, calledBy,
    	uElem);
    effect.applyRoutine();
  }
  
  public void renameJavaNamedElement(final NamedElement uElem, final String name) {
    mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine effect = new mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine(this.executionState, calledBy,
    	uElem, name);
    effect.applyRoutine();
  }
  
  public void createJavaParameter(final Operation uMeth, final Parameter umlParam) {
    mir.routines.umlToJavaMethod.CreateJavaParameterRoutine effect = new mir.routines.umlToJavaMethod.CreateJavaParameterRoutine(this.executionState, calledBy,
    	uMeth, umlParam);
    effect.applyRoutine();
  }
  
  public void deleteJavaParameter(final Parameter uParam) {
    mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine effect = new mir.routines.umlToJavaMethod.DeleteJavaParameterRoutine(this.executionState, calledBy,
    	uParam);
    effect.applyRoutine();
  }
  
  public void changeJavaParameterType(final Parameter uParam, final Type uType) {
    mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine effect = new mir.routines.umlToJavaMethod.ChangeJavaParameterTypeRoutine(this.executionState, calledBy,
    	uParam, uType);
    effect.applyRoutine();
  }
  
  public void adaptJavaParametertoDirectionChange(final Operation uOperation, final Parameter uParam, final ParameterDirectionKind oldDirection, final ParameterDirectionKind newDirection) {
    mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine effect = new mir.routines.umlToJavaMethod.AdaptJavaParametertoDirectionChangeRoutine(this.executionState, calledBy,
    	uOperation, uParam, oldDirection, newDirection);
    effect.applyRoutine();
  }
}
