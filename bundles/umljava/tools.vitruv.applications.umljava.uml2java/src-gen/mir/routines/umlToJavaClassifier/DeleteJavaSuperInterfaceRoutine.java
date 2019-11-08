package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaSuperInterfaceRoutine extends AbstractRepairRoutineRealization {
  private DeleteJavaSuperInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Generalization uGeneralization, final TypeReference jReference) {
      return jReference;
    }
    
    public EObject getCorrepondenceSourceJReference(final Generalization uGeneralization) {
      return uGeneralization;
    }
  }
  
  public DeleteJavaSuperInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization uGeneralization) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.uGeneralization = uGeneralization;
  }
  
  private Generalization uGeneralization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaSuperInterfaceRoutine with input:");
    getLogger().debug("   uGeneralization: " + this.uGeneralization);
    
    org.emftext.language.java.types.TypeReference jReference = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJReference(uGeneralization), // correspondence source supplier
    	org.emftext.language.java.types.TypeReference.class,
    	(org.emftext.language.java.types.TypeReference _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jReference == null) {
    	return false;
    }
    registerObjectUnderModification(jReference);
    deleteObject(userExecution.getElement1(uGeneralization, jReference));
    
    postprocessElements();
    
    return true;
  }
}
