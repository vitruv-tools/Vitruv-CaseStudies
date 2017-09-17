package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UnsetUmlOperationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UnsetUmlOperationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final Operation umlOperation) {
      return umlOperation;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final Operation umlOperation) {
      umlOperation.setType(null);
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature) {
      return pcmSignature;
    }
  }
  
  public UnsetUmlOperationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.UnsetUmlOperationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmSignature = pcmSignature;
  }
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UnsetUmlOperationTypeRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    // val updatedElement userExecution.getElement1(pcmSignature, umlOperation);
    userExecution.update0Element(pcmSignature, umlOperation);
    
    postprocessElements();
    
    return true;
  }
}
