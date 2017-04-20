package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public boolean getCorrespondingModelElementsPreconditionUmlComponent(final org.eclipse.uml2.uml.Class umlClass, final Component umlComponent) {
      boolean _equals = umlComponent.getName().equals(umlClass.getName());
      return _equals;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Component umlComponent) {
      return umlComponent;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Component umlComponent) {
      umlComponent.setName(umlClass.getName());
    }
    
    public EObject getCorrepondenceSourceUmlComponent(final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
  }
  
  public RenameComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RenameComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameComponentRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    Component umlComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponent(umlClass), // correspondence source supplier
    	Component.class,
    	(Component _element) -> userExecution.getCorrespondingModelElementsPreconditionUmlComponent(umlClass, _element), // correspondence precondition checker
    	null);
    if (umlComponent == null) {
    	return;
    }
    registerObjectUnderModification(umlComponent);
    // val updatedElement userExecution.getElement1(umlClass, umlComponent);
    userExecution.update0Element(umlClass, umlComponent);
    
    postprocessElements();
  }
}
