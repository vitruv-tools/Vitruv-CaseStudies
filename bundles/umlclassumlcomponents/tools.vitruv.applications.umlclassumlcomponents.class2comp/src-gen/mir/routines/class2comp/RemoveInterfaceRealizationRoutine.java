package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveInterfaceRealizationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveInterfaceRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization classIFRealization, final InterfaceRealization compIFRealization) {
      return compIFRealization;
    }
    
    public EObject getCorrepondenceSourceCompIFRealization(final InterfaceRealization classIFRealization) {
      return classIFRealization;
    }
  }
  
  public RemoveInterfaceRealizationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization classIFRealization) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RemoveInterfaceRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classIFRealization = classIFRealization;
  }
  
  private InterfaceRealization classIFRealization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveInterfaceRealizationRoutine with input:");
    getLogger().debug("   classIFRealization: " + this.classIFRealization);
    
    org.eclipse.uml2.uml.InterfaceRealization compIFRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompIFRealization(classIFRealization), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compIFRealization == null) {
    	return false;
    }
    registerObjectUnderModification(compIFRealization);
    deleteObject(userExecution.getElement1(classIFRealization, compIFRealization));
    
    postprocessElements();
    
    return true;
  }
}
