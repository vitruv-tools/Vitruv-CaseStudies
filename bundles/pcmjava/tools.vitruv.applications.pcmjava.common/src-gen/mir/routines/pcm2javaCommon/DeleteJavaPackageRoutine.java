package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
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
    
    public EObject getElement1(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public String getRetrieveTag1(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
      return expectedTag;
    }
    
    public EObject getCorrepondenceSourceJavaPackage(final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
      return sourceElementMappedToPackage;
    }
  }
  
  public DeleteJavaPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToPackage, final String packageName, final String expectedTag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.DeleteJavaPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.sourceElementMappedToPackage = sourceElementMappedToPackage;this.packageName = packageName;this.expectedTag = expectedTag;
  }
  
  private NamedElement sourceElementMappedToPackage;
  
  private String packageName;
  
  private String expectedTag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaPackageRoutine with input:");
    getLogger().debug("   sourceElementMappedToPackage: " + this.sourceElementMappedToPackage);
    getLogger().debug("   packageName: " + this.packageName);
    getLogger().debug("   expectedTag: " + this.expectedTag);
    
    org.emftext.language.java.containers.Package javaPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, packageName, expectedTag), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(sourceElementMappedToPackage, packageName, expectedTag), 
    	false // asserted
    	);
    if (javaPackage == null) {
    	return false;
    }
    registerObjectUnderModification(javaPackage);
    deleteObject(userExecution.getElement1(sourceElementMappedToPackage, packageName, expectedTag, javaPackage));
    
    postprocessElements();
    
    return true;
  }
}
