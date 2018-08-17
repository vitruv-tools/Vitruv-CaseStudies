package mir.routines.umlRepositoryAndSystemPackageReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRepositoryAndSystemPackageReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.PcmUmlClassHelper;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingRepositoryOrSystemRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingRepositoryOrSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.eclipse.uml2.uml.Package umlPackage, final org.eclipse.uml2.uml.Package umlParentPackage) {
      return umlPackage;
    }
    
    public EObject getCorrepondenceSourcePcmSystem(final org.eclipse.uml2.uml.Package umlPackage, final org.eclipse.uml2.uml.Package umlParentPackage, final Optional<Repository> pcmRepository) {
      return umlPackage;
    }
    
    public String getRetrieveTag1(final org.eclipse.uml2.uml.Package umlPackage, final org.eclipse.uml2.uml.Package umlParentPackage) {
      return TagLiterals.REPOSITORY_TO_REPOSITORY_PACKAGE;
    }
    
    public String getRetrieveTag2(final org.eclipse.uml2.uml.Package umlPackage, final org.eclipse.uml2.uml.Package umlParentPackage, final Optional<Repository> pcmRepository) {
      return TagLiterals.SYSTEM__SYSTEM_PACKAGE;
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package umlPackage, final org.eclipse.uml2.uml.Package umlParentPackage, final Optional<Repository> pcmRepository, final Optional<org.palladiosimulator.pcm.system.System> pcmSystem, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isContainedInRepositoryHierarchy = PcmUmlClassHelper.isContainedInRepositoryHierarchy(umlPackage, this.correspondenceModel);
      boolean _not = (!_isContainedInRepositoryHierarchy);
      if (_not) {
        if (((!pcmRepository.isPresent()) && (!pcmSystem.isPresent()))) {
          _routinesFacade.userDisambiguateRepositoryOrSystemCreation(umlPackage, umlParentPackage);
        }
      } else {
        _routinesFacade.deleteCorrespondingRepositoryOrSystem(umlPackage);
      }
    }
  }
  
  public InsertCorrespondingRepositoryOrSystemRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package umlPackage, final org.eclipse.uml2.uml.Package umlParentPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRepositoryAndSystemPackageReactions.InsertCorrespondingRepositoryOrSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlPackage = umlPackage;this.umlParentPackage = umlParentPackage;
  }
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  private org.eclipse.uml2.uml.Package umlParentPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingRepositoryOrSystemRoutine with input:");
    getLogger().debug("   umlPackage: " + this.umlPackage);
    getLogger().debug("   umlParentPackage: " + this.umlParentPackage);
    
    	Optional<org.palladiosimulator.pcm.repository.Repository> pcmRepository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRepository(umlPackage, umlParentPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlPackage, umlParentPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRepository.isPresent() ? pcmRepository.get() : null);
    	Optional<org.palladiosimulator.pcm.system.System> pcmSystem = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmSystem(umlPackage, umlParentPackage, pcmRepository), // correspondence source supplier
    		org.palladiosimulator.pcm.system.System.class,
    		(org.palladiosimulator.pcm.system.System _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlPackage, umlParentPackage, pcmRepository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmSystem.isPresent() ? pcmSystem.get() : null);
    userExecution.callRoutine1(umlPackage, umlParentPackage, pcmRepository, pcmSystem, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
