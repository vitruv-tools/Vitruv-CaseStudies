package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaMethodReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaMethodReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJParam(final Parameter uParam, final Integer changedToVoid, final Method javaMethod) {
      return uParam;
    }
    
    public EObject getElement1(final Parameter uParam, final Integer changedToVoid, final Method javaMethod, final OrdinaryParameter jParam, final org.emftext.language.java.classifiers.Class returnType) {
      return javaMethod;
    }
    
    public void update0Element(final Parameter uParam, final Integer changedToVoid, final Method javaMethod, final OrdinaryParameter jParam, final org.emftext.language.java.classifiers.Class returnType) {
      if (((changedToVoid).intValue() == 1)) {
        org.emftext.language.java.types.Void _createVoid = TypesFactory.eINSTANCE.createVoid();
        javaMethod.setTypeReference(_createVoid);
      } else {
        Type _type = uParam.getType();
        TypeReference _createTypeReference = UmlToJavaHelper.createTypeReference(_type, returnType);
        javaMethod.setTypeReference(_createTypeReference);
      }
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Parameter uParam, final Integer changedToVoid) {
      Operation _operation = uParam.getOperation();
      return _operation;
    }
    
    public EObject getCorrepondenceSourceReturnType(final Parameter uParam, final Integer changedToVoid, final Method javaMethod, final OrdinaryParameter jParam) {
      Type _type = uParam.getType();
      return _type;
    }
  }
  
  public SetJavaMethodReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParam, final Integer changedToVoid) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.SetJavaMethodReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uParam = uParam;this.changedToVoid = changedToVoid;
  }
  
  private Parameter uParam;
  
  private Integer changedToVoid;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaMethodReturnTypeRoutine with input:");
    getLogger().debug("   Parameter: " + this.uParam);
    getLogger().debug("   Integer: " + this.changedToVoid);
    
    Method javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(uParam, changedToVoid), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	null);
    if (javaMethod == null) {
    	return;
    }
    initializeRetrieveElementState(javaMethod);
    OrdinaryParameter jParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJParam(uParam, changedToVoid, javaMethod), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (jParam == null) {
    	return;
    }
    initializeRetrieveElementState(jParam);
    org.emftext.language.java.classifiers.Class returnType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceReturnType(uParam, changedToVoid, javaMethod, jParam), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (returnType == null) {
    	return;
    }
    initializeRetrieveElementState(returnType);
    // val updatedElement userExecution.getElement1(uParam, changedToVoid, javaMethod, jParam, returnType);
    userExecution.update0Element(uParam, changedToVoid, javaMethod, jParam, returnType);
    
    postprocessElementStates();
  }
}
