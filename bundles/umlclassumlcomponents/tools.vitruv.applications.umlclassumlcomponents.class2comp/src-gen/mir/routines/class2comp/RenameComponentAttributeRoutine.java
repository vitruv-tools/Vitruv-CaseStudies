package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameComponentAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameComponentAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property classAttribute, final Property componentAttribute) {
      return componentAttribute;
    }
    
    public void update0Element(final Property classAttribute, final Property componentAttribute) {
      String _name = classAttribute.getName();
      componentAttribute.setName(_name);
    }
    
    public EObject getCorrepondenceSourceComponentAttribute(final Property classAttribute) {
      return classAttribute;
    }
  }
  
  public RenameComponentAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property classAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RenameComponentAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classAttribute = classAttribute;
  }
  
  private Property classAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameComponentAttributeRoutine with input:");
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
    // val updatedElement userExecution.getElement1(classAttribute, componentAttribute);
    userExecution.update0Element(classAttribute, componentAttribute);
    
    postprocessElements();
  }
}
