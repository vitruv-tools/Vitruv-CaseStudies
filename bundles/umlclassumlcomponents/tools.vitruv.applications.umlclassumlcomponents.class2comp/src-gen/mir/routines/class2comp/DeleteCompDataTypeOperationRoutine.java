package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCompDataTypeOperationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteCompDataTypeOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation classOperation, final Operation compOperation) {
      return compOperation;
    }
    
    public String getRetrieveTag1(final Operation classOperation) {
      return SharedUtil.DATA_TYPE_PROPERTY;
    }
    
    public EObject getElement2(final Operation classOperation, final Operation compOperation) {
      return classOperation;
    }
    
    public EObject getElement3(final Operation classOperation, final Operation compOperation) {
      return compOperation;
    }
    
    public EObject getCorrepondenceSourceCompOperation(final Operation classOperation) {
      return classOperation;
    }
  }
  
  public DeleteCompDataTypeOperationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation classOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteCompDataTypeOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classOperation = classOperation;
  }
  
  private Operation classOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCompDataTypeOperationRoutine with input:");
    getLogger().debug("   classOperation: " + this.classOperation);
    
    org.eclipse.uml2.uml.Operation compOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompOperation(classOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(classOperation), 
    	false // asserted
    	);
    if (compOperation == null) {
    	return false;
    }
    registerObjectUnderModification(compOperation);
    deleteObject(userExecution.getElement1(classOperation, compOperation));
    
    removeCorrespondenceBetween(userExecution.getElement2(classOperation, compOperation), userExecution.getElement3(classOperation, compOperation), "");
    
    postprocessElements();
    
    return true;
  }
}
