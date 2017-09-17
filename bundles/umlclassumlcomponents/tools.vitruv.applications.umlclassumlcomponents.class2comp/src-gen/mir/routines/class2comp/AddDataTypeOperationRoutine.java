package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
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
    
    public EObject getCorrepondenceSourceCompDataType(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return dataTypeClass;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation, final DataType compDataType, final Operation compOperation) {
      return compDataType;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation, final DataType compDataType, final Operation compOperation) {
      EList<Operation> _ownedOperations = compDataType.getOwnedOperations();
      _ownedOperations.add(compOperation);
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
      return SharedUtil.DATA_TYPE_REPRESENTATION_TAG;
    }
    
    public void updateCompOperationElement(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation, final DataType compDataType, final Operation compOperation) {
      compOperation.setName(classOperation.getName());
      compOperation.setDatatype(classOperation.getDatatype());
      compOperation.setVisibility(classOperation.getVisibility());
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation, final DataType compDataType, final Operation compOperation) {
      return classOperation;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation, final DataType compDataType, final Operation compOperation) {
      return compOperation;
    }
  }
  
  public AddDataTypeOperationRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class dataTypeClass, final Operation classOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.AddDataTypeOperationRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.dataTypeClass = dataTypeClass;this.classOperation = classOperation;
  }
  
  private org.eclipse.uml2.uml.Class dataTypeClass;
  
  private Operation classOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddDataTypeOperationRoutine with input:");
    getLogger().debug("   dataTypeClass: " + this.dataTypeClass);
    getLogger().debug("   classOperation: " + this.classOperation);
    
    org.eclipse.uml2.uml.DataType compDataType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompDataType(dataTypeClass, classOperation), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(dataTypeClass, classOperation), 
    	false // asserted
    	);
    if (compDataType == null) {
    	return false;
    }
    registerObjectUnderModification(compDataType);
    org.eclipse.uml2.uml.Operation compOperation = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createOperation();
    notifyObjectCreated(compOperation);
    userExecution.updateCompOperationElement(dataTypeClass, classOperation, compDataType, compOperation);
    
    // val updatedElement userExecution.getElement1(dataTypeClass, classOperation, compDataType, compOperation);
    userExecution.update0Element(dataTypeClass, classOperation, compDataType, compOperation);
    
    addCorrespondenceBetween(userExecution.getElement2(dataTypeClass, classOperation, compDataType, compOperation), userExecution.getElement3(dataTypeClass, classOperation, compDataType, compOperation), "");
    
    postprocessElements();
    
    return true;
  }
}
