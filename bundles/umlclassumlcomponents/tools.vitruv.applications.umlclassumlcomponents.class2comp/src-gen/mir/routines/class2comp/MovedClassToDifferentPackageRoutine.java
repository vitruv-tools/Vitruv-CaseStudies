package mir.routines.class2comp;

import java.io.IOException;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public EObject getCorrepondenceSourcePackageNewComp(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component packageOldComp) {
      return newPackage;
    }
    
    public EObject getCorrepondenceSourceCompModel(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
      Model _model = umlClass.getModel();
      return _model;
    }
    
    public EObject getCorrepondenceSourceCompLinked(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component packageOldComp, final Component packageNewComp) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourcePackageOldComp(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel) {
      return oldPackage;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Model compModel, final Component packageOldComp, final Component packageNewComp, final Component compLinked, @Extension final RoutinesFacade _routinesFacade) {
      if ((packageOldComp != null)) {
        _routinesFacade.removeCorrespondence(((Classifier) umlClass), ((Classifier) packageOldComp));
      }
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
    Component packageOldComp = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePackageOldComp(umlClass, oldPackage, newPackage, compModel), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(packageOldComp);
    Component packageNewComp = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePackageNewComp(umlClass, oldPackage, newPackage, compModel, packageOldComp), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(packageNewComp);
    Component compLinked = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompLinked(umlClass, oldPackage, newPackage, compModel, packageOldComp, packageNewComp), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(compLinked);
    userExecution.callRoutine1(umlClass, oldPackage, newPackage, compModel, packageOldComp, packageNewComp, compLinked, actionsFacade);
    
    postprocessElements();
  }
}
