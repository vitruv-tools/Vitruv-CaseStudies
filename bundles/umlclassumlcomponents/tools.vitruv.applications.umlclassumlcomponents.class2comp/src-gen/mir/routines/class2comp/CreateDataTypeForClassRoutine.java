package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDataTypeForClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeForClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final DataType compDataType) {
      return compModel;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final DataType compDataType) {
      EList<PackageableElement> _packagedElements = compModel.getPackagedElements();
      _packagedElements.add(compDataType);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final DataType compDataType) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Class umlClass) {
      Model _model = umlClass.getModel();
      return _model;
    }
    
    public void updateCompDataTypeElement(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final DataType compDataType) {
      compDataType.setName(umlClass.getName());
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final DataType compDataType) {
      return compDataType;
    }
    
    public String getTag1(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final DataType compDataType) {
      return SharedUtil.DATA_TYPE_REPRESENTATION_TAG;
    }
  }
  
  public CreateDataTypeForClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateDataTypeForClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeForClassRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    
    org.eclipse.uml2.uml.Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClass), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (compModel == null) {
    	return false;
    }
    registerObjectUnderModification(compModel);
    org.eclipse.uml2.uml.DataType compDataType = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createDataType();
    notifyObjectCreated(compDataType);
    userExecution.updateCompDataTypeElement(umlClass, compModel, compDataType);
    
    // val updatedElement userExecution.getElement1(umlClass, compModel, compDataType);
    userExecution.update0Element(umlClass, compModel, compDataType);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, compModel, compDataType), userExecution.getElement3(umlClass, compModel, compDataType), userExecution.getTag1(umlClass, compModel, compDataType));
    
    postprocessElements();
    
    return true;
  }
}
