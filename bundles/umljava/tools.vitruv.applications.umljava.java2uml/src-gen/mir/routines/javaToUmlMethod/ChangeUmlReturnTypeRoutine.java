package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
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
    
    public EObject getElement1(final Method jMeth, final TypeReference jType, final Operation uOperation) {
      return uOperation;
    }
    
    public void update0Element(final Method jMeth, final TypeReference jType, final Operation uOperation) {
      uOperation.setType(JavaToUmlHelper.getUmlType(jType, JavaToUmlHelper.getUmlModel(this.changePropagationObservable, this.correspondenceModel, this.userInteracting), this.correspondenceModel));
    }
    
    public EObject getCorrepondenceSourceUOperation(final Method jMeth, final TypeReference jType) {
      return jMeth;
    }
  }
  
  public ChangeUmlReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method jMeth, final TypeReference jType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.ChangeUmlReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.jType = jType;
  }
  
  private Method jMeth;
  
  private TypeReference jType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlReturnTypeRoutine with input:");
    getLogger().debug("   jMeth: " + this.jMeth);
    getLogger().debug("   jType: " + this.jType);
    
    org.eclipse.uml2.uml.Operation uOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUOperation(jMeth, jType), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uOperation == null) {
    	return false;
    }
    registerObjectUnderModification(uOperation);
    // val updatedElement userExecution.getElement1(jMeth, jType, uOperation);
    userExecution.update0Element(jMeth, jType, uOperation);
    
    postprocessElements();
    
    return true;
  }
}
