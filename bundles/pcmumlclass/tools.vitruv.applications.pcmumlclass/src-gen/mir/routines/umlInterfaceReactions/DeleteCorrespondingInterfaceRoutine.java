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
public class DeleteCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Interface umlInterface) {
      return umlInterface;
    }
    
    public String getRetrieveTag1(final Interface umlInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final Interface umlInterface, final OperationInterface pcmInterface) {
      return umlInterface;
    }
    
    public EObject getElement3(final Interface umlInterface, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public String getTag1(final Interface umlInterface, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public DeleteCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceReactions.DeleteCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;
  }
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    removeCorrespondenceBetween(userExecution.getElement1(umlInterface, pcmInterface), userExecution.getElement2(umlInterface, pcmInterface), userExecution.getTag1(umlInterface, pcmInterface));
    
    deleteObject(userExecution.getElement3(umlInterface, pcmInterface));
    
    postprocessElements();
    
    return true;
  }
}
