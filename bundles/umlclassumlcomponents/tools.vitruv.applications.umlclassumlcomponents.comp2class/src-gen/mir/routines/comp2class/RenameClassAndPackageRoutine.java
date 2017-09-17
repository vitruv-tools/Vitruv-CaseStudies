package mir.routines.comp2class;

import java.io.IOException;
import java.util.Optional;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umlclassumlcomponents.sharedutil.SharedUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameClassAndPackageRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameClassAndPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComp, final String newName, final org.eclipse.uml2.uml.Class umlClass, final Optional<org.eclipse.uml2.uml.Package> classPackage) {
      return umlClass;
    }
    
    public void update0Element(final Component umlComp, final String newName, final org.eclipse.uml2.uml.Class umlClass, final Optional<org.eclipse.uml2.uml.Package> classPackage) {
      umlClass.setName(newName);
    }
    
    public String getRetrieveTag1(final Component umlComp, final String newName) {
      return "";
    }
    
    public EObject getCorrepondenceSourceClassPackage(final Component umlComp, final String newName, final org.eclipse.uml2.uml.Class umlClass) {
      return umlComp;
    }
    
    public EObject getCorrepondenceSourceUmlClass(final Component umlComp, final String newName) {
      return umlComp;
    }
    
    public void callRoutine1(final Component umlComp, final String newName, final org.eclipse.uml2.uml.Class umlClass, final Optional<org.eclipse.uml2.uml.Package> classPackage, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = classPackage.isPresent();
      if (_isPresent) {
        org.eclipse.uml2.uml.Package _get = classPackage.get();
        _get.setName((newName + SharedUtil.PACKAGE_SUFFIX));
      }
    }
  }
  
  public RenameClassAndPackageRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComp, final String newName) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.RenameClassAndPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.umlComp = umlComp;this.newName = newName;
  }
  
  private Component umlComp;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameClassAndPackageRoutine with input:");
    getLogger().debug("   umlComp: " + this.umlComp);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Class umlClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlClass(umlComp, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlComp, newName), 
    	false // asserted
    	);
    if (umlClass == null) {
    	return false;
    }
    registerObjectUnderModification(umlClass);
    	Optional<org.eclipse.uml2.uml.Package> classPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceClassPackage(umlComp, newName, umlClass), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(classPackage.isPresent() ? classPackage.get() : null);
    // val updatedElement userExecution.getElement1(umlComp, newName, umlClass, classPackage);
    userExecution.update0Element(umlComp, newName, umlClass, classPackage);
    
    userExecution.callRoutine1(umlComp, newName, umlClass, classPackage, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
