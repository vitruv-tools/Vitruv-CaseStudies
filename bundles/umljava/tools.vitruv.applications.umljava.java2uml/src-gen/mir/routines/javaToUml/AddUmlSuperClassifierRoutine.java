package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlSuperClassifierRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlSuperClassifierRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ConcreteClassifier jClass, final ConcreteClassifier jSuper, final Classifier uClass, final Classifier uSuper) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final ConcreteClassifier jClass, final ConcreteClassifier jSuper) {
      return jClass;
    }
    
    public void update0Element(final ConcreteClassifier jClass, final ConcreteClassifier jSuper, final Classifier uClass, final Classifier uSuper) {
      EList<Classifier> _generals = uClass.getGenerals();
      _generals.add(uSuper);
    }
    
    public EObject getCorrepondenceSourceUSuper(final ConcreteClassifier jClass, final ConcreteClassifier jSuper, final Classifier uClass) {
      return jSuper;
    }
  }
  
  public AddUmlSuperClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier jClass, final ConcreteClassifier jSuper) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.AddUmlSuperClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jSuper = jSuper;
  }
  
  private ConcreteClassifier jClass;
  
  private ConcreteClassifier jSuper;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlSuperClassifierRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.jClass);
    getLogger().debug("   ConcreteClassifier: " + this.jSuper);
    
    Classifier uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jSuper), // correspondence source supplier
    	Classifier.class,
    	(Classifier _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    initializeRetrieveElementState(uClass);
    Classifier uSuper = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUSuper(jClass, jSuper, uClass), // correspondence source supplier
    	Classifier.class,
    	(Classifier _element) -> true, // correspondence precondition checker
    	null);
    if (uSuper == null) {
    	return;
    }
    initializeRetrieveElementState(uSuper);
    // val updatedElement userExecution.getElement1(jClass, jSuper, uClass, uSuper);
    userExecution.update0Element(jClass, jSuper, uClass, uSuper);
    
    postprocessElementStates();
  }
}
