package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.PackageableElement;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingComponentPackageRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingComponentPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      return umlRepositoryPackage;
    }
    
    public void update0Element(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage, final org.eclipse.uml2.uml.Package umlComponentPackage) {
      EList<PackageableElement> _packagedElements = umlRepositoryPackage.getPackagedElements();
      _packagedElements.add(umlComponentPackage);
    }
    
    public EObject getCorrepondenceSourceUmlComponentPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage) {
      return pcmComponent;
    }
    
    public String getRetrieveTag1(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public EObject getCorrepondenceSourceUmlRepositoryPackage(final RepositoryComponent pcmComponent, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public String getRetrieveTag2(final RepositoryComponent pcmComponent, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlRepositoryPackage) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public MoveCorrespondingComponentPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.MoveCorrespondingComponentPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.pcmRepository = pcmRepository;
  }
  
  private RepositoryComponent pcmComponent;
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingComponentPackageRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    org.eclipse.uml2.uml.Package umlRepositoryPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRepositoryPackage(pcmComponent, pcmRepository), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmComponent, pcmRepository), 
    	false // asserted
    	);
    if (umlRepositoryPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlRepositoryPackage);
    org.eclipse.uml2.uml.Package umlComponentPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentPackage(pcmComponent, pcmRepository, umlRepositoryPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmComponent, pcmRepository, umlRepositoryPackage), 
    	false // asserted
    	);
    if (umlComponentPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentPackage);
    // val updatedElement userExecution.getElement1(pcmComponent, pcmRepository, umlRepositoryPackage, umlComponentPackage);
    userExecution.update0Element(pcmComponent, pcmRepository, umlRepositoryPackage, umlComponentPackage);
    
    postprocessElements();
    
    return true;
  }
}
