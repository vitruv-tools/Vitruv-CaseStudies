package mir.routines.umlInterfaceReactions;

import java.io.IOException;
import mir.routines.umlInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForExistingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForExistingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSource1(final Interface umlInterface, final OperationInterface pcmInterface) {
      return umlInterface;
    }
    
    public EObject getCorrepondenceSource2(final Interface umlInterface, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final Interface umlInterface, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final Interface umlInterface, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final Interface umlInterface, final OperationInterface pcmInterface) {
      return umlInterface;
    }
    
    public String getTag1(final Interface umlInterface, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public AddCorrespondenceForExistingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceReactions.AddCorrespondenceForExistingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.pcmInterface = pcmInterface;
  }
  
  private Interface umlInterface;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForExistingInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlInterface, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface, pcmInterface)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlInterface, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlInterface, pcmInterface)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlInterface, pcmInterface), userExecution.getElement2(umlInterface, pcmInterface), userExecution.getTag1(umlInterface, pcmInterface));
    
    postprocessElements();
    
    return true;
  }
}
