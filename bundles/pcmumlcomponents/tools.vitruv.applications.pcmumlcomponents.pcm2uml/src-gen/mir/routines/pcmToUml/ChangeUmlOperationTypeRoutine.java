package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlOperationTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlOperationTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final Model umlModel, final Operation umlOperation, final DataType umlReturnType) {
      return umlOperation;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final Model umlModel, final Operation umlOperation, final DataType umlReturnType) {
      DataType returnType = umlReturnType;
      if ((returnType == null)) {
        returnType = PcmToUmlUtil.retrieveUmlType(this.correspondenceModel, pcmSignature.getReturnType__OperationSignature(), umlModel);
      }
      umlOperation.setType(returnType);
      PcmToUmlUtil.updateOperationReturnTypeMultiplicity(umlOperation, 
        Boolean.valueOf(((pcmSignature.getReturnType__OperationSignature() instanceof CollectionDataType) && 
          (((CollectionDataType) pcmSignature.getReturnType__OperationSignature()).getInnerType_CollectionDataType() != null))));
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature, final Model umlModel) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSourceUmlReturnType(final OperationSignature pcmSignature, final Model umlModel, final Operation umlOperation) {
      org.palladiosimulator.pcm.repository.DataType _returnType__OperationSignature = pcmSignature.getReturnType__OperationSignature();
      return _returnType__OperationSignature;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final OperationSignature pcmSignature) {
      Repository _repository__Interface = pcmSignature.getInterface__OperationSignature().getRepository__Interface();
      return _repository__Interface;
    }
  }
  
  public ChangeUmlOperationTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ChangeUmlOperationTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmSignature = pcmSignature;
  }
  
  private OperationSignature pcmSignature;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlOperationTypeRoutine with input:");
    getLogger().debug("   OperationSignature: " + this.pcmSignature);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(pcmSignature), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    registerObjectUnderModification(umlModel);
    Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature, umlModel), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (umlOperation == null) {
    	return;
    }
    registerObjectUnderModification(umlOperation);
    DataType umlReturnType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlReturnType(pcmSignature, umlModel, umlOperation), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(umlReturnType);
    // val updatedElement userExecution.getElement1(pcmSignature, umlModel, umlOperation, umlReturnType);
    userExecution.update0Element(pcmSignature, umlModel, umlOperation, umlReturnType);
    
    postprocessElements();
  }
}
