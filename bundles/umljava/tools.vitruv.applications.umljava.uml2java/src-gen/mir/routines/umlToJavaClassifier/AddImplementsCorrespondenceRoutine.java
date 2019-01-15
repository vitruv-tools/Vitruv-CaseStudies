package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
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
    
    public EObject getElement1(final InterfaceRealization uRealization, final TypeReference jReference) {
      return uRealization;
    }
    
    public EObject getCorrepondenceSource1(final InterfaceRealization uRealization, final TypeReference jReference) {
      return uRealization;
    }
    
    public EObject getElement2(final InterfaceRealization uRealization, final TypeReference jReference) {
      return jReference;
    }
  }
  
  public AddImplementsCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization uRealization, final TypeReference jReference) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.AddImplementsCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.uRealization = uRealization;this.jReference = jReference;
  }
  
  private InterfaceRealization uRealization;
  
  private TypeReference jReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddImplementsCorrespondenceRoutine with input:");
    getLogger().debug("   uRealization: " + this.uRealization);
    getLogger().debug("   jReference: " + this.jReference);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uRealization, jReference), // correspondence source supplier
    	org.emftext.language.java.types.TypeReference.class,
    	(org.emftext.language.java.types.TypeReference _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(uRealization, jReference), userExecution.getElement2(uRealization, jReference), "");
    
    postprocessElements();
    
    return true;
  }
}
