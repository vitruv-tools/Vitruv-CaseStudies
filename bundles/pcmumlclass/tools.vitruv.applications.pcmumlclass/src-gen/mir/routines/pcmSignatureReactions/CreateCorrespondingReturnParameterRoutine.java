package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.DefaultLiterals;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingReturnParameterRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingReturnParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSource1(final OperationSignature pcmSignature, final Operation umlOperation) {
      return pcmSignature;
    }
    
    public void updateUmlReturnParamElement(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      umlReturnParam.setDirection(ParameterDirectionKind.RETURN_LITERAL);
      umlReturnParam.setName(DefaultLiterals.RETURN_PARAM_NAME);
      EList<Parameter> _ownedParameters = umlOperation.getOwnedParameters();
      _ownedParameters.add(umlReturnParam);
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getElement2(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return umlReturnParam;
    }
    
    public String getTag1(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public void callRoutine1(final OperationSignature pcmSignature, final Operation umlOperation, final Parameter umlReturnParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeTypeOfCorrespondingReturnParameter(pcmSignature, pcmSignature.getReturnType__OperationSignature());
    }
  }
  
  public CreateCorrespondingReturnParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.CreateCorrespondingReturnParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;
  }
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingReturnParameterRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature), 
    	true // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmSignature, umlOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmSignature, umlOperation)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Parameter umlReturnParam = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(umlReturnParam);
    userExecution.updateUmlReturnParamElement(pcmSignature, umlOperation, umlReturnParam);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmSignature, umlOperation, umlReturnParam), userExecution.getElement2(pcmSignature, umlOperation, umlReturnParam), userExecution.getTag1(pcmSignature, umlOperation, umlReturnParam));
    
    userExecution.callRoutine1(pcmSignature, umlOperation, umlReturnParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
