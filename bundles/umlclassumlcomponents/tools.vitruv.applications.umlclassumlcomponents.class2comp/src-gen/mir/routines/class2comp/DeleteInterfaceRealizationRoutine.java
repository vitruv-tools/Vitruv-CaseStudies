package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteInterfaceRealizationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteInterfaceRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization classInterface, final InterfaceRealization interfaceRealization) {
      return interfaceRealization;
    }
    
    public EObject getCorrepondenceSourceInterfaceRealization(final InterfaceRealization classInterface) {
      return classInterface;
    }
  }
  
  public DeleteInterfaceRealizationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization classInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteInterfaceRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classInterface = classInterface;
  }
  
  private InterfaceRealization classInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteInterfaceRealizationRoutine with input:");
    getLogger().debug("   InterfaceRealization: " + this.classInterface);
    
    InterfaceRealization interfaceRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceRealization(classInterface), // correspondence source supplier
    	InterfaceRealization.class,
    	(InterfaceRealization _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceRealization == null) {
    	return;
    }
    registerObjectUnderModification(interfaceRealization);
    deleteObject(userExecution.getElement1(classInterface, interfaceRealization));
    
    postprocessElements();
  }
}
