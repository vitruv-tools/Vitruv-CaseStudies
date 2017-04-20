package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MovedClassToDifferentPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private MovedClassToDifferentPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlCompOld(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel) {
      return oldPackage;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component umlCompOld, final Component umlCompNew) {
      return umlClass;
    }
    
    public EObject getElement4(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component umlCompOld, final Component umlCompNew) {
      return newPackage;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component umlCompOld, final Component umlCompNew) {
      return oldPackage;
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
      Model _model = umlClass.getModel();
      return _model;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component umlCompOld, final Component umlCompNew) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourceUmlCompNew(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component umlCompOld) {
      return newPackage;
    }
    
    public EObject getCorrepondenceSourcenull(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component umlCompOld, final Component umlCompNew) {
      return umlClass;
    }
  }
  
  public MovedClassToDifferentPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.MovedClassToDifferentPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.umlClass = umlClass;this.oldPackage = oldPackage;this.newPackage = newPackage;
  }
  
  private org.eclipse.uml2.uml.Class umlClass;
  
  private org.eclipse.uml2.uml.Package oldPackage;
  
  private org.eclipse.uml2.uml.Package newPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine MovedClassToDifferentPackageRoutine with input:");
    getLogger().debug("   Class: " + this.umlClass);
    getLogger().debug("   Package: " + this.oldPackage);
    getLogger().debug("   Package: " + this.newPackage);
    
    Model compModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompModel(umlClass, oldPackage, newPackage), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (compModel == null) {
    	return;
    }
    registerObjectUnderModification(compModel);
    Component umlCompOld = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompOld(umlClass, oldPackage, newPackage, compModel), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    if (umlCompOld == null) {
    	return;
    }
    registerObjectUnderModification(umlCompOld);
    Component umlCompNew = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlCompNew(umlClass, oldPackage, newPackage, compModel, umlCompOld), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    if (umlCompNew == null) {
    	return;
    }
    registerObjectUnderModification(umlCompNew);
    if (getCorrespondingElement(
    	userExecution.getCorrepondenceSourcenull(umlClass, oldPackage, newPackage, compModel, umlCompOld, umlCompNew), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null) != null) {
    	return;
    }
    removeCorrespondenceBetween(userExecution.getElement1(umlClass, oldPackage, newPackage, compModel, umlCompOld, umlCompNew), userExecution.getElement2(umlClass, oldPackage, newPackage, compModel, umlCompOld, umlCompNew));
    
    addCorrespondenceBetween(userExecution.getElement3(umlClass, oldPackage, newPackage, compModel, umlCompOld, umlCompNew), userExecution.getElement4(umlClass, oldPackage, newPackage, compModel, umlCompOld, umlCompNew), "");
    
    postprocessElements();
  }
}
