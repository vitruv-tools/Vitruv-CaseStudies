package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameUmlClassifierRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameUmlClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ConcreteClassifier jClass, final Classifier uClass, final CompilationUnit jCompUnit) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final ConcreteClassifier jClass) {
      return jClass;
    }
    
    public void update0Element(final ConcreteClassifier jClass, final Classifier uClass, final CompilationUnit jCompUnit) {
      String _name = jClass.getName();
      uClass.setName(_name);
    }
    
    public EObject getElement2(final ConcreteClassifier jClass, final Classifier uClass, final CompilationUnit jCompUnit) {
      return jCompUnit;
    }
    
    public EObject getCorrepondenceSourceJCompUnit(final ConcreteClassifier jClass, final Classifier uClass) {
      return uClass;
    }
    
    public void update1Element(final ConcreteClassifier jClass, final Classifier uClass, final CompilationUnit jCompUnit) {
      String _name = jClass.getName();
      jCompUnit.setName(_name);
    }
  }
  
  public RenameUmlClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier jClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.RenameUmlClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;
  }
  
  private ConcreteClassifier jClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameUmlClassifierRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.jClass);
    
    Classifier uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass), // correspondence source supplier
    	Classifier.class,
    	(Classifier _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    initializeRetrieveElementState(uClass);
    CompilationUnit jCompUnit = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJCompUnit(jClass, uClass), // correspondence source supplier
    	CompilationUnit.class,
    	(CompilationUnit _element) -> true, // correspondence precondition checker
    	null);
    if (jCompUnit == null) {
    	return;
    }
    initializeRetrieveElementState(jCompUnit);
    // val updatedElement userExecution.getElement1(jClass, uClass, jCompUnit);
    userExecution.update0Element(jClass, uClass, jCompUnit);
    
    // val updatedElement userExecution.getElement2(jClass, uClass, jCompUnit);
    userExecution.update1Element(jClass, uClass, jCompUnit);
    
    postprocessElementStates();
  }
}
