package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterface_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private OperationInterface_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<Repository> repository_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_contractsPackage) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_interface(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_contractsPackage(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<Repository> repository_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_Repository:repository";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<Repository> repository_correspondingTo_interface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceRepository_correspondingTo_contractsPackage(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<Repository> repository_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_contractsPackage) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<Repository> repository_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_contractsPackage, final Optional<Repository> repository_correspondingTo_contractsPackage, @Extension final RoutinesFacade _routinesFacade) {
      OperationInterface operationInterface_ = null;
      Repository repository_ = null;
      boolean _isPresent = operationInterface_correspondingTo_interface.isPresent();
      if (_isPresent) {
        operationInterface_ = operationInterface_correspondingTo_interface.get();
      }
      boolean _isPresent_1 = repository_correspondingTo_interface.isPresent();
      if (_isPresent_1) {
        repository_ = repository_correspondingTo_interface.get();
      }
      boolean _isPresent_2 = operationInterface_correspondingTo_contractsPackage.isPresent();
      if (_isPresent_2) {
        operationInterface_ = operationInterface_correspondingTo_contractsPackage.get();
      }
      boolean _isPresent_3 = repository_correspondingTo_contractsPackage.isPresent();
      if (_isPresent_3) {
        repository_ = repository_correspondingTo_contractsPackage.get();
      }
      if (((operationInterface_ != null) && (repository_ != null))) {
        _routinesFacade.operationInterface_DeleteMapping(operationInterface_, repository_);
      }
    }
  }
  
  public OperationInterface_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterface_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_interface(affectedEObject), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_interface.isPresent() ? operationInterface_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_interface(affectedEObject, operationInterface_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, operationInterface_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_interface.isPresent() ? repository_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_contractsPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_contractsPackage(affectedEObject, operationInterface_correspondingTo_interface, repository_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, operationInterface_correspondingTo_interface, repository_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_contractsPackage.isPresent() ? operationInterface_correspondingTo_contractsPackage.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.Repository> repository_correspondingTo_contractsPackage = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRepository_correspondingTo_contractsPackage(affectedEObject, operationInterface_correspondingTo_interface, repository_correspondingTo_interface, operationInterface_correspondingTo_contractsPackage), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Repository.class,
    		(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, operationInterface_correspondingTo_interface, repository_correspondingTo_interface, operationInterface_correspondingTo_contractsPackage), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(repository_correspondingTo_contractsPackage.isPresent() ? repository_correspondingTo_contractsPackage.get() : null);
    userExecution.callRoutine1(affectedEObject, operationInterface_correspondingTo_interface, repository_correspondingTo_interface, operationInterface_correspondingTo_contractsPackage, repository_correspondingTo_contractsPackage, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
