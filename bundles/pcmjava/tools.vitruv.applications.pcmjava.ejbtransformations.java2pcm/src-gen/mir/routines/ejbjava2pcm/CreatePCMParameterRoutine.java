package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmjava.util.PcmJavaUtils;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePCMParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return opSignature;
    }
    
    public void update0Element(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      opSignature.getParameters__OperationSignature().add(pcmParameter);
    }
    
    public EObject getElement2(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getElement3(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return jaMoPPParam;
    }
    
    public void updatePcmParameterElement(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      PcmJavaUtils.setParameterName(pcmParameter, jaMoPPParam.getName());
      pcmParameter.setDataType__Parameter(TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(jaMoPPParam.getTypeReference(), 
        this.correspondenceModel, this.userInteracting, opSignature.getInterface__OperationSignature().getRepository__Interface(), jaMoPPParam.getArrayDimension()));
    }
  }
  
  public CreatePCMParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter jaMoPPParam, final OperationSignature opSignature) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatePCMParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.jaMoPPParam = jaMoPPParam;this.opSignature = opSignature;
  }
  
  private Parameter jaMoPPParam;
  
  private OperationSignature opSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMParameterRoutine with input:");
    getLogger().debug("   jaMoPPParam: " + this.jaMoPPParam);
    getLogger().debug("   opSignature: " + this.opSignature);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(pcmParameter);
    userExecution.updatePcmParameterElement(jaMoPPParam, opSignature, pcmParameter);
    
    // val updatedElement userExecution.getElement1(jaMoPPParam, opSignature, pcmParameter);
    userExecution.update0Element(jaMoPPParam, opSignature, pcmParameter);
    
    addCorrespondenceBetween(userExecution.getElement2(jaMoPPParam, opSignature, pcmParameter), userExecution.getElement3(jaMoPPParam, opSignature, pcmParameter), "");
    
    postprocessElements();
    
    return true;
  }
}
