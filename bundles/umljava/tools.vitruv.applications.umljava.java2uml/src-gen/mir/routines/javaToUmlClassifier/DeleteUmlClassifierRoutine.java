package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteUmlClassifierRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteUmlClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit, final Classifier uClassfier) {
      return uClassfier;
    }
    
    public EObject getCorrepondenceSourceUClassfier(final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit) {
      return jClassifier;
    }
    
    public EObject getElement2(final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit, final Classifier uClassfier) {
      return jCompUnit;
    }
  }
  
  public DeleteUmlClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier jClassifier, final CompilationUnit jCompUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jClassifier = jClassifier;this.jCompUnit = jCompUnit;
  }
  
  private ConcreteClassifier jClassifier;
  
  private CompilationUnit jCompUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteUmlClassifierRoutine with input:");
    getLogger().debug("   jClassifier: " + this.jClassifier);
    getLogger().debug("   jCompUnit: " + this.jCompUnit);
    
    org.eclipse.uml2.uml.Classifier uClassfier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClassfier(jClassifier, jCompUnit), // correspondence source supplier
    	org.eclipse.uml2.uml.Classifier.class,
    	(org.eclipse.uml2.uml.Classifier _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClassfier == null) {
    	return false;
    }
    registerObjectUnderModification(uClassfier);
    deleteObject(userExecution.getElement1(jClassifier, jCompUnit, uClassfier));
    
    deleteObject(userExecution.getElement2(jClassifier, jCompUnit, uClassfier));
    
    postprocessElements();
    
    return true;
  }
}
