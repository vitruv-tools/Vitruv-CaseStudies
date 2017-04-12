package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import tools.vitruv.applications.umljava.util.UmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveUmlSuperInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveUmlSuperInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface jI, final ConcreteClassifier jSuper, final org.eclipse.uml2.uml.Interface uI, final org.eclipse.uml2.uml.Interface uSuper) {
      return uI;
    }
    
    public void update0Element(final Interface jI, final ConcreteClassifier jSuper, final org.eclipse.uml2.uml.Interface uI, final org.eclipse.uml2.uml.Interface uSuper) {
      UmlUtil.removeClassifierFromIterator(uI.getGenerals().iterator(), uSuper);
    }
    
    public EObject getCorrepondenceSourceUI(final Interface jI, final ConcreteClassifier jSuper) {
      return jI;
    }
    
    public EObject getCorrepondenceSourceUSuper(final Interface jI, final ConcreteClassifier jSuper, final org.eclipse.uml2.uml.Interface uI) {
      return jSuper;
    }
  }
  
  public RemoveUmlSuperInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jI, final ConcreteClassifier jSuper) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jI = jI;this.jSuper = jSuper;
  }
  
  private Interface jI;
  
  private ConcreteClassifier jSuper;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveUmlSuperInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.jI);
    getLogger().debug("   ConcreteClassifier: " + this.jSuper);
    
    org.eclipse.uml2.uml.Interface uI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUI(jI, jSuper), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uI == null) {
    	return;
    }
    registerObjectUnderModification(uI);
    org.eclipse.uml2.uml.Interface uSuper = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUSuper(jI, jSuper, uI), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uSuper == null) {
    	return;
    }
    registerObjectUnderModification(uSuper);
    // val updatedElement userExecution.getElement1(jI, jSuper, uI, uSuper);
    userExecution.update0Element(jI, jSuper, uI, uSuper);
    
    postprocessElements();
  }
}
