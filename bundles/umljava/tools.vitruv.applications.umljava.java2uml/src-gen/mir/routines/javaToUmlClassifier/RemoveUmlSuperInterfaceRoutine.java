package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Classifier;
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
    
    public EObject getElement1(final Interface jI, final Classifier jSuper, final org.eclipse.uml2.uml.Interface uInterface, final org.eclipse.uml2.uml.Interface uSuper) {
      return uInterface;
    }
    
    public void update0Element(final Interface jI, final Classifier jSuper, final org.eclipse.uml2.uml.Interface uInterface, final org.eclipse.uml2.uml.Interface uSuper) {
      UmlUtil.removeClassifierFromIterator(uInterface.getGenerals().iterator(), uSuper);
    }
    
    public EObject getCorrepondenceSourceUInterface(final Interface jI, final Classifier jSuper) {
      return jI;
    }
    
    public EObject getCorrepondenceSourceUSuper(final Interface jI, final Classifier jSuper, final org.eclipse.uml2.uml.Interface uInterface) {
      return jSuper;
    }
  }
  
  public RemoveUmlSuperInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface jI, final Classifier jSuper) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.RemoveUmlSuperInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlClassifier.RoutinesFacade(getExecutionState(), this);
    this.jI = jI;this.jSuper = jSuper;
  }
  
  private Interface jI;
  
  private Classifier jSuper;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveUmlSuperInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.jI);
    getLogger().debug("   Classifier: " + this.jSuper);
    
    org.eclipse.uml2.uml.Interface uInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUInterface(jI, jSuper), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uInterface == null) {
    	return;
    }
    registerObjectUnderModification(uInterface);
    org.eclipse.uml2.uml.Interface uSuper = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUSuper(jI, jSuper, uInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uSuper == null) {
    	return;
    }
    registerObjectUnderModification(uSuper);
    // val updatedElement userExecution.getElement1(jI, jSuper, uInterface, uSuper);
    userExecution.update0Element(jI, jSuper, uInterface, uSuper);
    
    postprocessElements();
  }
}
