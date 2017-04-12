package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import tools.vitruv.applications.umljava.util.JavaUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaImplementedInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaImplementedInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface uI, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jI) {
      return jClass;
    }
    
    public EObject getCorrepondenceSourceJClass(final Interface uI, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public void update0Element(final Interface uI, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass, final org.emftext.language.java.classifiers.Interface jI) {
      JavaUtil.removeClassifierFromIterator(jClass.getImplements().iterator(), jI);
    }
    
    public EObject getCorrepondenceSourceJI(final Interface uI, final org.eclipse.uml2.uml.Class uClass, final org.emftext.language.java.classifiers.Class jClass) {
      return uI;
    }
  }
  
  public DeleteJavaImplementedInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface uI, final org.eclipse.uml2.uml.Class uClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaImplementedInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.uI = uI;this.uClass = uClass;
  }
  
  private Interface uI;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaImplementedInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.uI);
    getLogger().debug("   Class: " + this.uClass);
    
    org.emftext.language.java.classifiers.Class jClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClass(uI, uClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (jClass == null) {
    	return;
    }
    registerObjectUnderModification(jClass);
    org.emftext.language.java.classifiers.Interface jI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJI(uI, uClass, jClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (jI == null) {
    	return;
    }
    registerObjectUnderModification(jI);
    // val updatedElement userExecution.getElement1(uI, uClass, jClass, jI);
    userExecution.update0Element(uI, uClass, jClass, jI);
    
    postprocessElements();
  }
}
