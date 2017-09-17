package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlClassAbstractRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlClassAbstractRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract, final org.eclipse.uml2.uml.Class uClass) {
      uClass.setIsAbstract((isAbstract).booleanValue());
    }
  }
  
  public SetUmlClassAbstractRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Boolean isAbstract) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.SetUmlClassAbstractRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.isAbstract = isAbstract;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private Boolean isAbstract;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlClassAbstractRoutine with input:");
    getLogger().debug("   jClass: " + this.jClass);
    getLogger().debug("   isAbstract: " + this.isAbstract);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, isAbstract), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uClass == null) {
    	return false;
    }
    registerObjectUnderModification(uClass);
    // val updatedElement userExecution.getElement1(jClass, isAbstract, uClass);
    userExecution.update0Element(jClass, isAbstract, uClass);
    
    postprocessElements();
    
    return true;
  }
}
