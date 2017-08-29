package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlOperationToEnumRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlOperationToEnumRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Enumeration uEnum, final Operation uOperation) {
      return uEnum;
    }
    
    public void update0Element(final Enumeration uEnum, final Operation uOperation) {
      EList<Operation> _ownedOperations = uEnum.getOwnedOperations();
      _ownedOperations.add(uOperation);
    }
  }
  
  public AddUmlOperationToEnumRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Enumeration uEnum, final Operation uOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.AddUmlOperationToEnumRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.uEnum = uEnum;this.uOperation = uOperation;
  }
  
  private Enumeration uEnum;
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlOperationToEnumRoutine with input:");
    getLogger().debug("   uEnum: " + this.uEnum);
    getLogger().debug("   uOperation: " + this.uOperation);
    
    // val updatedElement userExecution.getElement1(uEnum, uOperation);
    userExecution.update0Element(uEnum, uOperation);
    
    postprocessElements();
    
    return true;
  }
}
