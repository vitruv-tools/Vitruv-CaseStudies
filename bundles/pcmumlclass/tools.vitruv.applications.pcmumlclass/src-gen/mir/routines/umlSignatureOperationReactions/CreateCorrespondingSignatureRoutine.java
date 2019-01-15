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
public class CreateCorrespondingSignatureRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSource1(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface) {
      return umlOperation;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Operation umlOperation, final Interface umlInterface) {
      return umlInterface;
    }
    
    public String getRetrieveTag1(final Operation umlOperation, final Interface umlInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getElement2(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface, final OperationSignature pcmSignature) {
      return umlOperation;
    }
    
    public void updatePcmSignatureElement(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface, final OperationSignature pcmSignature) {
      pcmSignature.setEntityName(umlOperation.getName());
      EList<OperationSignature> _signatures__OperationInterface = pcmInterface.getSignatures__OperationInterface();
      _signatures__OperationInterface.add(pcmSignature);
    }
    
    public String getTag1(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
  }
  
  public CreateCorrespondingSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Interface umlInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlSignatureOperationReactions.CreateCorrespondingSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlInterface = umlInterface;
  }
  
  private Operation umlOperation;
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingSignatureRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlOperation, umlInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlOperation, umlInterface), 
    	true // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlOperation, umlInterface, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlOperation, umlInterface, pcmInterface)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationSignature();
    notifyObjectCreated(pcmSignature);
    userExecution.updatePcmSignatureElement(umlOperation, umlInterface, pcmInterface, pcmSignature);
    
    addCorrespondenceBetween(userExecution.getElement1(umlOperation, umlInterface, pcmInterface, pcmSignature), userExecution.getElement2(umlOperation, umlInterface, pcmInterface, pcmSignature), userExecution.getTag1(umlOperation, umlInterface, pcmInterface, pcmSignature));
    
    postprocessElements();
    
    return true;
  }
}
