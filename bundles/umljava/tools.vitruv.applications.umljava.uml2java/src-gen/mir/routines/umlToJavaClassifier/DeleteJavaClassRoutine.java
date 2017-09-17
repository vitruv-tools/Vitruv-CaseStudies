package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Classifier umlClassifer, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      return javaClassifier;
    }
    
    public EObject getElement2(final Classifier umlClassifer, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaCompilationUnit(final Classifier umlClassifer, final ConcreteClassifier javaClassifier) {
      return umlClassifer;
    }
    
    public EObject getCorrepondenceSourceJavaClassifier(final Classifier umlClassifer) {
      return umlClassifer;
    }
  }
  
  public DeleteJavaClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier umlClassifer) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.umlClassifer = umlClassifer;
  }
  
  private Classifier umlClassifer;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaClassRoutine with input:");
    getLogger().debug("   umlClassifer: " + this.umlClassifer);
    
    org.emftext.language.java.classifiers.ConcreteClassifier javaClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClassifier(umlClassifer), // correspondence source supplier
    	org.emftext.language.java.classifiers.ConcreteClassifier.class,
    	(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaClassifier == null) {
    	return false;
    }
    registerObjectUnderModification(javaClassifier);
    org.emftext.language.java.containers.CompilationUnit javaCompilationUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaCompilationUnit(umlClassifer, javaClassifier), // correspondence source supplier
    	org.emftext.language.java.containers.CompilationUnit.class,
    	(org.emftext.language.java.containers.CompilationUnit _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaCompilationUnit == null) {
    	return false;
    }
    registerObjectUnderModification(javaCompilationUnit);
    deleteObject(userExecution.getElement1(umlClassifer, javaClassifier, javaCompilationUnit));
    
    deleteObject(userExecution.getElement2(umlClassifer, javaClassifier, javaCompilationUnit));
    
    postprocessElements();
    
    return true;
  }
}
