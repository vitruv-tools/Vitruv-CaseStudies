package mir.routines.umlRegularParameterReactions;

import java.io.IOException;
import mir.routines.umlRegularParameterReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      return pcmSignature;
    }
    
    public void update0Element(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParam) {
      EList<org.palladiosimulator.pcm.repository.Parameter> _parameters__OperationSignature = pcmSignature.getParameters__OperationSignature();
      _parameters__OperationSignature.add(pcmParam);
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlParam;
    }
    
    public EObject getCorrepondenceSourcePcmParam(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlParam;
    }
    
    public String getRetrieveTag1(final Parameter umlParam, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public String getRetrieveTag3(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Parameter umlParam, final Operation umlOperation) {
      return umlOperation;
    }
  }
  
  public MoveCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParam, final Operation umlOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.MoveCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParam = umlParam;this.umlOperation = umlOperation;
  }
  
  private Parameter umlParam;
  
  private Operation umlOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   umlParam: " + this.umlParam);
    getLogger().debug("   umlOperation: " + this.umlOperation);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlParam, umlOperation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParam, umlOperation), 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParam, umlOperation, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParam, umlOperation, pcmSignature)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.Parameter pcmParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParam(umlParam, umlOperation, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlParam, umlOperation, pcmSignature), 
    	false // asserted
    	);
    if (pcmParam == null) {
    	return false;
    }
    registerObjectUnderModification(pcmParam);
    // val updatedElement userExecution.getElement1(umlParam, umlOperation, pcmSignature, pcmParam);
    userExecution.update0Element(umlParam, umlOperation, pcmSignature, pcmParam);
    
    postprocessElements();
    
    return true;
  }
}
