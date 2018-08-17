package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AttemptAddingCorrespondenceForExistingOperationCandidateRoutine extends AbstractRepairRoutineRealization {
  private AttemptAddingCorrespondenceForExistingOperationCandidateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate, final Interface umlInterface) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSource1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate, final Interface umlInterface) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSource2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate, final Interface umlInterface) {
      return umlOperationCandidate;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate, final Interface umlInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getElement2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate, final Interface umlInterface) {
      return umlOperationCandidate;
    }
    
    public String getRetrieveTag3(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate, final Interface umlInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getTag1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate, final Interface umlInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
  }
  
  public AttemptAddingCorrespondenceForExistingOperationCandidateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Operation umlOperationCandidate) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.AttemptAddingCorrespondenceForExistingOperationCandidateRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmInterface = pcmInterface;this.umlOperationCandidate = umlOperationCandidate;
  }
  
  private OperationSignature pcmSignature;
  
  private OperationInterface pcmInterface;
  
  private Operation umlOperationCandidate;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AttemptAddingCorrespondenceForExistingOperationCandidateRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   umlOperationCandidate: " + this.umlOperationCandidate);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmSignature, pcmInterface, umlOperationCandidate), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, pcmInterface, umlOperationCandidate), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSignature, pcmInterface, umlOperationCandidate, umlInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSignature, pcmInterface, umlOperationCandidate, umlInterface)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(pcmSignature, pcmInterface, umlOperationCandidate, umlInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(pcmSignature, pcmInterface, umlOperationCandidate, umlInterface)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmSignature, pcmInterface, umlOperationCandidate, umlInterface), userExecution.getElement2(pcmSignature, pcmInterface, umlOperationCandidate, umlInterface), userExecution.getTag1(pcmSignature, pcmInterface, umlOperationCandidate, umlInterface));
    
    postprocessElements();
    
    return true;
  }
}
