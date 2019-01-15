package mir.routines.pcmParameterReactions;

import java.io.IOException;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParam) {
      return pcmParam;
    }
    
    public EObject getCorrepondenceSource1(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation) {
      return pcmParam;
    }
    
    public void updateUmlParamElement(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParam) {
      umlParam.setName(pcmParam.getParameterName());
    }
    
    public String getRetrieveTag1(final Parameter pcmParam, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getElement2(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParam) {
      return umlParam;
    }
    
    public String getTag1(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public void callRoutine2(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeTypeOfCorrespondingRegularParameter(pcmParam, pcmParam.getDataType__Parameter());
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final Parameter pcmParam, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public void callRoutine1(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeDirectionOfCorrespondingRegularParameter(pcmParam);
    }
  }
  
  public CreateCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.CreateCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;this.pcmSignature = pcmSignature;
  }
  
  private Parameter pcmParam;
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmParam, pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam, pcmSignature), 
    	false // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmParam, pcmSignature, umlOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmParam, pcmSignature, umlOperation)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Parameter umlParam = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(umlParam);
    userExecution.updateUmlParamElement(pcmParam, pcmSignature, umlOperation, umlParam);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmParam, pcmSignature, umlOperation, umlParam), userExecution.getElement2(pcmParam, pcmSignature, umlOperation, umlParam), userExecution.getTag1(pcmParam, pcmSignature, umlOperation, umlParam));
    
    userExecution.callRoutine1(pcmParam, pcmSignature, umlOperation, umlParam, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmParam, pcmSignature, umlOperation, umlParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
