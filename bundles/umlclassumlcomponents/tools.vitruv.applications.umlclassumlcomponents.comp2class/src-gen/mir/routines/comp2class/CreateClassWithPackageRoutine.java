package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateClassWithPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateClassWithPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateUmlClassElement(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      umlClass.setName(umlComp.getName());
      umlClass.setPackage(classPackage);
    }
    
    public EObject getElement1(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return classPackage;
    }
    
    public void update0Element(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      EList<PackageableElement> _packagedElements = classPackage.getPackagedElements();
      _packagedElements.add(umlClass);
    }
    
    public EObject getElement4(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlClass;
    }
    
    public EObject getElement5(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComp;
    }
    
    public EObject getElement2(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return classModel;
    }
    
    public EObject getElement3(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComp;
    }
    
    public EObject getElement6(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      return classPackage;
    }
    
    public void update1Element(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage, final org.eclipse.uml2.uml.Class umlClass) {
      EList<PackageableElement> _packagedElements = classModel.getPackagedElements();
      _packagedElements.add(classPackage);
    }
    
    public EObject getCorrepondenceSourceClassModel(final Component umlComp) {
      Model _model = umlComp.getModel();
      return _model;
    }
    
    public void updateClassPackageElement(final Component umlComp, final Model classModel, final org.eclipse.uml2.uml.Package classPackage) {
      String _name = umlComp.getName();
      String _plus = (_name + SharedUtil.PACKAGE_SUFFIX);
      classPackage.setName(_plus);
    }
  }
  
  public CreateClassWithPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.CreateClassWithPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComp = umlComp;
  }
  
  private Component umlComp;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateClassWithPackageRoutine with input:");
    getLogger().debug("   umlComp: " + this.umlComp);
    
    org.eclipse.uml2.uml.Model classModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassModel(umlComp), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (classModel == null) {
    	return false;
    }
    registerObjectUnderModification(classModel);
    org.eclipse.uml2.uml.Package classPackage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createPackage();
    notifyObjectCreated(classPackage);
    userExecution.updateClassPackageElement(umlComp, classModel, classPackage);
    
    org.eclipse.uml2.uml.Class umlClass = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createClass();
    notifyObjectCreated(umlClass);
    userExecution.updateUmlClassElement(umlComp, classModel, classPackage, umlClass);
    
    // val updatedElement userExecution.getElement1(umlComp, classModel, classPackage, umlClass);
    userExecution.update0Element(umlComp, classModel, classPackage, umlClass);
    
    // val updatedElement userExecution.getElement2(umlComp, classModel, classPackage, umlClass);
    userExecution.update1Element(umlComp, classModel, classPackage, umlClass);
    
    addCorrespondenceBetween(userExecution.getElement3(umlComp, classModel, classPackage, umlClass), userExecution.getElement4(umlComp, classModel, classPackage, umlClass), "");
    
    addCorrespondenceBetween(userExecution.getElement5(umlComp, classModel, classPackage, umlClass), userExecution.getElement6(umlComp, classModel, classPackage, umlClass), "");
    
    postprocessElements();
    
    return true;
  }
}
