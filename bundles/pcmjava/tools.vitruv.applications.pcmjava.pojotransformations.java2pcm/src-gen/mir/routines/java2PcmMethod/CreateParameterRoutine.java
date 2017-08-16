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
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
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
    
    public EObject getElement1(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      return jaMoPPParam;
    }
    
    public void update0Element(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      EList<Parameter> _parameters__OperationSignature = operationSignature.getParameters__OperationSignature();
      _parameters__OperationSignature.add(pcmParameter);
    }
    
    public EObject getElement2(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getElement3(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      return operationSignature;
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod) {
      return javaMethod;
    }
    
    public EObject getCorrepondenceSourcenull(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod, final OperationSignature operationSignature) {
      return jaMoPPParam;
    }
    
    public void updatePcmParameterElement(final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod, final OperationSignature operationSignature, final Parameter pcmParameter) {
      pcmParameter.setOperationSignature__Parameter(operationSignature);
      pcmParameter.setDataType__Parameter(TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(jaMoPPParam.getTypeReference(), this.correspondenceModel, 
        this.userInteracting, null, jaMoPPParam.getArrayDimension()));
      DataType _dataType__Parameter = pcmParameter.getDataType__Parameter();
      _dataType__Parameter.setRepository__DataType(operationSignature.getInterface__OperationSignature().getRepository__Interface());
      ParameterUtil.setName(pcmParameter, jaMoPPParam.getName());
    }
  }
  
  public CreateParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter jaMoPPParam, final Parametrizable javaMethod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.jaMoPPParam = jaMoPPParam;this.javaMethod = javaMethod;
  }
  
  private OrdinaryParameter jaMoPPParam;
  
  private Parametrizable javaMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateParameterRoutine with input:");
    getLogger().debug("   OrdinaryParameter: " + this.jaMoPPParam);
    getLogger().debug("   Parametrizable: " + this.javaMethod);
    
    OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(jaMoPPParam, javaMethod), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (operationSignature == null) {
    	return;
    }
    registerObjectUnderModification(operationSignature);
    if (getCorrespondingElement(
    	userExecution.getCorrepondenceSourcenull(jaMoPPParam, javaMethod, operationSignature), // correspondence source supplier
    	Parameter.class,
    	(Parameter _element) -> true, // correspondence precondition checker
    	null) != null) {
    	return;
    }
    Parameter pcmParameter = RepositoryFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(pcmParameter);
    userExecution.updatePcmParameterElement(jaMoPPParam, javaMethod, operationSignature, pcmParameter);
    
    addCorrespondenceBetween(userExecution.getElement1(jaMoPPParam, javaMethod, operationSignature, pcmParameter), userExecution.getElement2(jaMoPPParam, javaMethod, operationSignature, pcmParameter), "");
    
    // val updatedElement userExecution.getElement3(jaMoPPParam, javaMethod, operationSignature, pcmParameter);
    userExecution.update0Element(jaMoPPParam, javaMethod, operationSignature, pcmParameter);
    
    postprocessElements();
  }
}
