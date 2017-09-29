package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeDataTypeAttributeTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeDataTypeAttributeTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property classProperty, final DataType classType, final Property compAttribute, final DataType compType) {
      return compAttribute;
    }
    
    public void update0Element(final Property classProperty, final DataType classType, final Property compAttribute, final DataType compType) {
      compAttribute.setType(compType);
    }
    
    public String getRetrieveTag1(final Property classProperty, final DataType classType) {
      return SharedUtil.DATA_TYPE_PROPERTY;
    }
    
    public EObject getCorrepondenceSourceCompAttribute(final Property classProperty, final DataType classType) {
      return classProperty;
    }
    
    public EObject getCorrepondenceSourceCompType(final Property classProperty, final DataType classType, final Property compAttribute) {
      return classType;
    }
  }
  
  public ChangeDataTypeAttributeTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property classProperty, final DataType classType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.ChangeDataTypeAttributeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classProperty = classProperty;this.classType = classType;
  }
  
  private Property classProperty;
  
  private DataType classType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeDataTypeAttributeTypeRoutine with input:");
    getLogger().debug("   classProperty: " + this.classProperty);
    getLogger().debug("   classType: " + this.classType);
    
    org.eclipse.uml2.uml.Property compAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompAttribute(classProperty, classType), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(classProperty, classType), 
    	false // asserted
    	);
    if (compAttribute == null) {
    	return false;
    }
    registerObjectUnderModification(compAttribute);
    org.eclipse.uml2.uml.DataType compType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompType(classProperty, classType, compAttribute), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compType == null) {
    	return false;
    }
    registerObjectUnderModification(compType);
    // val updatedElement userExecution.getElement1(classProperty, classType, compAttribute, compType);
    userExecution.update0Element(classProperty, classType, compAttribute, compType);
    
    postprocessElements();
    
    return true;
  }
}
