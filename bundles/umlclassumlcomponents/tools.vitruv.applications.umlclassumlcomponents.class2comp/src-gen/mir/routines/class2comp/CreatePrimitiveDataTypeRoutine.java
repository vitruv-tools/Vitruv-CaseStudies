package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePrimitiveDataTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePrimitiveDataTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final PrimitiveType classType, final Model umlModel, final PrimitiveType compType) {
      return umlModel;
    }
    
    public void update0Element(final PrimitiveType classType, final Model umlModel, final PrimitiveType compType) {
      EList<Type> _ownedTypes = umlModel.getOwnedTypes();
      _ownedTypes.add(compType);
    }
    
    public EObject getElement2(final PrimitiveType classType, final Model umlModel, final PrimitiveType compType) {
      return compType;
    }
    
    public void updateCompTypeElement(final PrimitiveType classType, final Model umlModel, final PrimitiveType compType) {
      String _name = classType.getName();
      compType.setName(_name);
    }
    
    public EObject getElement3(final PrimitiveType classType, final Model umlModel, final PrimitiveType compType) {
      return classType;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final PrimitiveType classType) {
      Model _model = classType.getModel();
      return _model;
    }
  }
  
  public CreatePrimitiveDataTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveType classType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreatePrimitiveDataTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classType = classType;
  }
  
  private PrimitiveType classType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePrimitiveDataTypeRoutine with input:");
    getLogger().debug("   PrimitiveType: " + this.classType);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(classType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    registerObjectUnderModification(umlModel);
    PrimitiveType compType = UMLFactoryImpl.eINSTANCE.createPrimitiveType();
    userExecution.updateCompTypeElement(classType, umlModel, compType);
    
    // val updatedElement userExecution.getElement1(classType, umlModel, compType);
    userExecution.update0Element(classType, umlModel, compType);
    
    addCorrespondenceBetween(userExecution.getElement2(classType, umlModel, compType), userExecution.getElement3(classType, umlModel, compType), "");
    
    postprocessElements();
  }
}
