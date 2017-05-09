package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveInterfaceRealizationForInterfaceRealizationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveInterfaceRealizationForInterfaceRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization compIFRealization, final InterfaceRealization classIFRealization) {
      return classIFRealization;
    }
    
    public EObject getCorrepondenceSourceClassIFRealization(final InterfaceRealization compIFRealization) {
      return compIFRealization;
    }
  }
  
  public RemoveInterfaceRealizationForInterfaceRealizationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization compIFRealization) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RemoveInterfaceRealizationForInterfaceRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compIFRealization = compIFRealization;
  }
  
  private InterfaceRealization compIFRealization;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveInterfaceRealizationForInterfaceRealizationRoutine with input:");
    getLogger().debug("   InterfaceRealization: " + this.compIFRealization);
    
    InterfaceRealization classIFRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassIFRealization(compIFRealization), // correspondence source supplier
    	InterfaceRealization.class,
    	(InterfaceRealization _element) -> true, // correspondence precondition checker
    	null);
    if (classIFRealization == null) {
    	return;
    }
    registerObjectUnderModification(classIFRealization);
    deleteObject(userExecution.getElement1(compIFRealization, classIFRealization));
    
    postprocessElements();
  }
}
