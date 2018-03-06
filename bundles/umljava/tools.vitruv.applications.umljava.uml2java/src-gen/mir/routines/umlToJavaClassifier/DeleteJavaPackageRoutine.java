package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaPackageRoutine extends AbstractRepairRoutineRealization {
  private DeleteJavaPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJPackage(final org.eclipse.uml2.uml.Package uPackage) {
      return uPackage;
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package uPackage, final org.emftext.language.java.containers.Package jPackage) {
      return jPackage;
    }
  }
  
  public DeleteJavaPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package uPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.DeleteJavaPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.uPackage = uPackage;
  }
  
  private org.eclipse.uml2.uml.Package uPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaPackageRoutine with input:");
    getLogger().debug("   uPackage: " + this.uPackage);
    
    org.emftext.language.java.containers.Package jPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJPackage(uPackage), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jPackage == null) {
    	return false;
    }
    registerObjectUnderModification(jPackage);
    deleteObject(userExecution.getElement1(uPackage, jPackage));
    
    postprocessElements();
    
    return true;
  }
}
