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
public class DeleteUmlSuperClassGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private DeleteUmlSuperClassGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final TypeReference jReference, final Generalization uGeneralization) {
      return uGeneralization;
    }
    
    public EObject getCorrepondenceSourceUGeneralization(final TypeReference jReference) {
      return jReference;
    }
  }
  
  public DeleteUmlSuperClassGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypeReference jReference) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.DeleteUmlSuperClassGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.jReference = jReference;
  }
  
  private TypeReference jReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteUmlSuperClassGeneralizationRoutine with input:");
    getLogger().debug("   jReference: " + this.jReference);
    
    org.eclipse.uml2.uml.Generalization uGeneralization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUGeneralization(jReference), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uGeneralization == null) {
    	return false;
    }
    registerObjectUnderModification(uGeneralization);
    deleteObject(userExecution.getElement1(jReference, uGeneralization));
    
    postprocessElements();
    
    return true;
  }
}
