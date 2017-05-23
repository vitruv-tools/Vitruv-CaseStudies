package mir.routines.javaToUmlmethod;

import java.io.IOException;
import mir.routines.javaToUmlmethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCustomType(final Method jMeth, final OrdinaryParameter jParam, final Operation uOperation) {
      Classifier _classifierFromTypeReference = JavaUtil.getClassifierFromTypeReference(jParam.getTypeReference());
      return _classifierFromTypeReference;
    }
    
    public EObject getElement1(final Method jMeth, final OrdinaryParameter jParam, final Operation uOperation, final org.eclipse.uml2.uml.Class customType, final Parameter uParam) {
      return uParam;
    }
    
    public void update0Element(final Method jMeth, final OrdinaryParameter jParam, final Operation uOperation, final org.eclipse.uml2.uml.Class customType, final Parameter uParam) {
      EList<Parameter> _ownedParameters = uOperation.getOwnedParameters();
      _ownedParameters.add(uParam);
    }
    
    public void updateUParamElement(final Method jMeth, final OrdinaryParameter jParam, final Operation uOperation, final org.eclipse.uml2.uml.Class customType, final Parameter uParam) {
      uParam.setName(jParam.getName());
    }
    
    public EObject getElement2(final Method jMeth, final OrdinaryParameter jParam, final Operation uOperation, final org.eclipse.uml2.uml.Class customType, final Parameter uParam) {
      return jParam;
    }
    
    public EObject getElement3(final Method jMeth, final OrdinaryParameter jParam, final Operation uOperation, final org.eclipse.uml2.uml.Class customType, final Parameter uParam) {
      return uOperation;
    }
    
    public EObject getCorrepondenceSourceUOperation(final Method jMeth, final OrdinaryParameter jParam) {
      return jMeth;
    }
  }
  
  public CreateUmlParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMeth, final OrdinaryParameter jParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlmethod.CreateUmlParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlmethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jParam = jParam;
  }
  
  private Method jMeth;
  
  private OrdinaryParameter jParam;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlParameterRoutine with input:");
    getLogger().debug("   Method: " + this.jMeth);
    getLogger().debug("   OrdinaryParameter: " + this.jParam);
    
    Operation uOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUOperation(jMeth, jParam), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uOperation == null) {
    	return;
    }
    registerObjectUnderModification(uOperation);
    org.eclipse.uml2.uml.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(jMeth, jParam, uOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customType);
    Parameter uParam = UMLFactoryImpl.eINSTANCE.createParameter();
    userExecution.updateUParamElement(jMeth, jParam, uOperation, customType, uParam);
    
    addCorrespondenceBetween(userExecution.getElement1(jMeth, jParam, uOperation, customType, uParam), userExecution.getElement2(jMeth, jParam, uOperation, customType, uParam), "");
    
    // val updatedElement userExecution.getElement3(jMeth, jParam, uOperation, customType, uParam);
    userExecution.update0Element(jMeth, jParam, uOperation, customType, uParam);
    
    postprocessElements();
  }
}