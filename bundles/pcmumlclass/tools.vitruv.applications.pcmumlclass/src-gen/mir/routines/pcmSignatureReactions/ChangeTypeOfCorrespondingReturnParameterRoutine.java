package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingReturnParameterRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingReturnParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final DataType pcmDataType) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlParam(final OperationSignature pcmSignature, final DataType pcmDataType) {
      return pcmSignature;
    }
    
    public void callRoutine1(final OperationSignature pcmSignature, final DataType pcmDataType, final Parameter umlParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.pcmDataTypePropagationReactions.setUmlParameterType(pcmDataType, umlParam);
    }
  }
  
  public ChangeTypeOfCorrespondingReturnParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final DataType pcmDataType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.ChangeTypeOfCorrespondingReturnParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmDataType = pcmDataType;
  }
  
  private OperationSignature pcmSignature;
  
  private DataType pcmDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingReturnParameterRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    
    org.eclipse.uml2.uml.Parameter umlParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParam(pcmSignature, pcmDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, pcmDataType), 
    	false // asserted
    	);
    if (umlParam == null) {
    	return false;
    }
    registerObjectUnderModification(umlParam);
    userExecution.callRoutine1(pcmSignature, pcmDataType, umlParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
