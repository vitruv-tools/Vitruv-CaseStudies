package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nCreates datatypes and contracts package into given javaPackage after it was created.
 *  
 */
@SuppressWarnings("all")
public class CreateJavaSubPackagesRoutine extends AbstractRepairRoutineRealization {
  private CreateJavaSubPackagesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRepository(final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public void callRoutine1(final org.emftext.language.java.containers.Package javaPackage, final Repository repository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaPackage(repository, javaPackage, "datatypes", "datatypes");
      _routinesFacade.createJavaPackage(repository, javaPackage, "contracts", "contracts");
    }
  }
  
  public CreateJavaSubPackagesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateJavaSubPackagesRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaSubPackagesRoutine with input:");
    getLogger().debug("   javaPackage: " + this.javaPackage);
    
    org.palladiosimulator.pcm.repository.Repository repository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepository(javaPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (repository == null) {
    	return false;
    }
    registerObjectUnderModification(repository);
    userExecution.callRoutine1(javaPackage, repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
