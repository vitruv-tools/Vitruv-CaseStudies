package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
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
public class CreateUmlComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Model umlModel, final Component umlComponent) {
      return umlModel;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Model umlModel, final Component umlComponent) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(umlComponent);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Model umlModel, final Component umlComponent) {
      return umlClass;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Model umlModel, final Component umlComponent) {
      return umlComponent;
    }
    
    public void updateUmlComponentElement(final org.eclipse.uml2.uml.Class umlClass, final Model umlModel, final Component umlComponent) {
      umlComponent.setName(umlClass.getName());
    }
    
    public EObject getCorrepondenceSourceUmlModel(final org.eclipse.uml2.uml.Class umlClass) {
      org.eclipse.uml2.uml.Package _package = umlClass.getPackage();
      return _package;
    }
  }
  
  public CreateUmlComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateUmlComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlComponentRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(umlClass), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    registerObjectUnderModification(umlModel);
    Component umlComponent = UMLFactoryImpl.eINSTANCE.createComponent();
    userExecution.updateUmlComponentElement(umlClass, umlModel, umlComponent);
    
    // val updatedElement userExecution.getElement1(umlClass, umlModel, umlComponent);
    userExecution.update0Element(umlClass, umlModel, umlComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, umlModel, umlComponent), userExecution.getElement3(umlClass, umlModel, umlComponent), "");
    
    postprocessElements();
  }
}
