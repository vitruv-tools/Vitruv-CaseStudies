package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType classType, final Model compModel, final DataType compType) {
      return compModel;
    }
    
    public void update0Element(final DataType classType, final Model compModel, final DataType compType) {
      EList<Type> _ownedTypes = compModel.getOwnedTypes();
      _ownedTypes.add(compType);
    }
    
    public EObject getElement2(final DataType classType, final Model compModel, final DataType compType) {
      return classType;
    }
    
    public EObject getCorrepondenceSourceCompModel(final DataType classType) {
      Model _model = classType.getModel();
      return _model;
    }
    
    public void updateCompTypeElement(final DataType classType, final Model compModel, final DataType compType) {
      String _name = classType.getName();
      compType.setName(_name);
    }
    
    public EObject getElement3(final DataType classType, final Model compModel, final DataType compType) {
      return compType;
    }
  }
  
  public CreateDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType classType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classType = classType;
  }
  
  private DataType classType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateDataTypeRoutine with input:");
    getLogger().debug("   DataType: " + this.classType);
    
    Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(classType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (compModel == null) {
    	return;
    }
    registerObjectUnderModification(compModel);
    DataType compType = UMLFactoryImpl.eINSTANCE.createDataType();
    userExecution.updateCompTypeElement(classType, compModel, compType);
    
    // val updatedElement userExecution.getElement1(classType, compModel, compType);
    userExecution.update0Element(classType, compModel, compType);
    
    addCorrespondenceBetween(userExecution.getElement2(classType, compModel, compType), userExecution.getElement3(classType, compModel, compType), "");
    
    postprocessElements();
  }
}
