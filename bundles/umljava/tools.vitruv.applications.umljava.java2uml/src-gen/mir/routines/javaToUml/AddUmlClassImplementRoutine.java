package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlClassImplementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddUmlClassImplementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final Interface jI, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Interface uI) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final Interface jI) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final Interface jI, final org.eclipse.uml2.uml.Class uClass, final org.eclipse.uml2.uml.Interface uI) {
      String _name = uI.getName();
      String _plus = ("IR-" + _name);
      uClass.createInterfaceRealization(_plus, uI);
    }
    
    public EObject getCorrepondenceSourceUI(final org.emftext.language.java.classifiers.Class jClass, final Interface jI, final org.eclipse.uml2.uml.Class uClass) {
      return jI;
    }
  }
  
  public AddUmlClassImplementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Interface jI) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.AddUmlClassImplementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.jI = jI;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private Interface jI;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlClassImplementRoutine with input:");
    getLogger().debug("   Class: " + this.jClass);
    getLogger().debug("   Interface: " + this.jI);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, jI), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    initializeRetrieveElementState(uClass);
    org.eclipse.uml2.uml.Interface uI = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUI(jClass, jI, uClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null);
    if (uI == null) {
    	return;
    }
    initializeRetrieveElementState(uI);
    // val updatedElement userExecution.getElement1(jClass, jI, uClass, uI);
    userExecution.update0Element(jClass, jI, uClass, uI);
    
    postprocessElementStates();
  }
}
