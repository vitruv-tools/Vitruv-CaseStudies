package mir.routines.umlRegularParameterReactions;

import java.io.IOException;
import mir.routines.umlRegularParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
    
    public EObject getElement1(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlParameter;
    }
    
    public EObject getCorrepondenceSource2(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getElement2(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return umlParameter;
    }
    
    public String getRetrieveTag3(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public String getTag1(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Parameter umlParameter, final Operation umlOperation) {
      return umlOperation;
    }
    
    public void updatePcmParameterElement(final Parameter umlParameter, final Operation umlOperation, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      pcmParameter.setParameterName(umlParameter.getName());
    }
  }
  
  public CreateCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final Operation umlOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.CreateCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.umlOperation = umlOperation;
  }
  
  private Parameter umlParameter;
  
  private Operation umlOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   umlOperation: " + this.umlOperation);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlParameter, umlOperation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, umlOperation), 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParameter, umlOperation, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParameter, umlOperation, pcmSignature)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlParameter, umlOperation, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlParameter, umlOperation, pcmSignature)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(pcmParameter);
    userExecution.updatePcmParameterElement(umlParameter, umlOperation, pcmSignature, pcmParameter);
    
    addCorrespondenceBetween(userExecution.getElement1(umlParameter, umlOperation, pcmSignature, pcmParameter), userExecution.getElement2(umlParameter, umlOperation, pcmSignature, pcmParameter), userExecution.getTag1(umlParameter, umlOperation, pcmSignature, pcmParameter));
    
    postprocessElements();
    
    return true;
  }
}
