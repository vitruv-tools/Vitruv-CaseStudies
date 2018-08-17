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
public class RenameCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RenameCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final String newName, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public void update0Element(final Interface umlInterface, final String newName, final OperationInterface pcmInterface) {
      pcmInterface.setEntityName(umlInterface.getName());
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Interface umlInterface, final String newName) {
      return umlInterface;
    }
    
    public String getRetrieveTag1(final Interface umlInterface, final String newName) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public RenameCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceReactions.RenameCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.newName = newName;
  }
  
  private Interface umlInterface;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   newName: " + this.newName);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlInterface, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface, newName), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    // val updatedElement userExecution.getElement1(umlInterface, newName, pcmInterface);
    userExecution.update0Element(umlInterface, newName, pcmInterface);
    
    postprocessElements();
    
    return true;
  }
}
