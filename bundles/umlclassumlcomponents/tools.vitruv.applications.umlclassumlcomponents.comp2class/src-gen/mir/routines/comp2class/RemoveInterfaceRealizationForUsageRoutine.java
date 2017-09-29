package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Usage;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveInterfaceRealizationForUsageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveInterfaceRealizationForUsageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Usage compUsage, final InterfaceRealization classIFRealization) {
      return classIFRealization;
    }
    
    public EObject getCorrepondenceSourceClassIFRealization(final Usage compUsage) {
      return compUsage;
    }
  }
  
  public RemoveInterfaceRealizationForUsageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Usage compUsage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RemoveInterfaceRealizationForUsageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compUsage = compUsage;
  }
  
  private Usage compUsage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveInterfaceRealizationForUsageRoutine with input:");
    getLogger().debug("   compUsage: " + this.compUsage);
    
    org.eclipse.uml2.uml.InterfaceRealization classIFRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassIFRealization(compUsage), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (classIFRealization == null) {
    	return false;
    }
    registerObjectUnderModification(classIFRealization);
    deleteObject(userExecution.getElement1(compUsage, classIFRealization));
    
    postprocessElements();
    
    return true;
  }
}
