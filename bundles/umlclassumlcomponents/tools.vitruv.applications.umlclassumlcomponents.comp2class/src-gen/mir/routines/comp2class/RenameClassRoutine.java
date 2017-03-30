package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public void update0Element(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass) {
      String _name = umlComp.getName();
      umlClass.setName(_name);
    }
    
    public EObject getCorrepondenceSourceUmlClass(final Component umlComp) {
      return umlComp;
    }
    
    public boolean getCorrespondingModelElementsPreconditionUmlClass(final Component umlComp, final org.eclipse.uml2.uml.Class umlClass) {
      String _name = umlClass.getName();
      org.eclipse.uml2.uml.Package _package = umlComp.getPackage();
      String _name_1 = _package.getName();
      boolean _equals = _name.equals(_name_1);
      return _equals;
    }
  }
  
  public RenameClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RenameClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComp = umlComp;
  }
  
  private Component umlComp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameClassRoutine with input:");
    getLogger().debug("   Component: " + this.umlComp);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(umlComp), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> userExecution.getCorrespondingModelElementsPreconditionUmlClass(umlComp, _element), // correspondence precondition checker
    	null);
    if (umlClass == null) {
    	return;
    }
    registerObjectUnderModification(umlClass);
    // val updatedElement userExecution.getElement1(umlComp, umlClass);
    userExecution.update0Element(umlComp, umlClass);
    
    postprocessElements();
  }
}
