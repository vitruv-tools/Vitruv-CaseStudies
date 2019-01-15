package mir.routines.pcmInterfaceReactions;

import java.io.IOException;
import mir.routines.pcmInterfaceReactions.RoutinesFacade;
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
    
    public EObject getElement1(final OperationInterface pcmInterface, final Interface umlInterface) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final OperationInterface pcmInterface, final Interface umlInterface) {
      return umlInterface;
    }
    
    public EObject getElement3(final OperationInterface pcmInterface, final Interface umlInterface) {
      return umlInterface;
    }
    
    public String getTag1(final OperationInterface pcmInterface, final Interface umlInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public DeleteCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.DeleteCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;
  }
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmInterface), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    removeCorrespondenceBetween(userExecution.getElement1(pcmInterface, umlInterface), userExecution.getElement2(pcmInterface, umlInterface), userExecution.getTag1(pcmInterface, umlInterface));
    
    deleteObject(userExecution.getElement3(pcmInterface, umlInterface));
    
    postprocessElements();
    
    return true;
  }
}
