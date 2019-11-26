package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RequiredRole_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private RequiredRole_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_operation(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRequiringEntity_correspondingTo_property(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_implementation(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_implementation(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRequiringEntity_correspondingTo_parameter(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface, final Optional<OperationRequiredRole> role_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_operation, @Extension final RoutinesFacade _routinesFacade) {
      OperationRequiredRole role_ = null;
      OperationInterface operationInterface_ = null;
      InterfaceProvidingRequiringEntity requiringEntity_ = null;
      boolean _isPresent = role_correspondingTo_property.isPresent();
      if (_isPresent) {
        role_ = role_correspondingTo_property.get();
      }
      boolean _isPresent_1 = operationInterface_correspondingTo_property.isPresent();
      if (_isPresent_1) {
        operationInterface_ = operationInterface_correspondingTo_property.get();
      }
      boolean _isPresent_2 = requiringEntity_correspondingTo_property.isPresent();
      if (_isPresent_2) {
        requiringEntity_ = requiringEntity_correspondingTo_property.get();
      }
      boolean _isPresent_3 = role_correspondingTo_parameter.isPresent();
      if (_isPresent_3) {
        role_ = role_correspondingTo_parameter.get();
      }
      boolean _isPresent_4 = operationInterface_correspondingTo_parameter.isPresent();
      if (_isPresent_4) {
        operationInterface_ = operationInterface_correspondingTo_parameter.get();
      }
      boolean _isPresent_5 = requiringEntity_correspondingTo_parameter.isPresent();
      if (_isPresent_5) {
        requiringEntity_ = requiringEntity_correspondingTo_parameter.get();
      }
      boolean _isPresent_6 = role_correspondingTo_implementation.isPresent();
      if (_isPresent_6) {
        role_ = role_correspondingTo_implementation.get();
      }
      boolean _isPresent_7 = operationInterface_correspondingTo_implementation.isPresent();
      if (_isPresent_7) {
        operationInterface_ = operationInterface_correspondingTo_implementation.get();
      }
      boolean _isPresent_8 = requiringEntity_correspondingTo_implementation.isPresent();
      if (_isPresent_8) {
        requiringEntity_ = requiringEntity_correspondingTo_implementation.get();
      }
      boolean _isPresent_9 = role_correspondingTo_interface.isPresent();
      if (_isPresent_9) {
        role_ = role_correspondingTo_interface.get();
      }
      boolean _isPresent_10 = operationInterface_correspondingTo_interface.isPresent();
      if (_isPresent_10) {
        operationInterface_ = operationInterface_correspondingTo_interface.get();
      }
      boolean _isPresent_11 = requiringEntity_correspondingTo_interface.isPresent();
      if (_isPresent_11) {
        requiringEntity_ = requiringEntity_correspondingTo_interface.get();
      }
      boolean _isPresent_12 = role_correspondingTo_operation.isPresent();
      if (_isPresent_12) {
        role_ = role_correspondingTo_operation.get();
      }
      boolean _isPresent_13 = operationInterface_correspondingTo_operation.isPresent();
      if (_isPresent_13) {
        operationInterface_ = operationInterface_correspondingTo_operation.get();
      }
      boolean _isPresent_14 = requiringEntity_correspondingTo_operation.isPresent();
      if (_isPresent_14) {
        requiringEntity_ = requiringEntity_correspondingTo_operation.get();
      }
      if ((((role_ != null) && (operationInterface_ != null)) && (requiringEntity_ != null))) {
        _routinesFacade.requiredRole_DeleteMapping(role_, operationInterface_, requiringEntity_);
      }
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRequiringEntity_correspondingTo_operation(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface, final Optional<OperationRequiredRole> role_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRequiringEntity_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_parameter(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_property(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_property(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationRequiredRole:role";
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_parameter(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property) {
      return affectedEObject;
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_operation(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface, final Optional<OperationRequiredRole> role_correspondingTo_operation) {
      return affectedEObject;
    }
    
    public String getRetrieveTag10(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag13(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag14(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface, final Optional<OperationRequiredRole> role_correspondingTo_operation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag11(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag12(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceRequiringEntity_correspondingTo_implementation(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation) {
      return affectedEObject;
    }
    
    public String getRetrieveTag9(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public EObject getCorrepondenceSourceRole_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation) {
      return affectedEObject;
    }
    
    public String getRetrieveTag15(final EObject affectedEObject, final Optional<OperationRequiredRole> role_correspondingTo_property, final Optional<OperationInterface> operationInterface_correspondingTo_property, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property, final Optional<OperationRequiredRole> role_correspondingTo_parameter, final Optional<OperationInterface> operationInterface_correspondingTo_parameter, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter, final Optional<OperationRequiredRole> role_correspondingTo_implementation, final Optional<OperationInterface> operationInterface_correspondingTo_implementation, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation, final Optional<OperationRequiredRole> role_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface, final Optional<OperationRequiredRole> role_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
  }
  
  public RequiredRole_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.RequiredRole_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> role_correspondingTo_property = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_property(affectedEObject), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_property.isPresent() ? role_correspondingTo_property.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_property = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_property(affectedEObject, role_correspondingTo_property), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, role_correspondingTo_property), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_property.isPresent() ? operationInterface_correspondingTo_property.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_property = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRequiringEntity_correspondingTo_property(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(requiringEntity_correspondingTo_property.isPresent() ? requiringEntity_correspondingTo_property.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> role_correspondingTo_parameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_parameter(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_parameter.isPresent() ? role_correspondingTo_parameter.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_parameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_parameter(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_parameter.isPresent() ? operationInterface_correspondingTo_parameter.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_parameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRequiringEntity_correspondingTo_parameter(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(requiringEntity_correspondingTo_parameter.isPresent() ? requiringEntity_correspondingTo_parameter.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> role_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_implementation(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_implementation.isPresent() ? role_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_implementation(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_implementation.isPresent() ? operationInterface_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_implementation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRequiringEntity_correspondingTo_implementation(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag9(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(requiringEntity_correspondingTo_implementation.isPresent() ? requiringEntity_correspondingTo_implementation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> role_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_interface(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag10(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_interface.isPresent() ? role_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_interface(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag11(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_interface.isPresent() ? operationInterface_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRequiringEntity_correspondingTo_interface(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag12(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(requiringEntity_correspondingTo_interface.isPresent() ? requiringEntity_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> role_correspondingTo_operation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRole_correspondingTo_operation(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, requiringEntity_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag13(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, requiringEntity_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(role_correspondingTo_operation.isPresent() ? role_correspondingTo_operation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_operation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_operation(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, requiringEntity_correspondingTo_interface, role_correspondingTo_operation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag14(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, requiringEntity_correspondingTo_interface, role_correspondingTo_operation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_operation.isPresent() ? operationInterface_correspondingTo_operation.get() : null);
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> requiringEntity_correspondingTo_operation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRequiringEntity_correspondingTo_operation(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, requiringEntity_correspondingTo_interface, role_correspondingTo_operation, operationInterface_correspondingTo_operation), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag15(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, requiringEntity_correspondingTo_interface, role_correspondingTo_operation, operationInterface_correspondingTo_operation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(requiringEntity_correspondingTo_operation.isPresent() ? requiringEntity_correspondingTo_operation.get() : null);
    userExecution.callRoutine1(affectedEObject, role_correspondingTo_property, operationInterface_correspondingTo_property, requiringEntity_correspondingTo_property, role_correspondingTo_parameter, operationInterface_correspondingTo_parameter, requiringEntity_correspondingTo_parameter, role_correspondingTo_implementation, operationInterface_correspondingTo_implementation, requiringEntity_correspondingTo_implementation, role_correspondingTo_interface, operationInterface_correspondingTo_interface, requiringEntity_correspondingTo_interface, role_correspondingTo_operation, operationInterface_correspondingTo_operation, requiringEntity_correspondingTo_operation, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
