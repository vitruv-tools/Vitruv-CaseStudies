package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaClassifierRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Classifier umlClassifier, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      return javaClassifier;
    }
    
    public void update0Element(final Classifier umlClassifier, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      javaClassifier.setName(umlClassifier.getName());
    }
    
    public EObject getElement2(final Classifier umlClassifier, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      return javaCompilationUnit;
    }
    
    public EObject getCorrepondenceSourceJavaCompilationUnit(final Classifier umlClassifier, final ConcreteClassifier javaClassifier) {
      return umlClassifier;
    }
    
    public EObject getCorrepondenceSourceJavaClassifier(final Classifier umlClassifier) {
      return umlClassifier;
    }
    
    public void update1Element(final Classifier umlClassifier, final ConcreteClassifier javaClassifier, final CompilationUnit javaCompilationUnit) {
      String _name = umlClassifier.getName();
      String _plus = (_name + ".java");
      javaCompilationUnit.setName(_plus);
      this.persistProjectRelative(umlClassifier, javaCompilationUnit, JavaPersistenceHelper.buildJavaFilePath(javaCompilationUnit));
    }
  }
  
  public RenameJavaClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier umlClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.RenameJavaClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.umlClassifier = umlClassifier;
  }
  
  private Classifier umlClassifier;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaClassifierRoutine with input:");
    getLogger().debug("   umlClassifier: " + this.umlClassifier);
    
    org.emftext.language.java.classifiers.ConcreteClassifier javaClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClassifier(umlClassifier), // correspondence source supplier
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
    	userExecution.getCorrepondenceSourceJavaCompilationUnit(umlClassifier, javaClassifier), // correspondence source supplier
    	org.emftext.language.java.containers.CompilationUnit.class,
    	(org.emftext.language.java.containers.CompilationUnit _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaCompilationUnit == null) {
    	return false;
    }
    registerObjectUnderModification(javaCompilationUnit);
    // val updatedElement userExecution.getElement1(umlClassifier, javaClassifier, javaCompilationUnit);
    userExecution.update0Element(umlClassifier, javaClassifier, javaCompilationUnit);
    
    // val updatedElement userExecution.getElement2(umlClassifier, javaClassifier, javaCompilationUnit);
    userExecution.update1Element(umlClassifier, javaClassifier, javaCompilationUnit);
    
    postprocessElements();
    
    return true;
  }
}
