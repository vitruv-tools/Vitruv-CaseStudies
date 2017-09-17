package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaSuperClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaSuperClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      jClass.setExtends(null);
    }
  }
  
  public DeleteJavaSuperClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class uClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaSuperClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.uClass = uClass;
  }
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaSuperClassRoutine with input:");
    getLogger().debug("   uClass: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(uClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jClass == null) {
    	return false;
    }
    registerObjectUnderModification(jClass);
    // val updatedElement userExecution.getElement1(uClass, jClass);
    userExecution.update0Element(uClass, jClass);
    
    postprocessElements();
    
    return true;
  }
}
