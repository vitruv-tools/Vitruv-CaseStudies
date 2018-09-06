package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddUmlElementToPackageRoutine extends AbstractRepairRoutineRealization {
  private AddUmlElementToPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public void update0Element(final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage) {
      EList<PackageableElement> _packagedElements = uPackage.getPackagedElements();
      _packagedElements.add(uPackageable);
    }
  }
  
  public AddUmlElementToPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PackageableElement uPackageable, final org.eclipse.uml2.uml.Package uPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddUmlElementToPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.uPackageable = uPackageable;this.uPackage = uPackage;
  }
  
  private PackageableElement uPackageable;
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddUmlElementToPackageRoutine with input:");
    getLogger().debug("   uPackageable: " + this.uPackageable);
    getLogger().debug("   uPackage: " + this.uPackage);
    
    // val updatedElement userExecution.getElement1(uPackageable, uPackage);
    userExecution.update0Element(uPackageable, uPackage);
    
    postprocessElements();
    
    return true;
  }
}
