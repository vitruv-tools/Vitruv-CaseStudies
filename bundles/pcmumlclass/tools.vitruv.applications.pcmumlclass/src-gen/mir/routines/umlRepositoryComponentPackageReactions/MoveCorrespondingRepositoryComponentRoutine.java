package mir.routines.umlRepositoryComponentPackageReactions;

import java.io.IOException;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingRepositoryComponentRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingRepositoryComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository, final RepositoryComponent pcmComponent) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
      return umlParentPkg;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository, final RepositoryComponent pcmComponent) {
      EList<RepositoryComponent> _components__Repository = pcmRepository.getComponents__Repository();
      _components__Repository.add(pcmComponent);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      return umlPkg;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_COMPONENT__PACKAGE;
    }
  }
  
  public MoveCorrespondingRepositoryComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.MoveCorrespondingRepositoryComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingRepositoryComponentRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlPkg, umlParentPkg), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlPkg, umlParentPkg), 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    org.palladiosimulator.pcm.repository.RepositoryComponent pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlPkg, umlParentPkg, pcmRepository), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    	(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlPkg, umlParentPkg, pcmRepository), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    // val updatedElement userExecution.getElement1(umlPkg, umlParentPkg, pcmRepository, pcmComponent);
    userExecution.update0Element(umlPkg, umlParentPkg, pcmRepository, pcmComponent);
    
    postprocessElements();
    
    return true;
  }
}
