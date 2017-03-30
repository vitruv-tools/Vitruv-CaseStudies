package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeClassDataTypePropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeClassDataTypePropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceClassProperty(final Property compProperty) {
      return compProperty;
    }
    
    public EObject getElement1(final Property compProperty, final Property classProperty) {
      return classProperty;
    }
    
    public void update0Element(final Property compProperty, final Property classProperty) {
      classProperty.setName(compProperty.getName());
    }
    
    public String getRetrieveTag1(final Property compProperty) {
      return "DataTypeProperty";
    }
  }
  
  public ChangeClassDataTypePropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property compProperty) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.ChangeClassDataTypePropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compProperty = compProperty;
  }
  
  private Property compProperty;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeClassDataTypePropertyRoutine with input:");
    getLogger().debug("   Property: " + this.compProperty);
    
    Property classProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassProperty(compProperty), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(compProperty));
    if (classProperty == null) {
    	return;
    }
    registerObjectUnderModification(classProperty);
    // val updatedElement userExecution.getElement1(compProperty, classProperty);
    userExecution.update0Element(compProperty, classProperty);
    
    postprocessElements();
  }
}
