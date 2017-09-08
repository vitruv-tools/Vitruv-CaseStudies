package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlMethodFinalRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlMethodFinalRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Method jMethod, final Boolean isFinal, final Operation uOperation) {
      return uOperation;
    }
    
    public void update0Element(final Method jMethod, final Boolean isFinal, final Operation uOperation) {
      uOperation.setIsLeaf((isFinal).booleanValue());
    }
    
    public EObject getCorrepondenceSourceUOperation(final Method jMethod, final Boolean isFinal) {
      return jMethod;
    }
  }
  
  public SetUmlMethodFinalRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMethod, final Boolean isFinal) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.SetUmlMethodFinalRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jMethod = jMethod;this.isFinal = isFinal;
  }
  
  private Method jMethod;
  
  private Boolean isFinal;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlMethodFinalRoutine with input:");
    getLogger().debug("   jMethod: " + this.jMethod);
    getLogger().debug("   isFinal: " + this.isFinal);
    
    org.eclipse.uml2.uml.Operation uOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUOperation(jMethod, isFinal), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uOperation == null) {
    	return false;
    }
    registerObjectUnderModification(uOperation);
    // val updatedElement userExecution.getElement1(jMethod, isFinal, uOperation);
    userExecution.update0Element(jMethod, isFinal, uOperation);
    
    postprocessElements();
    
    return true;
  }
}
