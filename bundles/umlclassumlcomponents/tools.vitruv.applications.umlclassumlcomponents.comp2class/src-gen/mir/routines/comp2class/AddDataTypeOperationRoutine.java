package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddDataTypeOperationRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddDataTypeOperationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation compOperation, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return dataTypeClass;
    }
    
    public void update0Element(final Operation compOperation, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      EList<Operation> _ownedOperations = dataTypeClass.getOwnedOperations();
      _ownedOperations.add(classOperation);
    }
    
    public String getRetrieveTag1(final Operation compOperation, final DataType compDataType, final Model umlModel) {
      return "DataTypeRepresentation";
    }
    
    public EObject getElement2(final Operation compOperation, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return classOperation;
    }
    
    public EObject getCorrepondenceSourceDataTypeClass(final Operation compOperation, final DataType compDataType, final Model umlModel) {
      return compDataType;
    }
    
    public EObject getElement3(final Operation compOperation, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return compOperation;
    }
    
    public String getTag1(final Operation compOperation, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return "DataTypeOperation";
    }
    
    public EObject getCorrepondenceSourceUmlModel(final Operation compOperation, final DataType compDataType) {
      Model _model = compOperation.getModel();
      return _model;
    }
    
    public void updateClassOperationElement(final Operation compOperation, final DataType compDataType, final Model umlModel, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      String _name = compOperation.getName();
      classOperation.setName(_name);
      Type _type = compOperation.getType();
      classOperation.setType(_type);
    }
  }
  
  public AddDataTypeOperationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation compOperation, final DataType compDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.AddDataTypeOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compOperation = compOperation;this.compDataType = compDataType;
  }
  
  private Operation compOperation;
  
  private DataType compDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddDataTypeOperationRoutine with input:");
    getLogger().debug("   Operation: " + this.compOperation);
    getLogger().debug("   DataType: " + this.compDataType);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(compOperation, compDataType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    registerObjectUnderModification(umlModel);
    org.eclipse.uml2.uml.Class dataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeClass(compOperation, compDataType, umlModel), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(compOperation, compDataType, umlModel));
    if (dataTypeClass == null) {
    	return;
    }
    registerObjectUnderModification(dataTypeClass);
    Operation classOperation = UMLFactoryImpl.eINSTANCE.createOperation();
    userExecution.updateClassOperationElement(compOperation, compDataType, umlModel, dataTypeClass, classOperation);
    
    // val updatedElement userExecution.getElement1(compOperation, compDataType, umlModel, dataTypeClass, classOperation);
    userExecution.update0Element(compOperation, compDataType, umlModel, dataTypeClass, classOperation);
    
    addCorrespondenceBetween(userExecution.getElement2(compOperation, compDataType, umlModel, dataTypeClass, classOperation), userExecution.getElement3(compOperation, compDataType, umlModel, dataTypeClass, classOperation), userExecution.getTag1(compOperation, compDataType, umlModel, dataTypeClass, classOperation));
    
    postprocessElements();
  }
}
