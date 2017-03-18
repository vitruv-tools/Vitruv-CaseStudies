package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddInterfaceOperationParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddInterfaceOperationParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final DataType pcmType, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return umlParameter;
    }
    
    public EObject getElement2(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final DataType pcmType, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getCorrepondenceSourcePcmType(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature) {
      Type _type = umlParameter.getType();
      return _type;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation, final Parameter umlParameter) {
      return umlOperation;
    }
    
    public void updatePcmParameterElement(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final DataType pcmType, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      pcmParameter.setParameterName(umlParameter.getName());
      pcmParameter.setEntityName(umlParameter.getName());
    }
  }
  
  public AddInterfaceOperationParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Parameter umlParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.AddInterfaceOperationParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlParameter = umlParameter;
  }
  
  private Operation umlOperation;
  
  private Parameter umlParameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddInterfaceOperationParameterRoutine with input:");
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
    initializeRetrieveElementState(pcmSignature);
    DataType pcmType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmType(umlOperation, umlParameter, pcmSignature), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmType == null) {
    	return;
    }
    initializeRetrieveElementState(pcmType);
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = RepositoryFactoryImpl.eINSTANCE.createParameter();
    initializeCreateElementState(pcmParameter);
    userExecution.updatePcmParameterElement(umlOperation, umlParameter, pcmSignature, pcmType, pcmParameter);
    
    addCorrespondenceBetween(userExecution.getElement1(umlOperation, umlParameter, pcmSignature, pcmType, pcmParameter), userExecution.getElement2(umlOperation, umlParameter, pcmSignature, pcmType, pcmParameter), "");
    
    postprocessElementStates();
  }
}
