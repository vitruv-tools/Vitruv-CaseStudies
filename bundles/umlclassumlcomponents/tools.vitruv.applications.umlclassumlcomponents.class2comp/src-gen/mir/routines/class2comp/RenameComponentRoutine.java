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
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Component component) {
      return component;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Component component) {
      component.setName(umlClass.getName());
    }
    
    public EObject getCorrepondenceSourceComponent(final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public boolean getCorrespondingModelElementsPreconditionComponent(final org.eclipse.uml2.uml.Class umlClass, final Component component) {
      boolean _equals = component.getName().equals(umlClass.getName());
      return _equals;
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
    
    Component component = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponent(umlClass), // correspondence source supplier
    	Component.class,
    	(Component _element) -> userExecution.getCorrespondingModelElementsPreconditionComponent(umlClass, _element), // correspondence precondition checker
    	null);
    if (component == null) {
    	return;
    }
    registerObjectUnderModification(component);
    // val updatedElement userExecution.getElement1(umlClass, component);
    userExecution.update0Element(umlClass, component);
    
    postprocessElements();
  }
}
