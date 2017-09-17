package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation uOperation, final Method jMeth) {
      return jMeth;
    }
    
    public EObject getCorrepondenceSourceJMeth(final Operation uOperation) {
      return uOperation;
    }
  }
  
  public DeleteJavaMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.DeleteJavaMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uOperation = uOperation;
  }
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaMethodRoutine with input:");
    getLogger().debug("   uOperation: " + this.uOperation);
    
    org.emftext.language.java.members.Method jMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMeth(uOperation), // correspondence source supplier
    	org.emftext.language.java.members.Method.class,
    	(org.emftext.language.java.members.Method _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jMeth == null) {
    	return false;
    }
    registerObjectUnderModification(jMeth);
    deleteObject(userExecution.getElement1(uOperation, jMeth));
    
    postprocessElements();
    
    return true;
  }
}
