package mir.routines.pcmParameterReactions;

import java.io.IOException;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
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
    
    public EObject getElement1(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return umlOperation;
    }
    
    public void update0Element(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParameter) {
      EList<org.eclipse.uml2.uml.Parameter> _ownedParameters = umlOperation.getOwnedParameters();
      _ownedParameters.add(umlParameter);
    }
    
    public String getRetrieveTag1(final Parameter pcmParam, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final Parameter pcmParam, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSourceUmlParameter(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation) {
      return pcmParam;
    }
  }
  
  public MoveCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.MoveCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;this.pcmSignature = pcmSignature;
  }
  
  private Parameter pcmParam;
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmParam, pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam, pcmSignature), 
    	true // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    org.eclipse.uml2.uml.Parameter umlParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParameter(pcmParam, pcmSignature, umlOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmParam, pcmSignature, umlOperation), 
    	true // asserted
    	);
    if (umlParameter == null) {
    	return false;
    }
    registerObjectUnderModification(umlParameter);
    // val updatedElement userExecution.getElement1(pcmParam, pcmSignature, umlOperation, umlParameter);
    userExecution.update0Element(pcmParam, pcmSignature, umlOperation, umlParameter);
    
    postprocessElements();
    
    return true;
  }
}
