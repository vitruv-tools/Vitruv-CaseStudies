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
public class ChangeUmlReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Method jMeth, final TypeReference jType, final OperationSignature operationSignature) {
      return operationSignature;
    }
    
    public void update0Element(final Method jMeth, final TypeReference jType, final OperationSignature operationSignature) {
      final Repository repository = operationSignature.getInterface__OperationSignature().getRepository__Interface();
      operationSignature.setReturnType__OperationSignature(Java2PcmHelper.getPCMDataTypeForTypeReference(jType, this.correspondenceModel, this.userInteracting, repository, jMeth));
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final Method jMeth, final TypeReference jType) {
      return jMeth;
    }
  }
  
  public ChangeUmlReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMeth, final TypeReference jType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.ChangeUmlReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jType = jType;
  }
  
  private Method jMeth;
  
  private TypeReference jType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlReturnTypeRoutine with input:");
    getLogger().debug("   Method: " + this.jMeth);
    getLogger().debug("   TypeReference: " + this.jType);
    
    OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(jMeth, jType), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (operationSignature == null) {
    	return;
    }
    registerObjectUnderModification(operationSignature);
    // val updatedElement userExecution.getElement1(jMeth, jType, operationSignature);
    userExecution.update0Element(jMeth, jType, operationSignature);
    
    postprocessElements();
  }
}
