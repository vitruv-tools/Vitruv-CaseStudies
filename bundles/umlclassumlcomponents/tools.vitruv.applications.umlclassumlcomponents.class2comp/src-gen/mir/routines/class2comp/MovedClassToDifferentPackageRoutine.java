package mir.routines.class2comp;

import java.io.IOException;
import java.util.Optional;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Component;
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
    
    public EObject getCorrepondenceSourcePackageNewComp(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Optional<Component> packageOldComp) {
      return newPackage;
    }
    
    public EObject getCorrepondenceSourceCompLinked(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Optional<Component> packageOldComp, final Optional<Component> packageNewComp) {
      return umlClass;
    }
    
    public EObject getCorrepondenceSourcePackageOldComp(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage) {
      return oldPackage;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package oldPackage, final org.eclipse.uml2.uml.Package newPackage, final Optional<Component> packageOldComp, final Optional<Component> packageNewComp, final Optional<Component> compLinked, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = packageOldComp.isPresent();
      if (_isPresent) {
        Component _get = packageOldComp.get();
        _routinesFacade.removeCorrespondence(((Classifier) umlClass), ((Classifier) _get));
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MovedClassToDifferentPackageRoutine with input:");
    getLogger().debug("   umlClass: " + this.umlClass);
    getLogger().debug("   oldPackage: " + this.oldPackage);
    getLogger().debug("   newPackage: " + this.newPackage);
    
    	Optional<org.eclipse.uml2.uml.Component> packageOldComp = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePackageOldComp(umlClass, oldPackage, newPackage), // correspondence source supplier
    		org.eclipse.uml2.uml.Component.class,
    		(org.eclipse.uml2.uml.Component _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(packageOldComp.isPresent() ? packageOldComp.get() : null);
    	Optional<org.eclipse.uml2.uml.Component> packageNewComp = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePackageNewComp(umlClass, oldPackage, newPackage, packageOldComp), // correspondence source supplier
    		org.eclipse.uml2.uml.Component.class,
    		(org.eclipse.uml2.uml.Component _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(packageNewComp.isPresent() ? packageNewComp.get() : null);
    	Optional<org.eclipse.uml2.uml.Component> compLinked = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceCompLinked(umlClass, oldPackage, newPackage, packageOldComp, packageNewComp), // correspondence source supplier
    		org.eclipse.uml2.uml.Component.class,
    		(org.eclipse.uml2.uml.Component _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(compLinked.isPresent() ? compLinked.get() : null);
    userExecution.callRoutine1(umlClass, oldPackage, newPackage, packageOldComp, packageNewComp, compLinked, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
