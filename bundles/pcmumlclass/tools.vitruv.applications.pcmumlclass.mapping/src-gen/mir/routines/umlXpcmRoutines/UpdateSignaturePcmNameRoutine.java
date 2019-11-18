package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateSignaturePcmNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateSignaturePcmNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Operation operation, final Parameter returnParameter, final Interface interface_, final OperationSignature operationSignature, @Extension final RoutinesFacade _routinesFacade) {
      operationSignature.setEntityName(operation.getName());
    }
    
    public String getRetrieveTag1(final Operation operation, final Parameter returnParameter, final Interface interface_) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final Operation operation, final Parameter returnParameter, final Interface interface_) {
      return operation;
    }
  }
  
  public UpdateSignaturePcmNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation operation, final Parameter returnParameter, final Interface interface_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateSignaturePcmNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.operation = operation;this.returnParameter = returnParameter;this.interface_ = interface_;
  }
  
  private Operation operation;
  
  private Parameter returnParameter;
  
  private Interface interface_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSignaturePcmNameRoutine with input:");
    getLogger().debug("   operation: " + this.operation);
    getLogger().debug("   returnParameter: " + this.returnParameter);
    getLogger().debug("   interface_: " + this.interface_);
    
    org.palladiosimulator.pcm.repository.OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(operation, returnParameter, interface_), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operation, returnParameter, interface_), 
    	false // asserted
    	);
    if (operationSignature == null) {
    	return false;
    }
    registerObjectUnderModification(operationSignature);
    userExecution.executeAction1(operation, returnParameter, interface_, operationSignature, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
