package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlOperationToClassRoutine extends AbstractRepairRoutineRealization {
  private AddUmlOperationToClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
      return uClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
      EList<Operation> _ownedOperations = uClass.getOwnedOperations();
      _ownedOperations.add(uOperation);
    }
  }
  
  public AddUmlOperationToClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class uClass, final Operation uOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.AddUmlOperationToClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.uClass = uClass;this.uOperation = uOperation;
  }
  
  private org.eclipse.uml2.uml.Class uClass;
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlOperationToClassRoutine with input:");
    getLogger().debug("   uClass: " + this.uClass);
    getLogger().debug("   uOperation: " + this.uOperation);
    
    // val updatedElement userExecution.getElement1(uClass, uOperation);
    userExecution.update0Element(uClass, uOperation);
    
    postprocessElements();
    
    return true;
  }
}
