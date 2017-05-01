package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlUtil;
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
    
    public EObject getElement1(final OperationSignature pcmSignature, final Operation umlOperation, final DataType umlReturnType) {
      return umlOperation;
    }
    
    public void update0Element(final OperationSignature pcmSignature, final Operation umlOperation, final DataType umlReturnType) {
      DataType returnType = umlReturnType;
      if ((returnType == null)) {
        org.palladiosimulator.pcm.repository.DataType _returnType__OperationSignature = pcmSignature.getReturnType__OperationSignature();
        if ((_returnType__OperationSignature instanceof PrimitiveDataType)) {
          InputOutput.<String>println("> is primitive type");
          org.palladiosimulator.pcm.repository.DataType _returnType__OperationSignature_1 = pcmSignature.getReturnType__OperationSignature();
          returnType = PcmToUmlUtil.getUmlPrimitiveType(((PrimitiveDataType) _returnType__OperationSignature_1).getType());
          InputOutput.<DataType>println(returnType);
        } else {
          org.palladiosimulator.pcm.repository.DataType _returnType__OperationSignature_2 = pcmSignature.getReturnType__OperationSignature();
          if ((_returnType__OperationSignature_2 instanceof CollectionDataType)) {
            final org.palladiosimulator.pcm.repository.DataType innerType = pcmSignature.getReturnType__OperationSignature();
            if ((innerType instanceof PrimitiveDataType)) {
              InputOutput.<String>println("> innerType is primitive type");
              returnType = PcmToUmlUtil.getUmlPrimitiveType(((PrimitiveDataType)innerType).getType());
              InputOutput.<DataType>println(returnType);
            }
          } else {
            InputOutput.<String>println("> is not primitive");
            InputOutput.<org.palladiosimulator.pcm.repository.DataType>println(pcmSignature.getReturnType__OperationSignature());
          }
        }
      }
      umlOperation.setType(returnType);
      org.palladiosimulator.pcm.repository.DataType _returnType__OperationSignature_3 = pcmSignature.getReturnType__OperationSignature();
      PcmToUmlUtil.updateOperationReturnTypeMultiplicity(umlOperation, 
        Boolean.valueOf((_returnType__OperationSignature_3 instanceof CollectionDataType)));
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSourceUmlReturnType(final OperationSignature pcmSignature, final Operation umlOperation) {
      org.palladiosimulator.pcm.repository.DataType _returnType__OperationSignature = pcmSignature.getReturnType__OperationSignature();
      return _returnType__OperationSignature;
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
    
    Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (umlOperation == null) {
    	return;
    }
    registerObjectUnderModification(umlOperation);
    DataType umlReturnType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlReturnType(pcmSignature, umlOperation), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(umlReturnType);
    // val updatedElement userExecution.getElement1(pcmSignature, umlOperation, umlReturnType);
    userExecution.update0Element(pcmSignature, umlOperation, umlReturnType);
    
    postprocessElements();
  }
}
