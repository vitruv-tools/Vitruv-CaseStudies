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
public class CreateUmlClassWithPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlClassWithPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateUmlClassElement(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      umlClass.setName(umlComponent.getName());
      umlClass.setPackage(classPackage);
    }
    
    public EObject getElement1(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return classPackage;
    }
    
    public void update0Element(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      EList<PackageableElement> _packagedElements = classPackage.getPackagedElements();
      _packagedElements.add(umlClass);
    }
    
    public EObject getElement4(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public EObject getElement5(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComponent;
    }
    
    public EObject getElement2(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return classModel;
    }
    
    public EObject getElement3(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComponent;
    }
    
    public EObject getElement6(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return classPackage;
    }
    
    public void update1Element(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      EList<PackageableElement> _packagedElements = classModel.getPackagedElements();
      _packagedElements.add(classPackage);
    }
    
    public EObject getCorrepondenceSourceClassModel(final Component umlComponent) {
      Model _model = umlComponent.getModel();
      return _model;
    }
    
    public void updateClassPackageElement(final Component umlComponent, final Model classModel, final org.eclipse.uml2.uml.Package classPackage) {
      String _name = umlComponent.getName();
      String _plus = (_name + " Package");
      classPackage.setName(_plus);
    }
  }
  
  public CreateUmlClassWithPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateUmlClassWithPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComponent = umlComponent;
  }
  
  private Component umlComponent;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlClassWithPackageRoutine with input:");
    getLogger().debug("   Component: " + this.umlComponent);
    
    Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(umlComponent), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (classModel == null) {
    	return;
    }
    registerObjectUnderModification(classModel);
    org.eclipse.uml2.uml.Package classPackage = UMLFactoryImpl.eINSTANCE.createPackage();
    userExecution.updateClassPackageElement(umlComponent, classModel, classPackage);
    
    org.eclipse.uml2.uml.Class umlClass = UMLFactoryImpl.eINSTANCE.createClass();
    userExecution.updateUmlClassElement(umlComponent, classModel, classPackage, umlClass);
    
    // val updatedElement userExecution.getElement1(umlComponent, classModel, classPackage, umlClass);
    userExecution.update0Element(umlComponent, classModel, classPackage, umlClass);
    
    // val updatedElement userExecution.getElement2(umlComponent, classModel, classPackage, umlClass);
    userExecution.update1Element(umlComponent, classModel, classPackage, umlClass);
    
    addCorrespondenceBetween(userExecution.getElement3(umlComponent, classModel, classPackage, umlClass), userExecution.getElement4(umlComponent, classModel, classPackage, umlClass), "");
    
    addCorrespondenceBetween(userExecution.getElement5(umlComponent, classModel, classPackage, umlClass), userExecution.getElement6(umlComponent, classModel, classPackage, umlClass), "");
    
    postprocessElements();
  }
}
