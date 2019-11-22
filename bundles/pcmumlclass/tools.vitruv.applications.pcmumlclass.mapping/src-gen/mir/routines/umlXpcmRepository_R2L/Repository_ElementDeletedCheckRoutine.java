package mir.routines.umlXpcmRepository_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmRepository_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:repositoryPkg_with_Repository:repository";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> repositoryPkg_correspondingTo_repository) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:contractsPkg_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceRepositoryPkg_correspondingTo_repository(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> repositoryPkg_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> contractsPkg_correspondingTo_repository) {
      return "umlXpcmRepository_map_UML_and_PCM_correspondence_Package:datatypesPkg_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceContractsPkg_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> repositoryPkg_correspondingTo_repository) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceDatatypesPkg_correspondingTo_repository(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> repositoryPkg_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> contractsPkg_correspondingTo_repository) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<org.eclipse.uml2.uml.Package> repositoryPkg_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> contractsPkg_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> datatypesPkg_correspondingTo_repository, @Extension final RoutinesFacade _routinesFacade) {
      org.eclipse.uml2.uml.Package repositoryPkg_ = null;
      org.eclipse.uml2.uml.Package contractsPkg_ = null;
      org.eclipse.uml2.uml.Package datatypesPkg_ = null;
      boolean _isPresent = repositoryPkg_correspondingTo_repository.isPresent();
      if (_isPresent) {
        repositoryPkg_ = repositoryPkg_correspondingTo_repository.get();
      }
      boolean _isPresent_1 = contractsPkg_correspondingTo_repository.isPresent();
      if (_isPresent_1) {
        contractsPkg_ = contractsPkg_correspondingTo_repository.get();
      }
      boolean _isPresent_2 = datatypesPkg_correspondingTo_repository.isPresent();
      if (_isPresent_2) {
        datatypesPkg_ = datatypesPkg_correspondingTo_repository.get();
      }
      if ((((repositoryPkg_ != null) && (contractsPkg_ != null)) && (datatypesPkg_ != null))) {
        _routinesFacade.repository_DeleteMapping(repositoryPkg_, contractsPkg_, datatypesPkg_);
      }
    }
  }
  
  public Repository_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_R2L.Repository_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.eclipse.uml2.uml.Package> repositoryPkg_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepositoryPkg_correspondingTo_repository(affectedEObject), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repositoryPkg_correspondingTo_repository.isPresent() ? repositoryPkg_correspondingTo_repository.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> contractsPkg_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceContractsPkg_correspondingTo_repository(affectedEObject, repositoryPkg_correspondingTo_repository), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, repositoryPkg_correspondingTo_repository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(contractsPkg_correspondingTo_repository.isPresent() ? contractsPkg_correspondingTo_repository.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> datatypesPkg_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceDatatypesPkg_correspondingTo_repository(affectedEObject, repositoryPkg_correspondingTo_repository, contractsPkg_correspondingTo_repository), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, repositoryPkg_correspondingTo_repository, contractsPkg_correspondingTo_repository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(datatypesPkg_correspondingTo_repository.isPresent() ? datatypesPkg_correspondingTo_repository.get() : null);
    userExecution.callRoutine1(affectedEObject, repositoryPkg_correspondingTo_repository, contractsPkg_correspondingTo_repository, datatypesPkg_correspondingTo_repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
