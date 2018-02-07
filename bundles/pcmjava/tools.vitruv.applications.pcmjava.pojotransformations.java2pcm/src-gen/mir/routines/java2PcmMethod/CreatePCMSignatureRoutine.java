package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMSignatureRoutine extends AbstractRepairRoutineRealization {
  private CreatePCMSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceMethod interfaceMethod, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      return pcmInterface;
    }
    
    public void updateOperationSignatureElement(final InterfaceMethod interfaceMethod, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      operationSignature.setEntityName(interfaceMethod.getName());
      operationSignature.setInterface__OperationSignature(pcmInterface);
    }
    
    public void update0Element(final InterfaceMethod interfaceMethod, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      EList<OperationSignature> _signatures__OperationInterface = pcmInterface.getSignatures__OperationInterface();
      _signatures__OperationInterface.add(operationSignature);
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final InterfaceMethod interfaceMethod) {
      ConcreteClassifier _containingConcreteClassifier = interfaceMethod.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public EObject getElement2(final InterfaceMethod interfaceMethod, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      return operationSignature;
    }
    
    public EObject getElement3(final InterfaceMethod interfaceMethod, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      return interfaceMethod;
    }
  }
  
  public CreatePCMSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod interfaceMethod) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreatePCMSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.interfaceMethod = interfaceMethod;
  }
  
  private InterfaceMethod interfaceMethod;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMSignatureRoutine with input:");
    getLogger().debug("   interfaceMethod: " + this.interfaceMethod);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(interfaceMethod), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    org.palladiosimulator.pcm.repository.OperationSignature operationSignature = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationSignature();
    notifyObjectCreated(operationSignature);
    userExecution.updateOperationSignatureElement(interfaceMethod, pcmInterface, operationSignature);
    
    // val updatedElement userExecution.getElement1(interfaceMethod, pcmInterface, operationSignature);
    userExecution.update0Element(interfaceMethod, pcmInterface, operationSignature);
    
    addCorrespondenceBetween(userExecution.getElement2(interfaceMethod, pcmInterface, operationSignature), userExecution.getElement3(interfaceMethod, pcmInterface, operationSignature), "");
    
    postprocessElements();
    
    return true;
  }
}
