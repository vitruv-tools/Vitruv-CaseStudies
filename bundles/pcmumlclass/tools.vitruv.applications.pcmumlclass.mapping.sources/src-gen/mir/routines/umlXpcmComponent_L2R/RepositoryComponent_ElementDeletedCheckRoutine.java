package mir.routines.umlXpcmComponent_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmComponent_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_componentPackage(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage) {
      return affectedEObject;
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_Repository:repository";
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage, final Optional<RepositoryComponent> component_correspondingTo_implementation) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_Repository:repository";
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage, final Optional<RepositoryComponent> component_correspondingTo_implementation, final Optional<Repository> repository_correspondingTo_implementation) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_constructor(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage, final Optional<RepositoryComponent> component_correspondingTo_implementation, final Optional<Repository> repository_correspondingTo_implementation, final Optional<RepositoryComponent> component_correspondingTo_constructor) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceComponent_correspondingTo_componentPackage(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_RepositoryComponent:component";
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_implementation(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage, final Optional<RepositoryComponent> component_correspondingTo_implementation) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage, final Optional<RepositoryComponent> component_correspondingTo_implementation, final Optional<Repository> repository_correspondingTo_implementation, final Optional<RepositoryComponent> component_correspondingTo_constructor, final Optional<Repository> repository_correspondingTo_constructor, @Extension final RoutinesFacade _routinesFacade) {
      RepositoryComponent component_ = null;
      Repository repository_ = null;
      boolean _isPresent = component_correspondingTo_componentPackage.isPresent();
      if (_isPresent) {
        component_ = component_correspondingTo_componentPackage.get();
      }
      boolean _isPresent_1 = repository_correspondingTo_componentPackage.isPresent();
      if (_isPresent_1) {
        repository_ = repository_correspondingTo_componentPackage.get();
      }
      boolean _isPresent_2 = component_correspondingTo_repositoryPackage.isPresent();
      if (_isPresent_2) {
        component_ = component_correspondingTo_repositoryPackage.get();
      }
      boolean _isPresent_3 = repository_correspondingTo_repositoryPackage.isPresent();
      if (_isPresent_3) {
        repository_ = repository_correspondingTo_repositoryPackage.get();
      }
      boolean _isPresent_4 = component_correspondingTo_implementation.isPresent();
      if (_isPresent_4) {
        component_ = component_correspondingTo_implementation.get();
      }
      boolean _isPresent_5 = repository_correspondingTo_implementation.isPresent();
      if (_isPresent_5) {
        repository_ = repository_correspondingTo_implementation.get();
      }
      boolean _isPresent_6 = component_correspondingTo_constructor.isPresent();
      if (_isPresent_6) {
        component_ = component_correspondingTo_constructor.get();
      }
      boolean _isPresent_7 = repository_correspondingTo_constructor.isPresent();
      if (_isPresent_7) {
        repository_ = repository_correspondingTo_constructor.get();
      }
      if (((component_ != null) && (repository_ != null))) {
        _routinesFacade.repositoryComponent_DeleteMapping(component_, repository_);
      }
    }
    
    public EObject getCorrepondenceSourceComponent_correspondingTo_repositoryPackage(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceComponent_correspondingTo_implementation(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage) {
      return affectedEObject;
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage, final Optional<RepositoryComponent> component_correspondingTo_implementation, final Optional<Repository> repository_correspondingTo_implementation, final Optional<RepositoryComponent> component_correspondingTo_constructor) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_repositoryPackage(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceComponent_correspondingTo_constructor(final EObject affectedEObject, final Optional<RepositoryComponent> component_correspondingTo_componentPackage, final Optional<Repository> repository_correspondingTo_componentPackage, final Optional<RepositoryComponent> component_correspondingTo_repositoryPackage, final Optional<Repository> repository_correspondingTo_repositoryPackage, final Optional<RepositoryComponent> component_correspondingTo_implementation, final Optional<Repository> repository_correspondingTo_implementation) {
      return affectedEObject;
    }
  }
  
  public RepositoryComponent_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_L2R.RepositoryComponent_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> component_correspondingTo_componentPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceComponent_correspondingTo_componentPackage(affectedEObject), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(component_correspondingTo_componentPackage.isPresent() ? component_correspondingTo_componentPackage.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_componentPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_componentPackage(affectedEObject, component_correspondingTo_componentPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, component_correspondingTo_componentPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_componentPackage.isPresent() ? repository_correspondingTo_componentPackage.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> component_correspondingTo_repositoryPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceComponent_correspondingTo_repositoryPackage(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(component_correspondingTo_repositoryPackage.isPresent() ? component_correspondingTo_repositoryPackage.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_repositoryPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_repositoryPackage(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_repositoryPackage.isPresent() ? repository_correspondingTo_repositoryPackage.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> component_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceComponent_correspondingTo_implementation(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(component_correspondingTo_implementation.isPresent() ? component_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_implementation(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage, component_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage, component_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_implementation.isPresent() ? repository_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.RepositoryComponent> component_correspondingTo_constructor = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceComponent_correspondingTo_constructor(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage, component_correspondingTo_implementation, repository_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.RepositoryComponent.class,
    		(org.palladiosimulator.pcm.repository.RepositoryComponent _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage, component_correspondingTo_implementation, repository_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(component_correspondingTo_constructor.isPresent() ? component_correspondingTo_constructor.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_constructor = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_constructor(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage, component_correspondingTo_implementation, repository_correspondingTo_implementation, component_correspondingTo_constructor), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage, component_correspondingTo_implementation, repository_correspondingTo_implementation, component_correspondingTo_constructor), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_constructor.isPresent() ? repository_correspondingTo_constructor.get() : null);
    userExecution.callRoutine1(affectedEObject, component_correspondingTo_componentPackage, repository_correspondingTo_componentPackage, component_correspondingTo_repositoryPackage, repository_correspondingTo_repositoryPackage, component_correspondingTo_implementation, repository_correspondingTo_implementation, component_correspondingTo_constructor, repository_correspondingTo_constructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
