package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UnsetInterfaceOperationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UnsetInterfaceOperationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public void update0Element(final Operation umlOperation, final OperationSignature pcmSignature) {
      pcmSignature.setReturnType__OperationSignature(null);
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation) {
      return umlOperation;
    }
  }
  
  public UnsetInterfaceOperationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.UnsetInterfaceOperationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlOperation = umlOperation;
  }
  
  private Operation umlOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UnsetInterfaceOperationTypeRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlOperation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    // val updatedElement userExecution.getElement1(umlOperation, pcmSignature);
    userExecution.update0Element(umlOperation, pcmSignature);
    
    postprocessElements();
    
    return true;
  }
}
