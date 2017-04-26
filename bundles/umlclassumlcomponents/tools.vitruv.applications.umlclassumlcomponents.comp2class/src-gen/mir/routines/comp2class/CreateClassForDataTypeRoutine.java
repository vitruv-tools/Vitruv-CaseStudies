package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateClassForDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateClassForDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      return classModel;
    }
    
    public void update0Element(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      EList<PackageableElement> _packagedElements = classModel.getPackagedElements();
      _packagedElements.add(dataTypeClass);
    }
    
    public EObject getElement2(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      return compType;
    }
    
    public EObject getElement3(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      return dataTypeClass;
    }
    
    public String getTag1(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      return "DataTypeRepresentation";
    }
    
    public void updateDataTypeClassElement(final DataType compType, final Model classModel, final org.eclipse.uml2.uml.Class dataTypeClass) {
      dataTypeClass.setName(compType.getName());
    }
    
    public EObject getCorrepondenceSourceClassModel(final DataType compType) {
      Model _model = compType.getModel();
      return _model;
    }
  }
  
  public CreateClassForDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType compType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassForDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compType = compType;
  }
  
  private DataType compType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassForDataTypeRoutine with input:");
    getLogger().debug("   DataType: " + this.compType);
    
    Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(compType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (classModel == null) {
    	return;
    }
    registerObjectUnderModification(classModel);
    org.eclipse.uml2.uml.Class dataTypeClass = UMLFactoryImpl.eINSTANCE.createClass();
    userExecution.updateDataTypeClassElement(compType, classModel, dataTypeClass);
    
    // val updatedElement userExecution.getElement1(compType, classModel, dataTypeClass);
    userExecution.update0Element(compType, classModel, dataTypeClass);
    
    addCorrespondenceBetween(userExecution.getElement2(compType, classModel, dataTypeClass), userExecution.getElement3(compType, classModel, dataTypeClass), userExecution.getTag1(compType, classModel, dataTypeClass));
    
    postprocessElements();
  }
}
