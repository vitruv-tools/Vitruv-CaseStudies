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
    
    public void updateUmlClassElement(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      umlClass.setName(umlComponent.getName());
      umlClass.setPackage(classPackage);
    }
    
    public EObject getElement1(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlModel;
    }
    
    public void update0Element(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      EList<PackageableElement> _packagedElements = umlModel.getPackagedElements();
      _packagedElements.add(classPackage);
    }
    
    public EObject getElement4(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public EObject getElement2(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return classPackage;
    }
    
    public EObject getElement3(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComponent;
    }
    
    public void update1Element(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      EList<PackageableElement> _packagedElements = classPackage.getPackagedElements();
      _packagedElements.add(umlClass);
    }
    
    public EObject getCorrepondenceSourceUmlModel(final Component umlComponent) {
      org.eclipse.uml2.uml.Package _package = umlComponent.getPackage();
      return _package;
    }
    
    public void updateClassPackageElement(final Component umlComponent, final Model umlModel, final org.eclipse.uml2.uml.Package classPackage) {
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
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(umlComponent), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    registerObjectUnderModification(umlModel);
    org.eclipse.uml2.uml.Package classPackage = UMLFactoryImpl.eINSTANCE.createPackage();
    userExecution.updateClassPackageElement(umlComponent, umlModel, classPackage);
    
    org.eclipse.uml2.uml.Class umlClass = UMLFactoryImpl.eINSTANCE.createClass();
    userExecution.updateUmlClassElement(umlComponent, umlModel, classPackage, umlClass);
    
    // val updatedElement userExecution.getElement1(umlComponent, umlModel, classPackage, umlClass);
    userExecution.update0Element(umlComponent, umlModel, classPackage, umlClass);
    
    // val updatedElement userExecution.getElement2(umlComponent, umlModel, classPackage, umlClass);
    userExecution.update1Element(umlComponent, umlModel, classPackage, umlClass);
    
    addCorrespondenceBetween(userExecution.getElement3(umlComponent, umlModel, classPackage, umlClass), userExecution.getElement4(umlComponent, umlModel, classPackage, umlClass), "");
    
    postprocessElements();
  }
}
