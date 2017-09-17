package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInterfaceOperationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateInterfaceOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePcmOperationElement(final Operation umlOperation, final OperationInterface pcmInterface, final OperationSignature pcmOperation) {
      pcmOperation.setEntityName(umlOperation.getName());
    }
    
    public EObject getElement1(final Operation umlOperation, final OperationInterface pcmInterface, final OperationSignature pcmOperation) {
      return pcmInterface;
    }
    
    public void update0Element(final Operation umlOperation, final OperationInterface pcmInterface, final OperationSignature pcmOperation) {
      EList<OperationSignature> _signatures__OperationInterface = pcmInterface.getSignatures__OperationInterface();
      _signatures__OperationInterface.add(pcmOperation);
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Operation umlOperation) {
      Interface _interface = umlOperation.getInterface();
      return _interface;
    }
    
    public EObject getElement2(final Operation umlOperation, final OperationInterface pcmInterface, final OperationSignature pcmOperation) {
      return umlOperation;
    }
    
    public EObject getElement3(final Operation umlOperation, final OperationInterface pcmInterface, final OperationSignature pcmOperation) {
      return pcmOperation;
    }
  }
  
  public CreateInterfaceOperationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateInterfaceOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlOperation = umlOperation;
  }
  
  private Operation umlOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceOperationRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlOperation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    org.palladiosimulator.pcm.repository.OperationSignature pcmOperation = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationSignature();
    notifyObjectCreated(pcmOperation);
    userExecution.updatePcmOperationElement(umlOperation, pcmInterface, pcmOperation);
    
    // val updatedElement userExecution.getElement1(umlOperation, pcmInterface, pcmOperation);
    userExecution.update0Element(umlOperation, pcmInterface, pcmOperation);
    
    addCorrespondenceBetween(userExecution.getElement2(umlOperation, pcmInterface, pcmOperation), userExecution.getElement3(umlOperation, pcmInterface, pcmOperation), "");
    
    postprocessElements();
    
    return true;
  }
}
