package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
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
public class MoveCorrespondingOperationRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Operation umlOperation) {
      return umlInterface;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Operation umlOperation) {
      EList<Operation> _ownedOperations = umlInterface.getOwnedOperations();
      _ownedOperations.add(umlOperation);
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface) {
      return pcmSignature;
    }
  }
  
  public MoveCorrespondingOperationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.MoveCorrespondingOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmInterface = pcmInterface;
  }
  
  private OperationSignature pcmSignature;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingOperationRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmSignature, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, pcmInterface), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature, pcmInterface, umlInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSignature, pcmInterface, umlInterface), 
    	false // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    // val updatedElement userExecution.getElement1(pcmSignature, pcmInterface, umlInterface, umlOperation);
    userExecution.update0Element(pcmSignature, pcmInterface, umlInterface, umlOperation);
    
    postprocessElements();
    
    return true;
  }
}
