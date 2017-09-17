package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
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
public class CreateOperationInterfaceSignatureRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationInterfaceSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final Interface umlInterface, final Operation umlOperation) {
      return umlInterface;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final Interface umlInterface, final Operation umlOperation) {
      EList<Operation> _ownedOperations = umlInterface.getOwnedOperations();
      _ownedOperations.add(umlOperation);
    }
    
    public void updateUmlOperationElement(final OperationSignature pcmSignature, final Interface umlInterface, final Operation umlOperation) {
      umlOperation.setName(pcmSignature.getEntityName());
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationSignature pcmSignature) {
      OperationInterface _interface__OperationSignature = pcmSignature.getInterface__OperationSignature();
      return _interface__OperationSignature;
    }
    
    public EObject getElement2(final OperationSignature pcmSignature, final Interface umlInterface, final Operation umlOperation) {
      return umlOperation;
    }
    
    public EObject getElement3(final OperationSignature pcmSignature, final Interface umlInterface, final Operation umlOperation) {
      return pcmSignature;
    }
  }
  
  public CreateOperationInterfaceSignatureRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateOperationInterfaceSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmSignature = pcmSignature;
  }
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationInterfaceSignatureRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    org.eclipse.uml2.uml.Operation umlOperation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(umlOperation);
    userExecution.updateUmlOperationElement(pcmSignature, umlInterface, umlOperation);
    
    // val updatedElement userExecution.getElement1(pcmSignature, umlInterface, umlOperation);
    userExecution.update0Element(pcmSignature, umlInterface, umlOperation);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmSignature, umlInterface, umlOperation), userExecution.getElement3(pcmSignature, umlInterface, umlOperation), "");
    
    postprocessElements();
    
    return true;
  }
}
