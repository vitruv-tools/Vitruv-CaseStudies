package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
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
      return SharedUtil.DATA_TYPE_PROPERTY;
    }
  }
  
  public ChangeClassDataTypePropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property compProperty) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.ChangeClassDataTypePropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compProperty = compProperty;
  }
  
  private Property compProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeClassDataTypePropertyRoutine with input:");
    getLogger().debug("   compProperty: " + this.compProperty);
    
    org.eclipse.uml2.uml.Property classProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassProperty(compProperty), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(compProperty), 
    	false // asserted
    	);
    if (classProperty == null) {
    	return false;
    }
    registerObjectUnderModification(classProperty);
    // val updatedElement userExecution.getElement1(compProperty, classProperty);
    userExecution.update0Element(compProperty, classProperty);
    
    postprocessElements();
    
    return true;
  }
}
