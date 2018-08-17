package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingOperationRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Operation umlOperation) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSource1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface) {
      return pcmSignature;
    }
    
    public void updateUmlOperationElement(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Operation umlOperation) {
      umlOperation.setName(pcmSignature.getEntityName());
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getElement2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Operation umlOperation) {
      return umlOperation;
    }
    
    public String getTag1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public void callRoutine1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Operation umlOperation, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createCorrespondingReturnParameter(pcmSignature);
    }
  }
  
  public CreateCorrespondingOperationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.CreateCorrespondingOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmInterface = pcmInterface;
  }
  
  private OperationSignature pcmSignature;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingOperationRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmSignature, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, pcmInterface), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSignature, pcmInterface, umlInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSignature, pcmInterface, umlInterface)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Operation umlOperation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(umlOperation);
    userExecution.updateUmlOperationElement(pcmSignature, pcmInterface, umlInterface, umlOperation);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmSignature, pcmInterface, umlInterface, umlOperation), userExecution.getElement2(pcmSignature, pcmInterface, umlInterface, umlOperation), userExecution.getTag1(pcmSignature, pcmInterface, umlInterface, umlOperation));
    
    userExecution.callRoutine1(pcmSignature, pcmInterface, umlInterface, umlOperation, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
