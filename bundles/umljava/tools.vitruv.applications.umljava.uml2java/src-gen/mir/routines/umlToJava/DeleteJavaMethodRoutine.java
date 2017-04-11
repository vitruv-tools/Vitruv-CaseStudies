package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
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
    
    public EObject getElement1(final Operation umlOp, final Method jMeth) {
      return jMeth;
    }
    
    public EObject getCorrepondenceSourceJMeth(final Operation umlOp) {
      return umlOp;
    }
  }
  
  public DeleteJavaMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.DeleteJavaMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlOp = umlOp;
  }
  
  private Operation umlOp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaMethodRoutine with input:");
    getLogger().debug("   Operation: " + this.umlOp);
    
    Method jMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMeth(umlOp), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	null);
    if (jMeth == null) {
    	return;
    }
    registerObjectUnderModification(jMeth);
    deleteObject(userExecution.getElement1(umlOp, jMeth));
    
    postprocessElements();
  }
}
