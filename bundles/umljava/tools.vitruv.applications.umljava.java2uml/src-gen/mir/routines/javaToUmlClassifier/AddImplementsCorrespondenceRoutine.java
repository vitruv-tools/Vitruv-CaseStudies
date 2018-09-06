package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddImplementsCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddImplementsCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final TypeReference jReference, final InterfaceRealization uRealization) {
      return uRealization;
    }
    
    public EObject getCorrepondenceSource1(final TypeReference jReference, final InterfaceRealization uRealization) {
      return jReference;
    }
    
    public EObject getElement2(final TypeReference jReference, final InterfaceRealization uRealization) {
      return jReference;
    }
  }
  
  public AddImplementsCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypeReference jReference, final InterfaceRealization uRealization) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddImplementsCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.jReference = jReference;this.uRealization = uRealization;
  }
  
  private TypeReference jReference;
  
  private InterfaceRealization uRealization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddImplementsCorrespondenceRoutine with input:");
    getLogger().debug("   jReference: " + this.jReference);
    getLogger().debug("   uRealization: " + this.uRealization);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(jReference, uRealization), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(jReference, uRealization), userExecution.getElement2(jReference, uRealization), "");
    
    postprocessElements();
    
    return true;
  }
}
