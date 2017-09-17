package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDataTypeForDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeForDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType compType, final Model classModel, final DataType classType) {
      return classModel;
    }
    
    public void update0Element(final DataType compType, final Model classModel, final DataType classType) {
      EList<Type> _ownedTypes = classModel.getOwnedTypes();
      _ownedTypes.add(classType);
    }
    
    public EObject getElement2(final DataType compType, final Model classModel, final DataType classType) {
      return compType;
    }
    
    public void updateClassTypeElement(final DataType compType, final Model classModel, final DataType classType) {
      classType.setName(compType.getName());
    }
    
    public EObject getElement3(final DataType compType, final Model classModel, final DataType classType) {
      return classType;
    }
    
    public EObject getCorrepondenceSourceClassModel(final DataType compType) {
      Model _model = compType.getModel();
      return _model;
    }
  }
  
  public CreateDataTypeForDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType compType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateDataTypeForDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compType = compType;
  }
  
  private DataType compType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeForDataTypeRoutine with input:");
    getLogger().debug("   compType: " + this.compType);
    
    org.eclipse.uml2.uml.Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(compType), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (classModel == null) {
    	return false;
    }
    registerObjectUnderModification(classModel);
    org.eclipse.uml2.uml.DataType classType = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createDataType();
    notifyObjectCreated(classType);
    userExecution.updateClassTypeElement(compType, classModel, classType);
    
    // val updatedElement userExecution.getElement1(compType, classModel, classType);
    userExecution.update0Element(compType, classModel, classType);
    
    addCorrespondenceBetween(userExecution.getElement2(compType, classModel, classType), userExecution.getElement3(compType, classModel, classType), "");
    
    postprocessElements();
    
    return true;
  }
}
