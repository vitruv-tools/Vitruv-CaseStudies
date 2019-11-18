package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateSignatureUmlReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private UpdateSignatureUmlReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface, final Parameter umlParam, @Extension final RoutinesFacade _routinesFacade) {
      InputOutput.<String>println("update signature uml type");
      umlParam.setDirection(ParameterDirectionKind.RETURN_LITERAL);
    }
    
    public String getRetrieveTag1(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlParam(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
      return operationSignature;
    }
    
    public void callRoutine1(final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface, final Parameter umlParam, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.pcmDataTypePropagationReactions.setUmlParameterType(returnType, umlParam);
    }
  }
  
  public UpdateSignatureUmlReturnTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature, final DataType returnType, final OperationInterface operationInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateSignatureUmlReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationSignature = operationSignature;this.returnType = returnType;this.operationInterface = operationInterface;
  }
  
  private OperationSignature operationSignature;
  
  private DataType returnType;
  
  private OperationInterface operationInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateSignatureUmlReturnTypeRoutine with input:");
    getLogger().debug("   operationSignature: " + this.operationSignature);
    getLogger().debug("   returnType: " + this.returnType);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    
    org.eclipse.uml2.uml.Parameter umlParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParam(operationSignature, returnType, operationInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operationSignature, returnType, operationInterface), 
    	false // asserted
    	);
    if (umlParam == null) {
    	return false;
    }
    registerObjectUnderModification(umlParam);
    userExecution.executeAction1(operationSignature, returnType, operationInterface, umlParam, this.getRoutinesFacade());
    
    userExecution.callRoutine1(operationSignature, returnType, operationInterface, umlParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
