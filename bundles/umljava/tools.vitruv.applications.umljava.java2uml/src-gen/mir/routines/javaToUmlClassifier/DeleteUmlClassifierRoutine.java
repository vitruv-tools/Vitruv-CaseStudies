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
    
    public EObject getElement1(final ConcreteClassifier jClass, final CompilationUnit jCompUnit, final Classifier uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final ConcreteClassifier jClass, final CompilationUnit jCompUnit) {
      return jClass;
    }
    
    public EObject getElement2(final ConcreteClassifier jClass, final CompilationUnit jCompUnit, final Classifier uClass) {
      return jCompUnit;
    }
  }
  
  public DeleteUmlClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier jClass, final CompilationUnit jCompUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.DeleteUmlClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jCompUnit = jCompUnit;
  }
  
  private ConcreteClassifier jClass;
  
  private CompilationUnit jCompUnit;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteUmlClassifierRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.jClass);
    getLogger().debug("   CompilationUnit: " + this.jCompUnit);
    
    Classifier uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jCompUnit), // correspondence source supplier
    	Classifier.class,
    	(Classifier _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    registerObjectUnderModification(uClass);
    deleteObject(userExecution.getElement1(jClass, jCompUnit, uClass));
    
    deleteObject(userExecution.getElement2(jClass, jCompUnit, uClass));
    
    postprocessElements();
  }
}
