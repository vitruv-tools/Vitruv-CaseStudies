package mir.routines.umlSignatureOperationReactions;

import java.io.IOException;
import mir.routines.umlSignatureOperationReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
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
public class RemoveCorrespondingSignatureRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondingSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final Interface umlInterface, final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public void update0Element(final Operation umlOperation, final Interface umlInterface, final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      EList<OperationSignature> _signatures__OperationInterface = pcmInterface.getSignatures__OperationInterface();
      _signatures__OperationInterface.remove(pcmSignature);
    }
    
    public String getRetrieveTag1(final Operation umlOperation, final Interface umlInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Operation umlOperation, final Interface umlInterface, final OperationSignature pcmSignature) {
      return umlInterface;
    }
    
    public String getRetrieveTag2(final Operation umlOperation, final Interface umlInterface, final OperationSignature pcmSignature) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation, final Interface umlInterface) {
      return umlOperation;
    }
  }
  
  public RemoveCorrespondingSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Interface umlInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlSignatureOperationReactions.RemoveCorrespondingSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlInterface = umlInterface;
  }
  
  private Operation umlOperation;
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingSignatureRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlOperation, umlInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlOperation, umlInterface), 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlOperation, umlInterface, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlOperation, umlInterface, pcmSignature), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    // val updatedElement userExecution.getElement1(umlOperation, umlInterface, pcmSignature, pcmInterface);
    userExecution.update0Element(umlOperation, umlInterface, pcmSignature, pcmInterface);
    
    postprocessElements();
    
    return true;
  }
}
