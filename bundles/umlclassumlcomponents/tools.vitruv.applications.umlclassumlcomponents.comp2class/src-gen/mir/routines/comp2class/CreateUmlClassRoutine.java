package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateUmlClassElement(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Class umlClass) {
      String _name = umlComponent.getName();
      umlClass.setName(_name);
    }
    
    public EObject getElement1(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Class umlClass) {
      return umlModel;
    }
    
    public void update0Element(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Class umlClass) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(umlClass);
    }
    
    public EObject getElement2(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComponent;
    }
    
    public EObject getElement3(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final Component umlComponent) {
      org.eclipse.uml2.uml.Package _package = umlComponent.getPackage();
      return _package;
    }
  }
  
  public CreateUmlClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateUmlClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComponent = umlComponent;
  }
  
  private Component umlComponent;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassRoutine with input:");
    getLogger().debug("   Component: " + this.umlComponent);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(umlComponent), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    registerObjectUnderModification(umlModel);
    org.eclipse.uml2.uml.Class umlClass = UMLFactoryImpl.eINSTANCE.createClass();
    userExecution.updateUmlClassElement(umlComponent, umlModel, umlClass);
    
    // val updatedElement userExecution.getElement1(umlComponent, umlModel, umlClass);
    userExecution.update0Element(umlComponent, umlModel, umlClass);
    
    addCorrespondenceBetween(userExecution.getElement2(umlComponent, umlModel, umlClass), userExecution.getElement3(umlComponent, umlModel, umlClass), "");
    
    postprocessElements();
  }
}
