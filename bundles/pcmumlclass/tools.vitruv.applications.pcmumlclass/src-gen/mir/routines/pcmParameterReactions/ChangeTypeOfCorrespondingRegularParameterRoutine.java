package mir.routines.pcmParameterReactions;

import java.io.IOException;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Parameter pcmParam, final DataType pcmDataType) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlParam(final Parameter pcmParam, final DataType pcmDataType) {
      return pcmParam;
    }
    
    public void callRoutine1(final Parameter pcmParam, final DataType pcmDataType, final org.eclipse.uml2.uml.Parameter umlParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.pcmDataTypePropagationReactions.setUmlParameterType(pcmDataType, umlParam);
    }
  }
  
  public ChangeTypeOfCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam, final DataType pcmDataType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.ChangeTypeOfCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;this.pcmDataType = pcmDataType;
  }
  
  private Parameter pcmParam;
  
  private DataType pcmDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    
    org.eclipse.uml2.uml.Parameter umlParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParam(pcmParam, pcmDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam, pcmDataType), 
    	false // asserted
    	);
    if (umlParam == null) {
    	return false;
    }
    registerObjectUnderModification(umlParam);
    userExecution.callRoutine1(pcmParam, pcmDataType, umlParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
