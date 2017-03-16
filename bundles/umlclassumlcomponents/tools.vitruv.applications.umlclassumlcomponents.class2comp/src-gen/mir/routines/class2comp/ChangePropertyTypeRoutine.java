package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangePropertyTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangePropertyTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property classProperty, final DataType classType, final Property compProperty, final DataType compType, final Model compModel) {
      return compProperty;
    }
    
    public void update0Element(final Property classProperty, final DataType classType, final Property compProperty, final DataType compType, final Model compModel) {
      compProperty.setType(compType);
    }
    
    public EObject getCorrepondenceSourceCompModel(final Property classProperty, final DataType classType, final Property compProperty, final DataType compType) {
      Model _model = classType.getModel();
      return _model;
    }
    
    public EObject getCorrepondenceSourceCompType(final Property classProperty, final DataType classType, final Property compProperty) {
      return classType;
    }
    
    public EObject getCorrepondenceSourceCompProperty(final Property classProperty, final DataType classType) {
      return classProperty;
    }
  }
  
  public ChangePropertyTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property classProperty, final DataType classType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.ChangePropertyTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classProperty = classProperty;this.classType = classType;
  }
  
  private Property classProperty;
  
  private DataType classType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangePropertyTypeRoutine with input:");
    getLogger().debug("   Property: " + this.classProperty);
    getLogger().debug("   DataType: " + this.classType);
    
    Property compProperty = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompProperty(classProperty, classType), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (compProperty == null) {
    	return;
    }
    registerObjectUnderModification(compProperty);
    DataType compType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompType(classProperty, classType, compProperty), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (compType == null) {
    	return;
    }
    registerObjectUnderModification(compType);
    Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(classProperty, classType, compProperty, compType), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (compModel == null) {
    	return;
    }
    registerObjectUnderModification(compModel);
    // val updatedElement userExecution.getElement1(classProperty, classType, compProperty, compType, compModel);
    userExecution.update0Element(classProperty, classType, compProperty, compType, compModel);
    
    postprocessElements();
  }
}
