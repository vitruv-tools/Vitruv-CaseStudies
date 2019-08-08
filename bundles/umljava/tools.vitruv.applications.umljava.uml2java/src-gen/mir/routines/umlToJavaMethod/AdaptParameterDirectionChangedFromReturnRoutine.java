package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypesFactory;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdaptParameterDirectionChangedFromReturnRoutine extends AbstractRepairRoutineRealization {
  private AdaptParameterDirectionChangedFromReturnRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJMethod(final Operation uOperation) {
      return uOperation;
    }
    
    public EObject getElement1(final Operation uOperation, final Method jMethod) {
      return jMethod;
    }
    
    public void update0Element(final Operation uOperation, final Method jMethod) {
      jMethod.setTypeReference(TypesFactory.eINSTANCE.createVoid());
    }
  }
  
  public AdaptParameterDirectionChangedFromReturnRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.AdaptParameterDirectionChangedFromReturnRoutine.ActionUserExecution(getExecutionState(), this);
    this.uOperation = uOperation;
  }
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdaptParameterDirectionChangedFromReturnRoutine with input:");
    getLogger().debug("   uOperation: " + this.uOperation);
    
    org.emftext.language.java.members.Method jMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMethod(uOperation), // correspondence source supplier
    	org.emftext.language.java.members.Method.class,
    	(org.emftext.language.java.members.Method _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jMethod == null) {
    	return false;
    }
    registerObjectUnderModification(jMethod);
    // val updatedElement userExecution.getElement1(uOperation, jMethod);
    userExecution.update0Element(uOperation, jMethod);
    
    postprocessElements();
    
    return true;
  }
}
