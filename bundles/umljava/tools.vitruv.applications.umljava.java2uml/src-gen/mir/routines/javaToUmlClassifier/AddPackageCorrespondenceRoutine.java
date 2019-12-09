package mir.routines.javaToUmlClassifier;

import java.io.IOException;
import mir.routines.javaToUmlClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddPackageCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddPackageCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
      return jPackage;
    }
  }
  
  public AddPackageCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package jPackage, final org.eclipse.uml2.uml.Package uPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlClassifier.AddPackageCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.jPackage = jPackage;this.uPackage = uPackage;
  }
  
  private org.emftext.language.java.containers.Package jPackage;
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddPackageCorrespondenceRoutine with input:");
    getLogger().debug("   jPackage: " + this.jPackage);
    getLogger().debug("   uPackage: " + this.uPackage);
    
    addCorrespondenceBetween(userExecution.getElement1(jPackage, uPackage), userExecution.getElement2(jPackage, uPackage), "");
    
    postprocessElements();
    
    return true;
  }
}
