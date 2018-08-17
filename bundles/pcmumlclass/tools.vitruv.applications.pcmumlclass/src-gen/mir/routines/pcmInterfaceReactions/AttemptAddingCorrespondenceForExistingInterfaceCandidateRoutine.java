package mir.routines.pcmInterfaceReactions;

import java.io.IOException;
import mir.routines.pcmInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine extends AbstractRepairRoutineRealization {
  private AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface umlInterfaceCandidate) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSource1(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface umlInterfaceCandidate) {
      return umlInterfaceCandidate;
    }
    
    public String getRetrieveTag1(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface umlInterfaceCandidate) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface umlInterfaceCandidate) {
      return umlInterfaceCandidate;
    }
    
    public String getTag1(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface umlInterfaceCandidate) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final Repository pcmRepository, final Interface umlInterfaceCandidate) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.pcmRepository = pcmRepository;this.umlInterfaceCandidate = umlInterfaceCandidate;
  }
  
  private OperationInterface pcmInterface;
  
  private Repository pcmRepository;
  
  private Interface umlInterfaceCandidate;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AttemptAddingCorrespondenceForExistingInterfaceCandidateRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    getLogger().debug("   umlInterfaceCandidate: " + this.umlInterfaceCandidate);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmInterface, pcmRepository, umlInterfaceCandidate), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmInterface, pcmRepository, umlInterfaceCandidate)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmInterface, pcmRepository, umlInterfaceCandidate), userExecution.getElement2(pcmInterface, pcmRepository, umlInterfaceCandidate), userExecution.getTag1(pcmInterface, pcmRepository, umlInterfaceCandidate));
    
    postprocessElements();
    
    return true;
  }
}
