package mir.routines.umlRepositoryComponentPackageReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRepositoryComponentPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingRepositoryComponentRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingRepositoryComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
      return umlParentPkg;
    }
    
    public EObject getCorrepondenceSource1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Optional<Repository> pcmRepository) {
      return umlPkg;
    }
    
    public EObject getCorrepondenceSource2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Optional<Repository> pcmRepository) {
      return umlPkg;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Optional<Repository> pcmRepository) {
      return TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE;
    }
    
    public String getRetrieveTag3(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Optional<Repository> pcmRepository) {
      return TagLiterals.REPOSITORY_TO_DATATYPES_PACKAGE;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg, final Optional<Repository> pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmRepository.isPresent();
      if (_isPresent) {
        _routinesFacade.detectOrCreateCorrespondingRepositoryComponent(umlPkg, umlParentPkg);
        _routinesFacade.moveCorrespondingRepositoryComponent(umlPkg, umlParentPkg);
      } else {
        _routinesFacade.deleteCorrespondingRepositoryComponent(umlPkg);
      }
    }
  }
  
  public InsertCorrespondingRepositoryComponentRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryComponentPackageReactions.InsertCorrespondingRepositoryComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPkg = umlPkg;this.umlParentPkg = umlParentPkg;
  }
  
  private org.eclipse.uml2.uml.Package umlPkg;
  
  private org.eclipse.uml2.uml.Package umlParentPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingRepositoryComponentRoutine with input:");
    getLogger().debug("   umlPkg: " + this.umlPkg);
    getLogger().debug("   umlParentPkg: " + this.umlParentPkg);
    
    	Optional<org.palladiosimulator.pcm.repository.Repository> pcmRepository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRepository(umlPkg, umlParentPkg), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlPkg, umlParentPkg), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRepository.isPresent() ? pcmRepository.get() : null);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlPkg, umlParentPkg, pcmRepository), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlPkg, umlParentPkg, pcmRepository)
    ).isEmpty()) {
    	return false;
    }
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource2(umlPkg, umlParentPkg, pcmRepository), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlPkg, umlParentPkg, pcmRepository)
    ).isEmpty()) {
    	return false;
    }
    userExecution.callRoutine1(umlPkg, umlParentPkg, pcmRepository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
