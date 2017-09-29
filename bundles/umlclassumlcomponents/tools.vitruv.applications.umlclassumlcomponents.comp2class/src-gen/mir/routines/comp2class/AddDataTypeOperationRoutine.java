package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Operation;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
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
    
    public EObject getElement1(final Operation compOperation, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return dataTypeClass;
    }
    
    public void update0Element(final Operation compOperation, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      EList<Operation> _ownedOperations = dataTypeClass.getOwnedOperations();
      _ownedOperations.add(classOperation);
    }
    
    public String getRetrieveTag1(final Operation compOperation, final DataType compDataType) {
      return SharedUtil.DATA_TYPE_REPRESENTATION_TAG;
    }
    
    public EObject getElement2(final Operation compOperation, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return classOperation;
    }
    
    public EObject getCorrepondenceSourceDataTypeClass(final Operation compOperation, final DataType compDataType) {
      return compDataType;
    }
    
    public EObject getElement3(final Operation compOperation, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return compOperation;
    }
    
    public String getTag1(final Operation compOperation, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return SharedUtil.DATA_TYPE_OPERATION_TAG;
    }
    
    public void updateClassOperationElement(final Operation compOperation, final DataType compDataType, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      classOperation.setName(compOperation.getName());
      classOperation.setType(compOperation.getType());
      classOperation.setVisibility(compOperation.getVisibility());
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddDataTypeOperationRoutine with input:");
    getLogger().debug("   compOperation: " + this.compOperation);
    getLogger().debug("   compDataType: " + this.compDataType);
    
    org.eclipse.uml2.uml.Class dataTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceDataTypeClass(compOperation, compDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(compOperation, compDataType), 
    	false // asserted
    	);
    if (dataTypeClass == null) {
    	return false;
    }
    registerObjectUnderModification(dataTypeClass);
    org.eclipse.uml2.uml.Operation classOperation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(classOperation);
    userExecution.updateClassOperationElement(compOperation, compDataType, dataTypeClass, classOperation);
    
    // val updatedElement userExecution.getElement1(compOperation, compDataType, dataTypeClass, classOperation);
    userExecution.update0Element(compOperation, compDataType, dataTypeClass, classOperation);
    
    addCorrespondenceBetween(userExecution.getElement2(compOperation, compDataType, dataTypeClass, classOperation), userExecution.getElement3(compOperation, compDataType, dataTypeClass, classOperation), userExecution.getTag1(compOperation, compDataType, dataTypeClass, classOperation));
    
    postprocessElements();
    
    return true;
  }
}
