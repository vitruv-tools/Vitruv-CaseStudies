package mir.routines.pcmParameterReactions;

import java.io.IOException;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeDirectionOfCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private ChangeDirectionOfCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      return umlParam;
    }
    
    public void update0Element(final Parameter pcmParam, final org.eclipse.uml2.uml.Parameter umlParam) {
      umlParam.setDirection(PcmUmlClassHelper.getMatchingParameterDirection(pcmParam.getModifier__Parameter()));
    }
    
    public String getRetrieveTag1(final Parameter pcmParam) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlParam(final Parameter pcmParam) {
      return pcmParam;
    }
  }
  
  public ChangeDirectionOfCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.ChangeDirectionOfCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;
  }
  
  private Parameter pcmParam;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeDirectionOfCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    
    org.eclipse.uml2.uml.Parameter umlParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParam(pcmParam), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam), 
    	false // asserted
    	);
    if (umlParam == null) {
    	return false;
    }
    registerObjectUnderModification(umlParam);
    // val updatedElement userExecution.getElement1(pcmParam, umlParam);
    userExecution.update0Element(pcmParam, umlParam);
    
    postprocessElements();
    
    return true;
  }
}
