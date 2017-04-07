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
public class CreateUmlComponentAndPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlComponentAndPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      return compModel;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      EList<PackageableElement> _packagedElements = compModel.getPackagedElements();
      _packagedElements.add(umlComponent);
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Class umlClass) {
      Model _model = umlClass.getModel();
      return _model;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      return umlComponent;
    }
    
    public void updateUmlComponentElement(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final org.eclipse.uml2.uml.Package classPackage, final Component umlComponent) {
      umlComponent.setName(umlClass.getName());
      umlComponent.setPackage(classPackage);
    }
    
    public void updateClassPackageElement(final org.eclipse.uml2.uml.Class umlClass, final Model compModel, final org.eclipse.uml2.uml.Package classPackage) {
      String _name = umlClass.getName();
      String _plus = (_name + "-Package");
      classPackage.setName(_plus);
    }
  }
  
  public CreateUmlComponentAndPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.CreateUmlComponentAndPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlComponentAndPackageRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    
    Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClass), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (compModel == null) {
    	return;
    }
    registerObjectUnderModification(compModel);
    org.eclipse.uml2.uml.Package classPackage = UMLFactoryImpl.eINSTANCE.createPackage();
    userExecution.updateClassPackageElement(umlClass, compModel, classPackage);
    
    Component umlComponent = UMLFactoryImpl.eINSTANCE.createComponent();
    userExecution.updateUmlComponentElement(umlClass, compModel, classPackage, umlComponent);
    
    // val updatedElement userExecution.getElement1(umlClass, compModel, classPackage, umlComponent);
    userExecution.update0Element(umlClass, compModel, classPackage, umlComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(umlClass, compModel, classPackage, umlComponent), userExecution.getElement3(umlClass, compModel, classPackage, umlComponent), "");
    
    postprocessElements();
  }
}
