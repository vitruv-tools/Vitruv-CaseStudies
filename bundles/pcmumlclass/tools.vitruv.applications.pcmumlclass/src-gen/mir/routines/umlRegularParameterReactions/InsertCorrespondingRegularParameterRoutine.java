package mir.routines.umlRegularParameterReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRegularParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParam, final Operation umlOperation, final Optional<OperationSignature> pcmSignature) {
      return umlParam;
    }
    
    public EObject getCorrepondenceSourcePcmParam(final Parameter umlParam, final Operation umlOperation, final Optional<OperationSignature> pcmSignature) {
      return umlParam;
    }
    
    public String getRetrieveTag1(final Parameter umlParam, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final Parameter umlParam, final Operation umlOperation, final Optional<OperationSignature> pcmSignature) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public String getRetrieveTag3(final Parameter umlParam, final Operation umlOperation, final Optional<OperationSignature> pcmSignature) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Parameter umlParam, final Operation umlOperation) {
      return umlOperation;
    }
    
    public void callRoutine1(final Parameter umlParam, final Operation umlOperation, final Optional<OperationSignature> pcmSignature, final Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParam, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmSignature.isPresent();
      if (_isPresent) {
        _routinesFacade.detectOrCreateCorrespondingRegularParameter(umlParam, umlOperation);
        _routinesFacade.moveCorrespondingRegularParameter(umlParam, umlOperation);
      } else {
        _routinesFacade.deleteCorrespondingRegularParameter(umlParam);
      }
    }
  }
  
  public InsertCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParam, final Operation umlOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.InsertCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParam = umlParam;this.umlOperation = umlOperation;
  }
  
  private Parameter umlParam;
  
  private Operation umlOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   umlParam: " + this.umlParam);
    getLogger().debug("   umlOperation: " + this.umlOperation);
    
    	Optional<org.palladiosimulator.pcm.repository.OperationSignature> pcmSignature = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmSignature(umlParam, umlOperation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationSignature.class,
    		(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlParam, umlOperation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmSignature.isPresent() ? pcmSignature.get() : null);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParam, umlOperation, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParam, umlOperation, pcmSignature)
    ).isEmpty()) {
    	return false;
    }
    	Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParam = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmParam(umlParam, umlOperation, pcmSignature), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Parameter.class,
    		(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlParam, umlOperation, pcmSignature), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmParam.isPresent() ? pcmParam.get() : null);
    userExecution.callRoutine1(umlParam, umlOperation, pcmSignature, pcmParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
