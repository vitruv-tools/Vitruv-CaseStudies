package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddClassDataTypePropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddClassDataTypePropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property compProperty, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return dataTypeClass;
    }
    
    public void update0Element(final Property compProperty, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      EList<Property> _ownedAttributes = dataTypeClass.getOwnedAttributes();
      _ownedAttributes.add(classProperty);
    }
    
    public String getRetrieveTag1(final Property compProperty, final DataType compDataType) {
      return SharedUtil.DATA_TYPE_REPRESENTATION_TAG;
    }
    
    public void updateClassPropertyElement(final Property compProperty, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      classProperty.setName(compProperty.getName());
      classProperty.setType(compProperty.getType());
      classProperty.setVisibility(compProperty.getVisibility());
    }
    
    public EObject getElement2(final Property compProperty, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return classProperty;
    }
    
    public EObject getCorrepondenceSourceDataTypeClass(final Property compProperty, final DataType compDataType) {
      return compDataType;
    }
    
    public EObject getElement3(final Property compProperty, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return compProperty;
    }
    
    public String getTag1(final Property compProperty, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return SharedUtil.DATA_TYPE_PROPERTY;
    }
  }
  
  public AddClassDataTypePropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property compProperty, final DataType compDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.AddClassDataTypePropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compProperty = compProperty;this.compDataType = compDataType;
  }
  
  private Property compProperty;
  
  private DataType compDataType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddClassDataTypePropertyRoutine with input:");
    getLogger().debug("   compProperty: " + this.compProperty);
    getLogger().debug("   compDataType: " + this.compDataType);
    
    org.eclipse.uml2.uml.Class dataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeClass(compProperty, compDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(compProperty, compDataType), 
    	false // asserted
    	);
    if (dataTypeClass == null) {
    	return false;
    }
    registerObjectUnderModification(dataTypeClass);
    org.eclipse.uml2.uml.Property classProperty = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(classProperty);
    userExecution.updateClassPropertyElement(compProperty, compDataType, dataTypeClass, classProperty);
    
    // val updatedElement userExecution.getElement1(compProperty, compDataType, dataTypeClass, classProperty);
    userExecution.update0Element(compProperty, compDataType, dataTypeClass, classProperty);
    
    addCorrespondenceBetween(userExecution.getElement2(compProperty, compDataType, dataTypeClass, classProperty), userExecution.getElement3(compProperty, compDataType, dataTypeClass, classProperty), userExecution.getTag1(compProperty, compDataType, dataTypeClass, classProperty));
    
    postprocessElements();
    
    return true;
  }
}
