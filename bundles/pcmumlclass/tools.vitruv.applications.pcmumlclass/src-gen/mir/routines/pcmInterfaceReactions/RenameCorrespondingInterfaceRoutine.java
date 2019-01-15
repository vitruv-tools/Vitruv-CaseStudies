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
public class RenameCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RenameCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface pcmInterface, final String newName, final Interface umlInterface) {
      return umlInterface;
    }
    
    public void update0Element(final OperationInterface pcmInterface, final String newName, final Interface umlInterface) {
      umlInterface.setName(newName);
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationInterface pcmInterface, final String newName) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationInterface pcmInterface, final String newName) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public RenameCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.RenameCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.newName = newName;
  }
  
  private OperationInterface pcmInterface;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmInterface, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmInterface, newName), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    // val updatedElement userExecution.getElement1(pcmInterface, newName, umlInterface);
    userExecution.update0Element(pcmInterface, newName, umlInterface);
    
    postprocessElements();
    
    return true;
  }
}
