package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlClassFinalRoutine extends AbstractRepairRoutineRealization {
  private SetUmlClassFinalRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal, final org.eclipse.uml2.uml.Class uClass) {
      uClass.setIsFinalSpecialization((isFinal).booleanValue());
    }
  }
  
  public SetUmlClassFinalRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Boolean isFinal) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.SetUmlClassFinalRoutine.ActionUserExecution(getExecutionState(), this);
    this.jClass = jClass;this.isFinal = isFinal;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private Boolean isFinal;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlClassFinalRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   isFinal: " + this.isFinal);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, isFinal), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClass == null) {
    	return false;
    }
    registerObjectUnderModification(uClass);
    // val updatedElement userExecution.getElement1(jClass, isFinal, uClass);
    userExecution.update0Element(jClass, isFinal, uClass);
    
    postprocessElements();
    
    return true;
  }
}
