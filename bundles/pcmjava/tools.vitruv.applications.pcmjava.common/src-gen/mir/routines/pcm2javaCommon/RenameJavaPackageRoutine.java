package mir.routines.pcm2javaCommon;

import com.google.common.collect.Iterables;
import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.domains.java.util.JavaPersistenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaPackageRoutine extends AbstractRepairRoutineRealization {
  private RenameJavaPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public void update0Element(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag, final org.emftext.language.java.containers.Package javaPackage) {
      javaPackage.getNamespaces().clear();
      if ((parentPackage != null)) {
        EList<String> _namespaces = javaPackage.getNamespaces();
        EList<String> _namespaces_1 = parentPackage.getNamespaces();
        Iterables.<String>addAll(_namespaces, _namespaces_1);
        EList<String> _namespaces_2 = javaPackage.getNamespaces();
        String _name = parentPackage.getName();
        _namespaces_2.add(_name);
      }
      javaPackage.setName(packageName);
      this.persistProjectRelative(sourceElementMappedToPackage, javaPackage, JavaPersistenceHelper.buildJavaFilePath(javaPackage));
    }
    
    public String getRetrieveTag1(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
      return expectedTag;
    }
    
    public EObject getCorrepondenceSourceJavaPackage(final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
      return sourceElementMappedToPackage;
    }
  }
  
  public RenameJavaPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement sourceElementMappedToPackage, final org.emftext.language.java.containers.Package parentPackage, final String packageName, final String expectedTag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenameJavaPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.sourceElementMappedToPackage = sourceElementMappedToPackage;this.parentPackage = parentPackage;this.packageName = packageName;this.expectedTag = expectedTag;
  }
  
  private NamedElement sourceElementMappedToPackage;
  
  private org.emftext.language.java.containers.Package parentPackage;
  
  private String packageName;
  
  private String expectedTag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaPackageRoutine with input:");
    getLogger().debug("   sourceElementMappedToPackage: " + this.sourceElementMappedToPackage);
    getLogger().debug("   parentPackage: " + this.parentPackage);
    getLogger().debug("   packageName: " + this.packageName);
    getLogger().debug("   expectedTag: " + this.expectedTag);
    
    org.emftext.language.java.containers.Package javaPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaPackage(sourceElementMappedToPackage, parentPackage, packageName, expectedTag), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(sourceElementMappedToPackage, parentPackage, packageName, expectedTag), 
    	false // asserted
    	);
    if (javaPackage == null) {
    	return false;
    }
    registerObjectUnderModification(javaPackage);
    // val updatedElement userExecution.getElement1(sourceElementMappedToPackage, parentPackage, packageName, expectedTag, javaPackage);
    userExecution.update0Element(sourceElementMappedToPackage, parentPackage, packageName, expectedTag, javaPackage);
    
    postprocessElements();
    
    return true;
  }
}
