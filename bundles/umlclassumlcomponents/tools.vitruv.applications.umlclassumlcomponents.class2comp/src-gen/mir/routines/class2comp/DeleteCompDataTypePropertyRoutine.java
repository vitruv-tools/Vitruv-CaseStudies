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
public class DeleteCompDataTypePropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteCompDataTypePropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property classProperty, final Property compProperty) {
      return compProperty;
    }
    
    public String getRetrieveTag1(final Property classProperty) {
      return SharedUtil.DATA_TYPE_PROPERTY;
    }
    
    public EObject getElement2(final Property classProperty, final Property compProperty) {
      return classProperty;
    }
    
    public EObject getElement3(final Property classProperty, final Property compProperty) {
      return compProperty;
    }
    
    public EObject getCorrepondenceSourceCompProperty(final Property classProperty) {
      return classProperty;
    }
  }
  
  public DeleteCompDataTypePropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property classProperty) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteCompDataTypePropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classProperty = classProperty;
  }
  
  private Property classProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCompDataTypePropertyRoutine with input:");
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
    deleteObject(userExecution.getElement1(classProperty, compProperty));
    
    removeCorrespondenceBetween(userExecution.getElement2(classProperty, compProperty), userExecution.getElement3(classProperty, compProperty), "");
    
    postprocessElements();
    
    return true;
  }
}
