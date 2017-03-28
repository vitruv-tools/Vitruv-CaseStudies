package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInterfaceOperationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeInterfaceOperationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final DataType pcmType) {
      return pcmSignature;
    }
    
    public void update0Element(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final DataType pcmType) {
      pcmSignature.setReturnType__OperationSignature(pcmType);
    }
    
    public EObject getCorrepondenceSourcePcmType(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature) {
      Type _type = umlParameter.getType();
      return _type;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation, final Parameter umlParameter) {
      return umlOperation;
    }
  }
  
  public ChangeInterfaceOperationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Parameter umlParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangeInterfaceOperationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlParameter = umlParameter;
  }
  
  private Operation umlOperation;
  
  private Parameter umlParameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInterfaceOperationTypeRoutine with input:");
    getLogger().debug("   Operation: " + this.umlOperation);
    getLogger().debug("   Parameter: " + this.umlParameter);
    
    OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlOperation, umlParameter), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (pcmSignature == null) {
    	return;
    }
    registerObjectUnderModification(pcmSignature);
    DataType pcmType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmType(umlOperation, umlParameter, pcmSignature), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmType == null) {
    	return;
    }
    registerObjectUnderModification(pcmType);
    // val updatedElement userExecution.getElement1(umlOperation, umlParameter, pcmSignature, pcmType);
    userExecution.update0Element(umlOperation, umlParameter, pcmSignature, pcmType);
    
    postprocessElements();
  }
}
