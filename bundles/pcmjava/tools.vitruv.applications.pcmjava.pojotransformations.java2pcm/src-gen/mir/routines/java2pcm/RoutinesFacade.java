package mir.routines.java2pcm;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createPCMRepository(final EObject sourceElementMappedToRepository, final String packageName, final String newTag) {
    mir.routines.java2pcm.CreatePCMRepositoryRoutine effect = new mir.routines.java2pcm.CreatePCMRepositoryRoutine(this.executionState, calledBy,
    	sourceElementMappedToRepository, packageName, newTag);
    effect.applyRoutine();
  }
  
  public void createJavaSubPackages(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.java2pcm.CreateJavaSubPackagesRoutine effect = new mir.routines.java2pcm.CreateJavaSubPackagesRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void createJavaPackage(final EObject sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String newTag) {
    mir.routines.java2pcm.CreateJavaPackageRoutine effect = new mir.routines.java2pcm.CreateJavaPackageRoutine(this.executionState, calledBy,
    	sourceElementMappedToPackage, parentPackage, packageName, newTag);
    effect.applyRoutine();
  }
}
