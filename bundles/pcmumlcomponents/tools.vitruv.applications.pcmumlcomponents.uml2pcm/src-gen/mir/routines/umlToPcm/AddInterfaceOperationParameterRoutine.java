package mir.routines.umlToPcm;

import edu.kit.ipd.sdq.commons.util.org.palladiosimulator.pcm.repository.ParameterUtil;
import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
    
    public EObject getElement1(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmSignature;
    }
    
    public void update0Element(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      EList<org.palladiosimulator.pcm.repository.Parameter> _parameters__OperationSignature = pcmSignature.getParameters__OperationSignature();
      _parameters__OperationSignature.add(pcmParameter);
    }
    
    public EObject getElement2(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return umlParameter;
    }
    
    public EObject getElement3(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation, final Parameter umlParameter) {
      return umlOperation;
    }
    
    public void updatePcmParameterElement(final Operation umlOperation, final Parameter umlParameter, final OperationSignature pcmSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      ParameterUtil.setName(pcmParameter, umlParameter.getName());
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddInterfaceOperationParameterRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlOperation, umlParameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(pcmParameter);
    userExecution.updatePcmParameterElement(umlOperation, umlParameter, pcmSignature, pcmParameter);
    
    // val updatedElement userExecution.getElement1(umlOperation, umlParameter, pcmSignature, pcmParameter);
    userExecution.update0Element(umlOperation, umlParameter, pcmSignature, pcmParameter);
    
    addCorrespondenceBetween(userExecution.getElement2(umlOperation, umlParameter, pcmSignature, pcmParameter), userExecution.getElement3(umlOperation, umlParameter, pcmSignature, pcmParameter), "");
    
    postprocessElements();
    
    return true;
  }
}
