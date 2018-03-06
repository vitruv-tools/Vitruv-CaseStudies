package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteMethodForOperationSignatureRoutine extends AbstractRepairRoutineRealization {
  private DeleteMethodForOperationSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature operationSignature, final InterfaceMethod interfaceMethod) {
      return interfaceMethod;
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final OperationSignature operationSignature) {
      return operationSignature;
    }
  }
  
  public DeleteMethodForOperationSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.DeleteMethodForOperationSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature = operationSignature;
  }
  
  private OperationSignature operationSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteMethodForOperationSignatureRoutine with input:");
    getLogger().debug("   operationSignature: " + this.operationSignature);
    
    org.emftext.language.java.members.InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(operationSignature), // correspondence source supplier
    	org.emftext.language.java.members.InterfaceMethod.class,
    	(org.emftext.language.java.members.InterfaceMethod _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (interfaceMethod == null) {
    	return false;
    }
    registerObjectUnderModification(interfaceMethod);
    deleteObject(userExecution.getElement1(operationSignature, interfaceMethod));
    
    postprocessElements();
    
    return true;
  }
}
