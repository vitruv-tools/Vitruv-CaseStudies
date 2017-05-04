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
public class DeleteCompDataTypeAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteCompDataTypeAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property classAttribute, final Property compAttribute) {
      return compAttribute;
    }
    
    public String getRetrieveTag1(final Property classAttribute) {
      return SharedUtil.DATA_TYPE_PROPERTY;
    }
    
    public EObject getElement2(final Property classAttribute, final Property compAttribute) {
      return classAttribute;
    }
    
    public EObject getElement3(final Property classAttribute, final Property compAttribute) {
      return compAttribute;
    }
    
    public EObject getCorrepondenceSourceCompAttribute(final Property classAttribute) {
      return classAttribute;
    }
  }
  
  public DeleteCompDataTypeAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property classAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.DeleteCompDataTypeAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classAttribute = classAttribute;
  }
  
  private Property classAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCompDataTypeAttributeRoutine with input:");
    getLogger().debug("   Property: " + this.classAttribute);
    
    Property compAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompAttribute(classAttribute), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(classAttribute));
    if (compAttribute == null) {
    	return;
    }
    registerObjectUnderModification(compAttribute);
    deleteObject(userExecution.getElement1(classAttribute, compAttribute));
    
    removeCorrespondenceBetween(userExecution.getElement2(classAttribute, compAttribute), userExecution.getElement3(classAttribute, compAttribute));
    
    postprocessElements();
  }
}
