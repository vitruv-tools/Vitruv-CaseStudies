package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeReturnTypeOfMethodForOperationSignatureRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeReturnTypeOfMethodForOperationSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final OperationSignature operationSignature) {
      return operationSignature;
    }
    
    public void callRoutine1(final OperationSignature operationSignature, final InterfaceMethod interfaceMethod, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeInterfaceMethodReturnType(interfaceMethod, operationSignature.getReturnType__OperationSignature());
    }
  }
  
  public ChangeReturnTypeOfMethodForOperationSignatureRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.ChangeReturnTypeOfMethodForOperationSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.operationSignature = operationSignature;
  }
  
  private OperationSignature operationSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeReturnTypeOfMethodForOperationSignatureRoutine with input:");
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
    userExecution.callRoutine1(operationSignature, interfaceMethod, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
