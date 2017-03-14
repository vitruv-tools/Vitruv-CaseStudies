package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameUmlMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameUmlMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Method jMeth, final Operation uMeth) {
      return uMeth;
    }
    
    public void update0Element(final Method jMeth, final Operation uMeth) {
      String _name = jMeth.getName();
      uMeth.setName(_name);
    }
    
    public EObject getCorrepondenceSourceUMeth(final Method jMeth) {
      return jMeth;
    }
  }
  
  public RenameUmlMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMeth) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.RenameUmlMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;
  }
  
  private Method jMeth;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameUmlMethodRoutine with input:");
    getLogger().debug("   Method: " + this.jMeth);
    
    Operation uMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMeth(jMeth), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uMeth == null) {
    	return;
    }
    initializeRetrieveElementState(uMeth);
    // val updatedElement userExecution.getElement1(jMeth, uMeth);
    userExecution.update0Element(jMeth, uMeth);
    
    postprocessElementStates();
  }
}
