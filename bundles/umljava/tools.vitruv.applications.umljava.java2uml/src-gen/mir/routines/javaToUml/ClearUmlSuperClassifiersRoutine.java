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
public class ClearUmlSuperClassifiersRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ClearUmlSuperClassifiersRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ConcreteClassifier jClass, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final ConcreteClassifier jClass) {
      return jClass;
    }
    
    public void update0Element(final ConcreteClassifier jClass, final org.eclipse.uml2.uml.Class uClass) {
      EList<Classifier> _generals = uClass.getGenerals();
      _generals.clear();
    }
  }
  
  public ClearUmlSuperClassifiersRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier jClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.ClearUmlSuperClassifiersRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;
  }
  
  private ConcreteClassifier jClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ClearUmlSuperClassifiersRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.jClass);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    initializeRetrieveElementState(uClass);
    // val updatedElement userExecution.getElement1(jClass, uClass);
    userExecution.update0Element(jClass, uClass);
    
    postprocessElementStates();
  }
}
