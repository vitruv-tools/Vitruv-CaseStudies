package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlMethodAbstractRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlMethodAbstractRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ClassMethod jMeth, final Boolean isAbstract, final Operation uOperation) {
      return uOperation;
    }
    
    public void update0Element(final ClassMethod jMeth, final Boolean isAbstract, final Operation uOperation) {
      uOperation.setIsAbstract((isAbstract).booleanValue());
    }
    
    public EObject getCorrepondenceSourceUOperation(final ClassMethod jMeth, final Boolean isAbstract) {
      return jMeth;
    }
  }
  
  public SetUmlMethodAbstractRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod jMeth, final Boolean isAbstract) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.SetUmlMethodAbstractRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.isAbstract = isAbstract;
  }
  
  private ClassMethod jMeth;
  
  private Boolean isAbstract;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlMethodAbstractRoutine with input:");
    getLogger().debug("   jMeth: " + this.jMeth);
    getLogger().debug("   isAbstract: " + this.isAbstract);
    
    org.eclipse.uml2.uml.Operation uOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUOperation(jMeth, isAbstract), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uOperation == null) {
    	return false;
    }
    registerObjectUnderModification(uOperation);
    // val updatedElement userExecution.getElement1(jMeth, isAbstract, uOperation);
    userExecution.update0Element(jMeth, isAbstract, uOperation);
    
    postprocessElements();
    
    return true;
  }
}
