package mir.routines.umlXpcmComponent_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_component(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component) {
      return affectedEObject;
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_Repository:repository";
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_repository) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_Repository:repository";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:componentPackage_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Package:repositoryPackage_with_RepositoryComponent:component";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Class:implementation_with_RepositoryComponent:component";
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_repository) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_repository, final Optional<Operation> constructor_correspondingTo_repository, @Extension final RoutinesFacade _routinesFacade) {
      org.eclipse.uml2.uml.Package componentPackage_ = null;
      org.eclipse.uml2.uml.Package repositoryPackage_ = null;
      org.eclipse.uml2.uml.Class implementation_ = null;
      Operation constructor_ = null;
      boolean _isPresent = componentPackage_correspondingTo_component.isPresent();
      if (_isPresent) {
        componentPackage_ = componentPackage_correspondingTo_component.get();
      }
      boolean _isPresent_1 = repositoryPackage_correspondingTo_component.isPresent();
      if (_isPresent_1) {
        repositoryPackage_ = repositoryPackage_correspondingTo_component.get();
      }
      boolean _isPresent_2 = implementation_correspondingTo_component.isPresent();
      if (_isPresent_2) {
        implementation_ = implementation_correspondingTo_component.get();
      }
      boolean _isPresent_3 = constructor_correspondingTo_component.isPresent();
      if (_isPresent_3) {
        constructor_ = constructor_correspondingTo_component.get();
      }
      boolean _isPresent_4 = componentPackage_correspondingTo_repository.isPresent();
      if (_isPresent_4) {
        componentPackage_ = componentPackage_correspondingTo_repository.get();
      }
      boolean _isPresent_5 = repositoryPackage_correspondingTo_repository.isPresent();
      if (_isPresent_5) {
        repositoryPackage_ = repositoryPackage_correspondingTo_repository.get();
      }
      boolean _isPresent_6 = implementation_correspondingTo_repository.isPresent();
      if (_isPresent_6) {
        implementation_ = implementation_correspondingTo_repository.get();
      }
      boolean _isPresent_7 = constructor_correspondingTo_repository.isPresent();
      if (_isPresent_7) {
        constructor_ = constructor_correspondingTo_repository.get();
      }
      if (((((componentPackage_ != null) && (repositoryPackage_ != null)) && (implementation_ != null)) && (constructor_ != null))) {
        _routinesFacade.repositoryComponent_DeleteMapping(componentPackage_, repositoryPackage_, implementation_, constructor_);
      }
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceComponentPackage_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceComponentPackage_correspondingTo_component(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceConstructor_correspondingTo_component(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceConstructor_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_repository) {
      return affectedEObject;
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component, final Optional<Operation> constructor_correspondingTo_component, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_repository) {
      return "umlXpcmComponent_map_UML_and_PCM_correspondence_Operation:constructor_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepositoryPackage_correspondingTo_component(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component) {
      return affectedEObject;
    }
  }
  
  public RepositoryComponent_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_R2L.RepositoryComponent_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_component = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceComponentPackage_correspondingTo_component(affectedEObject), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(componentPackage_correspondingTo_component.isPresent() ? componentPackage_correspondingTo_component.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_component = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepositoryPackage_correspondingTo_component(affectedEObject, componentPackage_correspondingTo_component), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, componentPackage_correspondingTo_component), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repositoryPackage_correspondingTo_component.isPresent() ? repositoryPackage_correspondingTo_component.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_component = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_component(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_component.isPresent() ? implementation_correspondingTo_component.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> constructor_correspondingTo_component = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceConstructor_correspondingTo_component(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(constructor_correspondingTo_component.isPresent() ? constructor_correspondingTo_component.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> componentPackage_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceComponentPackage_correspondingTo_repository(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(componentPackage_correspondingTo_repository.isPresent() ? componentPackage_correspondingTo_repository.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> repositoryPackage_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepositoryPackage_correspondingTo_repository(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component, componentPackage_correspondingTo_repository), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component, componentPackage_correspondingTo_repository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repositoryPackage_correspondingTo_repository.isPresent() ? repositoryPackage_correspondingTo_repository.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_repository(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component, componentPackage_correspondingTo_repository, repositoryPackage_correspondingTo_repository), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component, componentPackage_correspondingTo_repository, repositoryPackage_correspondingTo_repository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_repository.isPresent() ? implementation_correspondingTo_repository.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> constructor_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceConstructor_correspondingTo_repository(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component, componentPackage_correspondingTo_repository, repositoryPackage_correspondingTo_repository, implementation_correspondingTo_repository), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component, componentPackage_correspondingTo_repository, repositoryPackage_correspondingTo_repository, implementation_correspondingTo_repository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(constructor_correspondingTo_repository.isPresent() ? constructor_correspondingTo_repository.get() : null);
    userExecution.callRoutine1(affectedEObject, componentPackage_correspondingTo_component, repositoryPackage_correspondingTo_component, implementation_correspondingTo_component, constructor_correspondingTo_component, componentPackage_correspondingTo_repository, repositoryPackage_correspondingTo_repository, implementation_correspondingTo_repository, constructor_correspondingTo_repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
