package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature signature, final Parameter parameter, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
      return javaParameter;
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final OperationSignature signature, final Parameter parameter) {
      return signature;
    }
    
    public EObject getCorrepondenceSourceJavaParameter(final OperationSignature signature, final Parameter parameter, final InterfaceMethod interfaceMethod) {
      return parameter;
    }
  }
  
  public DeleteParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature signature, final Parameter parameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.DeleteParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.signature = signature;this.parameter = parameter;
  }
  
  private OperationSignature signature;
  
  private Parameter parameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteParameterRoutine with input:");
    getLogger().debug("   signature: " + this.signature);
    getLogger().debug("   parameter: " + this.parameter);
    
    org.emftext.language.java.members.InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(signature, parameter), // correspondence source supplier
    	org.emftext.language.java.members.InterfaceMethod.class,
    	(org.emftext.language.java.members.InterfaceMethod _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (interfaceMethod == null) {
    	return false;
    }
    registerObjectUnderModification(interfaceMethod);
    org.emftext.language.java.parameters.OrdinaryParameter javaParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaParameter(signature, parameter, interfaceMethod), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaParameter == null) {
    	return false;
    }
    registerObjectUnderModification(javaParameter);
    deleteObject(userExecution.getElement1(signature, parameter, interfaceMethod, javaParameter));
    
    postprocessElements();
    
    return true;
  }
}
