package mir.routines.umlRegularParameterReactions;

import java.io.IOException;
import mir.routines.umlRegularParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParam, final String newName, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      return pcmParam;
    }
    
    public void update0Element(final Parameter umlParam, final String newName, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      pcmParam.setParameterName(newName);
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParam, final String newName) {
      return umlParam;
    }
    
    public EObject getCorrepondenceSourcePcmParam(final Parameter umlParam, final String newName) {
      return umlParam;
    }
    
    public String getRetrieveTag1(final Parameter umlParam, final String newName) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public String getRetrieveTag2(final Parameter umlParam, final String newName) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
  }
  
  public ChangeNameOfCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParam, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.ChangeNameOfCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParam = umlParam;this.newName = newName;
  }
  
  private Parameter umlParam;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   umlParam: " + this.umlParam);
    getLogger().debug("   newName: " + this.newName);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParam, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParam, newName)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Parameter pcmParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParam(umlParam, newName), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParam, newName), 
    	false // asserted
    	);
    if (pcmParam == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParam);
    // val updatedElement userExecution.getElement1(umlParam, newName, pcmParam);
    userExecution.update0Element(umlParam, newName, pcmParam);
    
    postprocessElements();
    
    return true;
  }
}
