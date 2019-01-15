package mir.routines.umlRepositoryAndSystemPackageReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteCorrespondingRepositoryOrSystemRoutine extends AbstractRepairRoutineRealization {
  private DeleteCorrespondingRepositoryOrSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Package umlPackage) {
      return umlPackage;
    }
    
    public EObject getCorrepondenceSourcePcmSystem(final org.eclipse.uml2.uml.Package umlPackage, final Optional<Repository> pcmRepository) {
      return umlPackage;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Package umlPackage, final Optional<Repository> pcmRepository) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package umlPackage, final Optional<Repository> pcmRepository, final Optional<org.palladiosimulator.pcm.system.System> pcmSystem, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmRepository.isPresent();
      if (_isPresent) {
        _routinesFacade.deleteCorrespondingRepository(umlPackage);
      }
      boolean _isPresent_1 = pcmSystem.isPresent();
      if (_isPresent_1) {
        _routinesFacade.deleteCorrespondingSystem(umlPackage);
      }
    }
  }
  
  public DeleteCorrespondingRepositoryOrSystemRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryAndSystemPackageReactions.DeleteCorrespondingRepositoryOrSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPackage = umlPackage;
  }
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteCorrespondingRepositoryOrSystemRoutine with input:");
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    	Optional<org.palladiosimulator.pcm.repository.Repository> pcmRepository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRepository(umlPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRepository.isPresent() ? pcmRepository.get() : null);
    	Optional<org.palladiosimulator.pcm.system.System> pcmSystem = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmSystem(umlPackage, pcmRepository), // correspondence source supplier
    		org.palladiosimulator.pcm.system.System.class,
    		(org.palladiosimulator.pcm.system.System _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlPackage, pcmRepository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmSystem.isPresent() ? pcmSystem.get() : null);
    userExecution.callRoutine1(umlPackage, pcmRepository, pcmSystem, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
