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
public class RenameJavaMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOp, final Method jMeth) {
      return jMeth;
    }
    
    public void update0Element(final Operation umlOp, final Method jMeth) {
      String _name = umlOp.getName();
      jMeth.setName(_name);
    }
    
    public EObject getCorrepondenceSourceJMeth(final Operation umlOp) {
      return umlOp;
    }
  }
  
  public RenameJavaMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.RenameJavaMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlOp = umlOp;
  }
  
  private Operation umlOp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaMethodRoutine with input:");
    getLogger().debug("   Operation: " + this.umlOp);
    
    Method jMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMeth(umlOp), // correspondence source supplier
    	Method.class,
    	(Method _element) -> true, // correspondence precondition checker
    	null);
    if (jMeth == null) {
    	return;
    }
    initializeRetrieveElementState(jMeth);
    // val updatedElement userExecution.getElement1(umlOp, jMeth);
    userExecution.update0Element(umlOp, jMeth);
    
    postprocessElementStates();
  }
}
