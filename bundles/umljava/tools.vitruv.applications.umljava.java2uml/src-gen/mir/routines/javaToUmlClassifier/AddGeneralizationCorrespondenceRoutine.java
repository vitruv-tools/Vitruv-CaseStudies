package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddGeneralizationCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddGeneralizationCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Generalization uGeneralization, final TypeReference jReference) {
      return uGeneralization;
    }
    
    public EObject getCorrepondenceSource1(final Generalization uGeneralization, final TypeReference jReference) {
      return jReference;
    }
    
    public EObject getCorrepondenceSource2(final Generalization uGeneralization, final TypeReference jReference) {
      return uGeneralization;
    }
    
    public EObject getElement2(final Generalization uGeneralization, final TypeReference jReference) {
      return jReference;
    }
  }
  
  public AddGeneralizationCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization uGeneralization, final TypeReference jReference) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddGeneralizationCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.uGeneralization = uGeneralization;this.jReference = jReference;
  }
  
  private Generalization uGeneralization;
  
  private TypeReference jReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddGeneralizationCorrespondenceRoutine with input:");
    getLogger().debug("   uGeneralization: " + this.uGeneralization);
    getLogger().debug("   jReference: " + this.jReference);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uGeneralization, jReference), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(uGeneralization, jReference), // correspondence source supplier
    	org.emftext.language.java.types.TypeReference.class,
    	(org.emftext.language.java.types.TypeReference _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(uGeneralization, jReference), userExecution.getElement2(uGeneralization, jReference), "");
    
    postprocessElements();
    
    return true;
  }
}
