package mir.routines.java2PcmMethod;

import edu.kit.ipd.sdq.commons.util.org.palladiosimulator.pcm.repository.ParameterUtil;
import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OrdinaryParameter javaParameter, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      return javaParameter;
    }
    
    public void update0Element(final OrdinaryParameter javaParameter, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      EList<Parameter> _parameters__OperationSignature = operationSignature.getParameters__OperationSignature();
      _parameters__OperationSignature.add(pcmParameter);
    }
    
    public EObject getElement2(final OrdinaryParameter javaParameter, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getElement3(final OrdinaryParameter javaParameter, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      return operationSignature;
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final OrdinaryParameter javaParameter, final Parametrizable javaMethod) {
      return javaMethod;
    }
    
    public void updatePcmParameterElement(final OrdinaryParameter javaParameter, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      pcmParameter.setOperationSignature__Parameter(operationSignature);
      pcmParameter.setDataType__Parameter(TypeReferenceCorrespondenceHelper.getDataTypeFromTypeReference(javaParameter.getTypeReference(), this.correspondenceModel, 
        this.userInteracting, null));
      DataType _dataType__Parameter = pcmParameter.getDataType__Parameter();
      _dataType__Parameter.setRepository__DataType(operationSignature.getInterface__OperationSignature().getRepository__Interface());
      ParameterUtil.setName(pcmParameter, javaParameter.getName());
    }
  }
  
  public CreateParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter javaParameter, final Parametrizable javaMethod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.javaParameter = javaParameter;this.javaMethod = javaMethod;
  }
  
  private OrdinaryParameter javaParameter;
  
  private Parametrizable javaMethod;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateParameterRoutine with input:");
    getLogger().debug("   javaParameter: " + this.javaParameter);
    getLogger().debug("   javaMethod: " + this.javaMethod);
    
    org.palladiosimulator.pcm.repository.OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(javaParameter, javaMethod), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (operationSignature == null) {
    	return false;
    }
    registerObjectUnderModification(operationSignature);
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(pcmParameter);
    userExecution.updatePcmParameterElement(javaParameter, javaMethod, operationSignature, pcmParameter);
    
    addCorrespondenceBetween(userExecution.getElement1(javaParameter, javaMethod, operationSignature, pcmParameter), userExecution.getElement2(javaParameter, javaMethod, operationSignature, pcmParameter), "");
    
    // val updatedElement userExecution.getElement3(javaParameter, javaMethod, operationSignature, pcmParameter);
    userExecution.update0Element(javaParameter, javaMethod, operationSignature, pcmParameter);
    
    postprocessElements();
    
    return true;
  }
}
