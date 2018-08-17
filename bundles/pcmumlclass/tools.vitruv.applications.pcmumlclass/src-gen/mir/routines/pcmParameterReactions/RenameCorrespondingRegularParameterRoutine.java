package mir.routines.pcmParameterReactions;

import java.io.IOException;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private RenameCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter pcmParam, final String newName, final org.eclipse.uml2.uml.Parameter umlParam) {
      return umlParam;
    }
    
    public void update0Element(final Parameter pcmParam, final String newName, final org.eclipse.uml2.uml.Parameter umlParam) {
      umlParam.setName(newName);
    }
    
    public String getRetrieveTag1(final Parameter pcmParam, final String newName) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlParam(final Parameter pcmParam, final String newName) {
      return pcmParam;
    }
  }
  
  public RenameCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.RenameCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;this.newName = newName;
  }
  
  private Parameter pcmParam;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Parameter umlParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParam(pcmParam, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam, newName), 
    	false // asserted
    	);
    if (umlParam == null) {
    	return false;
    }
    registerObjectUnderModification(umlParam);
    // val updatedElement userExecution.getElement1(pcmParam, newName, umlParam);
    userExecution.update0Element(pcmParam, newName, umlParam);
    
    postprocessElements();
    
    return true;
  }
}
