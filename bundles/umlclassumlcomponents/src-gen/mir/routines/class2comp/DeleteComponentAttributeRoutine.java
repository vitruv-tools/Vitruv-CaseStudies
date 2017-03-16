package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteComponentAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteComponentAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property classAttribute, final Property componentAttribute) {
      return componentAttribute;
    }
    
    public EObject getElement2(final Property classAttribute, final Property componentAttribute) {
      return classAttribute;
    }
    
    public EObject getElement3(final Property classAttribute, final Property componentAttribute) {
      return componentAttribute;
    }
    
    public EObject getCorrepondenceSourceComponentAttribute(final Property classAttribute) {
      return classAttribute;
    }
  }
  
  public DeleteComponentAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property classAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteComponentAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classAttribute = classAttribute;
  }
  
  private Property classAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteComponentAttributeRoutine with input:");
    getLogger().debug("   Property: " + this.classAttribute);
    
    Property componentAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponentAttribute(classAttribute), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (componentAttribute == null) {
    	return;
    }
    registerObjectUnderModification(componentAttribute);
    deleteObject(userExecution.getElement1(classAttribute, componentAttribute));
    
    removeCorrespondenceBetween(userExecution.getElement2(classAttribute, componentAttribute), userExecution.getElement3(classAttribute, componentAttribute));
    
    postprocessElements();
  }
}
