package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationSignatureParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationSignatureParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final Parameter pcmParameter, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return umlOperation;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final Parameter pcmParameter, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParameter) {
      EList<org.eclipse.uml2.uml.Parameter> _ownedParameters = umlOperation.getOwnedParameters();
      _ownedParameters.add(umlParameter);
    }
    
    public void updateUmlParameterElement(final OperationSignature pcmSignature, final Parameter pcmParameter, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParameter) {
      umlParameter.setName(pcmParameter.getParameterName());
    }
    
    public EObject getElement2(final OperationSignature pcmSignature, final Parameter pcmParameter, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return umlParameter;
    }
    
    public EObject getElement3(final OperationSignature pcmSignature, final Parameter pcmParameter, final Operation umlOperation, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return pcmParameter;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature, final Parameter pcmParameter) {
      return pcmSignature;
    }
  }
  
  public CreateOperationSignatureParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final Parameter pcmParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateOperationSignatureParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmParameter = pcmParameter;
  }
  
  private OperationSignature pcmSignature;
  
  private Parameter pcmParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationSignatureParameterRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmParameter: " + this.pcmParameter);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature, pcmParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    org.eclipse.uml2.uml.Parameter umlParameter = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(umlParameter);
    userExecution.updateUmlParameterElement(pcmSignature, pcmParameter, umlOperation, umlParameter);
    
    // val updatedElement userExecution.getElement1(pcmSignature, pcmParameter, umlOperation, umlParameter);
    userExecution.update0Element(pcmSignature, pcmParameter, umlOperation, umlParameter);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmSignature, pcmParameter, umlOperation, umlParameter), userExecution.getElement3(pcmSignature, pcmParameter, umlOperation, umlParameter), "");
    
    postprocessElements();
    
    return true;
  }
}
