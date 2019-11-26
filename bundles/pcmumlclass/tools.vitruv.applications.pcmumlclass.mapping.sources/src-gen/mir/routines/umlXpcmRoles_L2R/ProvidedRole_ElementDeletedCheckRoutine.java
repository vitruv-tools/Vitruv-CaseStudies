package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ProvidedRole_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private ProvidedRole_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationProvidedRole:role";
    }
    
    public EObject getCorrepondenceSourceProvidingEntity_correspondingTo_interfaceRealization(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization) {
      return affectedEObject;
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_interfaceRealization(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationProvidedRole:role";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationProvidedRole:role";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_implementation(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceProvidingEntity_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation, final Optional<OperationProvidedRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_implementation(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation, final Optional<OperationProvidedRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interface, @Extension final RoutinesFacade _routinesFacade) {
      OperationProvidedRole role_ = null;
      OperationInterface operationInterface_ = null;
      InterfaceProvidingRequiringEntity providingEntity_ = null;
      boolean _isPresent = role_correspondingTo_interfaceRealization.isPresent();
      if (_isPresent) {
        role_ = role_correspondingTo_interfaceRealization.get();
      }
      boolean _isPresent_1 = operationInterface_correspondingTo_interfaceRealization.isPresent();
      if (_isPresent_1) {
        operationInterface_ = operationInterface_correspondingTo_interfaceRealization.get();
      }
      boolean _isPresent_2 = providingEntity_correspondingTo_interfaceRealization.isPresent();
      if (_isPresent_2) {
        providingEntity_ = providingEntity_correspondingTo_interfaceRealization.get();
      }
      boolean _isPresent_3 = role_correspondingTo_implementation.isPresent();
      if (_isPresent_3) {
        role_ = role_correspondingTo_implementation.get();
      }
      boolean _isPresent_4 = operationInterface_correspondingTo_implementation.isPresent();
      if (_isPresent_4) {
        operationInterface_ = operationInterface_correspondingTo_implementation.get();
      }
      boolean _isPresent_5 = providingEntity_correspondingTo_implementation.isPresent();
      if (_isPresent_5) {
        providingEntity_ = providingEntity_correspondingTo_implementation.get();
      }
      boolean _isPresent_6 = role_correspondingTo_interface.isPresent();
      if (_isPresent_6) {
        role_ = role_correspondingTo_interface.get();
      }
      boolean _isPresent_7 = operationInterface_correspondingTo_interface.isPresent();
      if (_isPresent_7) {
        operationInterface_ = operationInterface_correspondingTo_interface.get();
      }
      boolean _isPresent_8 = providingEntity_correspondingTo_interface.isPresent();
      if (_isPresent_8) {
        providingEntity_ = providingEntity_correspondingTo_interface.get();
      }
      if ((((role_ != null) && (operationInterface_ != null)) && (providingEntity_ != null))) {
        _routinesFacade.providedRole_DeleteMapping(role_, operationInterface_, providingEntity_);
      }
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation, final Optional<OperationProvidedRole> role_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_interfaceRealization(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceProvidingEntity_correspondingTo_implementation(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation) {
      return affectedEObject;
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation, final Optional<OperationProvidedRole> role_correspondingTo_interface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag9(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation, final Optional<OperationProvidedRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationProvidedRole> role_correspondingTo_interfaceRealization, final Optional<OperationInterface> operationInterface_correspondingTo_interfaceRealization, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization, final Optional<OperationProvidedRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation) {
      return affectedEObject;
    }
  }
  
  public ProvidedRole_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.ProvidedRole_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.palladiosimulator.pcm.repository.OperationProvidedRole> role_correspondingTo_interfaceRealization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_interfaceRealization(affectedEObject), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    		(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_interfaceRealization.isPresent() ? role_correspondingTo_interfaceRealization.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_interfaceRealization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_interfaceRealization(affectedEObject, role_correspondingTo_interfaceRealization), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, role_correspondingTo_interfaceRealization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_interfaceRealization.isPresent() ? operationInterface_correspondingTo_interfaceRealization.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interfaceRealization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceProvidingEntity_correspondingTo_interfaceRealization(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(providingEntity_correspondingTo_interfaceRealization.isPresent() ? providingEntity_correspondingTo_interfaceRealization.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationProvidedRole> role_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_implementation(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    		(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_implementation.isPresent() ? role_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_implementation(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_implementation.isPresent() ? operationInterface_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceProvidingEntity_correspondingTo_implementation(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(providingEntity_correspondingTo_implementation.isPresent() ? providingEntity_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationProvidedRole> role_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_interface(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, providingEntity_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    		(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, providingEntity_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_interface.isPresent() ? role_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_interface(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, providingEntity_correspondingTo_implementation, role_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, providingEntity_correspondingTo_implementation, role_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_interface.isPresent() ? operationInterface_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> providingEntity_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceProvidingEntity_correspondingTo_interface(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, providingEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag9(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, providingEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(providingEntity_correspondingTo_interface.isPresent() ? providingEntity_correspondingTo_interface.get() : null);
    userExecution.callRoutine1(affectedEObject, role_correspondingTo_interfaceRealization, operationInterface_correspondingTo_interfaceRealization, providingEntity_correspondingTo_interfaceRealization, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, providingEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, providingEntity_correspondingTo_interface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
