package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
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
    
    public EObject getElement1(final ConcreteClassifier jClass, final Classifier jSuperClassifier, final org.eclipse.uml2.uml.Classifier uClass, final org.eclipse.uml2.uml.Classifier uSuperClassifier) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final ConcreteClassifier jClass, final Classifier jSuperClassifier) {
      return jClass;
    }
    
    public void update0Element(final ConcreteClassifier jClass, final Classifier jSuperClassifier, final org.eclipse.uml2.uml.Classifier uClass, final org.eclipse.uml2.uml.Classifier uSuperClassifier) {
      EList<org.eclipse.uml2.uml.Classifier> _generals = uClass.getGenerals();
      _generals.add(uSuperClassifier);
    }
    
    public EObject getCorrepondenceSourceUSuperClassifier(final ConcreteClassifier jClass, final Classifier jSuperClassifier, final org.eclipse.uml2.uml.Classifier uClass) {
      return jSuperClassifier;
    }
  }
  
  public AddUmlSuperClassifierRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier jClass, final Classifier jSuperClassifier) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlSuperClassifierRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jSuperClassifier = jSuperClassifier;
  }
  
  private ConcreteClassifier jClass;
  
  private Classifier jSuperClassifier;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlSuperClassifierRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.jClass);
    getLogger().debug("   Classifier: " + this.jSuperClassifier);
    
    org.eclipse.uml2.uml.Classifier uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jSuperClassifier), // correspondence source supplier
    	org.eclipse.uml2.uml.Classifier.class,
    	(org.eclipse.uml2.uml.Classifier _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    registerObjectUnderModification(uClass);
    org.eclipse.uml2.uml.Classifier uSuperClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUSuperClassifier(jClass, jSuperClassifier, uClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Classifier.class,
    	(org.eclipse.uml2.uml.Classifier _element) -> true, // correspondence precondition checker
    	null);
    if (uSuperClassifier == null) {
    	return;
    }
    registerObjectUnderModification(uSuperClassifier);
    // val updatedElement userExecution.getElement1(jClass, jSuperClassifier, uClass, uSuperClassifier);
    userExecution.update0Element(jClass, jSuperClassifier, uClass, uSuperClassifier);
    
    postprocessElements();
  }
}
