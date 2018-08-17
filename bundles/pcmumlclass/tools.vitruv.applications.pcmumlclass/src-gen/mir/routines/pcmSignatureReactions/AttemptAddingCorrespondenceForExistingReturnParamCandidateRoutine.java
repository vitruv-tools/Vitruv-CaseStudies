package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine extends AbstractRepairRoutineRealization {
  private AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return umlReturnParamCandidate;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      umlReturnParamCandidate.setName(DefaultLiterals.RETURN_PARAM_NAME);
    }
    
    public EObject getCorrepondenceSource1(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSource2(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return umlReturnParamCandidate;
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getElement2(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return pcmSignature;
    }
    
    public String getRetrieveTag3(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getElement3(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return umlReturnParamCandidate;
    }
    
    public String getTag1(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate) {
      return pcmSignature;
    }
  }
  
  public AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final Parameter umlReturnParamCandidate) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.umlReturnParamCandidate = umlReturnParamCandidate;
  }
  
  private OperationSignature pcmSignature;
  
  private Parameter umlReturnParamCandidate;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AttemptAddingCorrespondenceForExistingReturnParamCandidateRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   umlReturnParamCandidate: " + this.umlReturnParamCandidate);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature, umlReturnParamCandidate), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, umlReturnParamCandidate), 
    	true // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSignature, umlReturnParamCandidate, umlOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSignature, umlReturnParamCandidate, umlOperation)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmSignature, umlReturnParamCandidate, umlOperation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(pcmSignature, umlReturnParamCandidate, umlOperation)
    ).isEmpty()) {
    	return false;
    }
    // val updatedElement userExecution.getElement1(pcmSignature, umlReturnParamCandidate, umlOperation);
    userExecution.update0Element(pcmSignature, umlReturnParamCandidate, umlOperation);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmSignature, umlReturnParamCandidate, umlOperation), userExecution.getElement3(pcmSignature, umlReturnParamCandidate, umlOperation), userExecution.getTag1(pcmSignature, umlReturnParamCandidate, umlOperation));
    
    postprocessElements();
    
    return true;
  }
}
