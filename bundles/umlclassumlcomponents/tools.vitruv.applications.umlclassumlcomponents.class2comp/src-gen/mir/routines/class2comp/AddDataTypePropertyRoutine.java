package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddDataTypePropertyRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddDataTypePropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCompDataType(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return dataTypeClass;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty, final DataType compDataType, final Property compProperty) {
      return compDataType;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty, final DataType compDataType, final Property compProperty) {
      EList<Property> _ownedAttributes = compDataType.getOwnedAttributes();
      _ownedAttributes.add(compProperty);
    }
    
    public void updateCompPropertyElement(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty, final DataType compDataType, final Property compProperty) {
      compProperty.setName(classProperty.getName());
      compProperty.setDatatype(classProperty.getDatatype());
      compProperty.setVisibility(classProperty.getVisibility());
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
      return SharedUtil.DATA_TYPE_REPRESENTATION_TAG;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty, final DataType compDataType, final Property compProperty) {
      return classProperty;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty, final DataType compDataType, final Property compProperty) {
      return compProperty;
    }
  }
  
  public AddDataTypePropertyRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class dataTypeClass, final Property classProperty) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.AddDataTypePropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.dataTypeClass = dataTypeClass;this.classProperty = classProperty;
  }
  
  private org.eclipse.uml2.uml.Class dataTypeClass;
  
  private Property classProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddDataTypePropertyRoutine with input:");
    getLogger().debug("   dataTypeClass: " + this.dataTypeClass);
    getLogger().debug("   classProperty: " + this.classProperty);
    
    org.eclipse.uml2.uml.DataType compDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompDataType(dataTypeClass, classProperty), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(dataTypeClass, classProperty), 
    	false // asserted
    	);
    if (compDataType == null) {
    	return false;
    }
    registerObjectUnderModification(compDataType);
    org.eclipse.uml2.uml.Property compProperty = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(compProperty);
    userExecution.updateCompPropertyElement(dataTypeClass, classProperty, compDataType, compProperty);
    
    // val updatedElement userExecution.getElement1(dataTypeClass, classProperty, compDataType, compProperty);
    userExecution.update0Element(dataTypeClass, classProperty, compDataType, compProperty);
    
    addCorrespondenceBetween(userExecution.getElement2(dataTypeClass, classProperty, compDataType, compProperty), userExecution.getElement3(dataTypeClass, classProperty, compDataType, compProperty), "");
    
    postprocessElements();
    
    return true;
  }
}
