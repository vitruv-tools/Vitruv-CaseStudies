package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeDataTypePropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeDataTypePropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property classProperty, final Property compProperty) {
      return compProperty;
    }
    
    public void update0Element(final Property classProperty, final Property compProperty) {
      compProperty.setName(classProperty.getName());
    }
    
    public String getRetrieveTag1(final Property classProperty) {
      return SharedUtil.DATA_TYPE_PROPERTY;
    }
    
    public EObject getCorrepondenceSourceCompProperty(final Property classProperty) {
      return classProperty;
    }
  }
  
  public ChangeDataTypePropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property classProperty) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.ChangeDataTypePropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classProperty = classProperty;
  }
  
  private Property classProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeDataTypePropertyRoutine with input:");
    getLogger().debug("   classProperty: " + this.classProperty);
    
    org.eclipse.uml2.uml.Property compProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompProperty(classProperty), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(classProperty), 
    	false // asserted
    	);
    if (compProperty == null) {
    	return false;
    }
    registerObjectUnderModification(compProperty);
    // val updatedElement userExecution.getElement1(classProperty, compProperty);
    userExecution.update0Element(classProperty, compProperty);
    
    postprocessElements();
    
    return true;
  }
}
