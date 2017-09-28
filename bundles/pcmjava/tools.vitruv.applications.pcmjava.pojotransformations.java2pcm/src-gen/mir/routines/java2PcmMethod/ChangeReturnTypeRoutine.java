package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Method javaMethod, final TypeReference typeReference, final OperationSignature operationSignature) {
      return operationSignature;
    }
    
    public void update0Element(final Method javaMethod, final TypeReference typeReference, final OperationSignature operationSignature) {
      final Repository repository = operationSignature.getInterface__OperationSignature().getRepository__Interface();
      operationSignature.setReturnType__OperationSignature(Java2PcmHelper.getPCMDataTypeForTypeReference(typeReference, this.correspondenceModel, this.userInteracting, repository, javaMethod));
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final Method javaMethod, final TypeReference typeReference) {
      return javaMethod;
    }
  }
  
  public ChangeReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method javaMethod, final TypeReference typeReference) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.ChangeReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.javaMethod = javaMethod;this.typeReference = typeReference;
  }
  
  private Method javaMethod;
  
  private TypeReference typeReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeReturnTypeRoutine with input:");
    getLogger().debug("   javaMethod: " + this.javaMethod);
    getLogger().debug("   typeReference: " + this.typeReference);
    
    org.palladiosimulator.pcm.repository.OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(javaMethod, typeReference), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (operationSignature == null) {
    	return false;
    }
    registerObjectUnderModification(operationSignature);
    // val updatedElement userExecution.getElement1(javaMethod, typeReference, operationSignature);
    userExecution.update0Element(javaMethod, typeReference, operationSignature);
    
    postprocessElements();
    
    return true;
  }
}
