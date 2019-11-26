package mir.routines.umlXpcmRepository_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmRepository_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private Repository_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_contractsPkg(final EObject affectedEObject, final Optional<Repository> repository_correspondingTo_repositoryPkg) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_repositoryPkg(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<Repository> repository_correspondingTo_repositoryPkg) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<Repository> repository_correspondingTo_repositoryPkg, final Optional<Repository> repository_correspondingTo_contractsPkg) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_datatypesPkg(final EObject affectedEObject, final Optional<Repository> repository_correspondingTo_repositoryPkg, final Optional<Repository> repository_correspondingTo_contractsPkg) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<Repository> repository_correspondingTo_repositoryPkg, final Optional<Repository> repository_correspondingTo_contractsPkg, final Optional<Repository> repository_correspondingTo_datatypesPkg, @Extension final RoutinesFacade _routinesFacade) {
      Repository repository_ = null;
      boolean _isPresent = repository_correspondingTo_repositoryPkg.isPresent();
      if (_isPresent) {
        repository_ = repository_correspondingTo_repositoryPkg.get();
      }
      boolean _isPresent_1 = repository_correspondingTo_contractsPkg.isPresent();
      if (_isPresent_1) {
        repository_ = repository_correspondingTo_contractsPkg.get();
      }
      boolean _isPresent_2 = repository_correspondingTo_datatypesPkg.isPresent();
      if (_isPresent_2) {
        repository_ = repository_correspondingTo_datatypesPkg.get();
      }
      if ((repository_ != null)) {
        _routinesFacade.repository_DeleteMapping(repository_);
      }
    }
  }
  
  public Repository_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_L2R.Repository_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_repositoryPkg = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_repositoryPkg(affectedEObject), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_repositoryPkg.isPresent() ? repository_correspondingTo_repositoryPkg.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_contractsPkg = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_contractsPkg(affectedEObject, repository_correspondingTo_repositoryPkg), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, repository_correspondingTo_repositoryPkg), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_contractsPkg.isPresent() ? repository_correspondingTo_contractsPkg.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_datatypesPkg = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_datatypesPkg(affectedEObject, repository_correspondingTo_repositoryPkg, repository_correspondingTo_contractsPkg), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, repository_correspondingTo_repositoryPkg, repository_correspondingTo_contractsPkg), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_datatypesPkg.isPresent() ? repository_correspondingTo_datatypesPkg.get() : null);
    userExecution.callRoutine1(affectedEObject, repository_correspondingTo_repositoryPkg, repository_correspondingTo_contractsPkg, repository_correspondingTo_datatypesPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
