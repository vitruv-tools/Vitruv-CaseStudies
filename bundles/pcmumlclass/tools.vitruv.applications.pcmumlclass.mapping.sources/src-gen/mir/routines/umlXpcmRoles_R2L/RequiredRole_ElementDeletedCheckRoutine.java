package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_requiringEntity(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity, final Optional<Parameter> parameter_correspondingTo_requiringEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_requiringEntity) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceProperty_correspondingTo_role(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperation_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_requiringEntity(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity, final Optional<Parameter> parameter_correspondingTo_requiringEntity) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity, final Optional<Parameter> parameter_correspondingTo_requiringEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_requiringEntity, final Optional<Interface> interface_correspondingTo_requiringEntity, final Optional<Operation> operation_correspondingTo_requiringEntity, @Extension final RoutinesFacade _routinesFacade) {
      Property property_ = null;
      Parameter parameter_ = null;
      org.eclipse.uml2.uml.Class implementation_ = null;
      Interface interface_ = null;
      Operation operation_ = null;
      boolean _isPresent = property_correspondingTo_role.isPresent();
      if (_isPresent) {
        property_ = property_correspondingTo_role.get();
      }
      boolean _isPresent_1 = parameter_correspondingTo_role.isPresent();
      if (_isPresent_1) {
        parameter_ = parameter_correspondingTo_role.get();
      }
      boolean _isPresent_2 = implementation_correspondingTo_role.isPresent();
      if (_isPresent_2) {
        implementation_ = implementation_correspondingTo_role.get();
      }
      boolean _isPresent_3 = interface_correspondingTo_role.isPresent();
      if (_isPresent_3) {
        interface_ = interface_correspondingTo_role.get();
      }
      boolean _isPresent_4 = operation_correspondingTo_role.isPresent();
      if (_isPresent_4) {
        operation_ = operation_correspondingTo_role.get();
      }
      boolean _isPresent_5 = property_correspondingTo_operationInterface.isPresent();
      if (_isPresent_5) {
        property_ = property_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_6 = parameter_correspondingTo_operationInterface.isPresent();
      if (_isPresent_6) {
        parameter_ = parameter_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_7 = implementation_correspondingTo_operationInterface.isPresent();
      if (_isPresent_7) {
        implementation_ = implementation_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_8 = interface_correspondingTo_operationInterface.isPresent();
      if (_isPresent_8) {
        interface_ = interface_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_9 = operation_correspondingTo_operationInterface.isPresent();
      if (_isPresent_9) {
        operation_ = operation_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_10 = property_correspondingTo_requiringEntity.isPresent();
      if (_isPresent_10) {
        property_ = property_correspondingTo_requiringEntity.get();
      }
      boolean _isPresent_11 = parameter_correspondingTo_requiringEntity.isPresent();
      if (_isPresent_11) {
        parameter_ = parameter_correspondingTo_requiringEntity.get();
      }
      boolean _isPresent_12 = implementation_correspondingTo_requiringEntity.isPresent();
      if (_isPresent_12) {
        implementation_ = implementation_correspondingTo_requiringEntity.get();
      }
      boolean _isPresent_13 = interface_correspondingTo_requiringEntity.isPresent();
      if (_isPresent_13) {
        interface_ = interface_correspondingTo_requiringEntity.get();
      }
      boolean _isPresent_14 = operation_correspondingTo_requiringEntity.isPresent();
      if (_isPresent_14) {
        operation_ = operation_correspondingTo_requiringEntity.get();
      }
      if ((((((property_ != null) && (parameter_ != null)) && (implementation_ != null)) && (interface_ != null)) && (operation_ != null))) {
        _routinesFacade.requiredRole_DeleteMapping(property_, parameter_, implementation_, interface_, operation_);
      }
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_role(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceProperty_correspondingTo_requiringEntity(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_OperationRequiredRole:role";
    }
    
    public EObject getCorrepondenceSourceParameter_correspondingTo_requiringEntity(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperation_correspondingTo_requiringEntity(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity, final Optional<Parameter> parameter_correspondingTo_requiringEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_requiringEntity, final Optional<Interface> interface_correspondingTo_requiringEntity) {
      return affectedEObject;
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_OperationRequiredRole:role";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationRequiredRole:role";
    }
    
    public EObject getCorrepondenceSourceParameter_correspondingTo_role(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceParameter_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_role(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceProperty_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role) {
      return affectedEObject;
    }
    
    public String getRetrieveTag10(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag13(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity, final Optional<Parameter> parameter_correspondingTo_requiringEntity) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public EObject getCorrepondenceSourceOperation_correspondingTo_role(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role) {
      return affectedEObject;
    }
    
    public String getRetrieveTag14(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity, final Optional<Parameter> parameter_correspondingTo_requiringEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_requiringEntity) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getRetrieveTag11(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Property:property_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getRetrieveTag12(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Parameter:parameter_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag9(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag15(final EObject affectedEObject, final Optional<Property> property_correspondingTo_role, final Optional<Parameter> parameter_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<Operation> operation_correspondingTo_role, final Optional<Property> property_correspondingTo_operationInterface, final Optional<Parameter> parameter_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Property> property_correspondingTo_requiringEntity, final Optional<Parameter> parameter_correspondingTo_requiringEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_requiringEntity, final Optional<Interface> interface_correspondingTo_requiringEntity) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Operation:operation_with_InterfaceProvidingRequiringEntity:requiringEntity";
    }
  }
  
  public RequiredRole_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.RequiredRole_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.eclipse.uml2.uml.Property> property_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceProperty_correspondingTo_role(affectedEObject), // correspondence source supplier
    		org.eclipse.uml2.uml.Property.class,
    		(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(property_correspondingTo_role.isPresent() ? property_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.Parameter> parameter_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceParameter_correspondingTo_role(affectedEObject, property_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, property_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(parameter_correspondingTo_role.isPresent() ? parameter_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_role(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_role.isPresent() ? implementation_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_role(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_role.isPresent() ? interface_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> operation_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperation_correspondingTo_role(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operation_correspondingTo_role.isPresent() ? operation_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.Property> property_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceProperty_correspondingTo_operationInterface(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.Property.class,
    		(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(property_correspondingTo_operationInterface.isPresent() ? property_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Parameter> parameter_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceParameter_correspondingTo_operationInterface(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(parameter_correspondingTo_operationInterface.isPresent() ? parameter_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_operationInterface(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_operationInterface.isPresent() ? implementation_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_operationInterface(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag9(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_operationInterface.isPresent() ? interface_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> operation_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperation_correspondingTo_operationInterface(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag10(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operation_correspondingTo_operationInterface.isPresent() ? operation_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Property> property_correspondingTo_requiringEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceProperty_correspondingTo_requiringEntity(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Property.class,
    		(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag11(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(property_correspondingTo_requiringEntity.isPresent() ? property_correspondingTo_requiringEntity.get() : null);
    	Optional<org.eclipse.uml2.uml.Parameter> parameter_correspondingTo_requiringEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceParameter_correspondingTo_requiringEntity(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag12(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(parameter_correspondingTo_requiringEntity.isPresent() ? parameter_correspondingTo_requiringEntity.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_requiringEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_requiringEntity(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity, parameter_correspondingTo_requiringEntity), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag13(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity, parameter_correspondingTo_requiringEntity), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_requiringEntity.isPresent() ? implementation_correspondingTo_requiringEntity.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_requiringEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_requiringEntity(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity, parameter_correspondingTo_requiringEntity, implementation_correspondingTo_requiringEntity), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag14(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity, parameter_correspondingTo_requiringEntity, implementation_correspondingTo_requiringEntity), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_requiringEntity.isPresent() ? interface_correspondingTo_requiringEntity.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> operation_correspondingTo_requiringEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperation_correspondingTo_requiringEntity(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity, parameter_correspondingTo_requiringEntity, implementation_correspondingTo_requiringEntity, interface_correspondingTo_requiringEntity), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag15(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity, parameter_correspondingTo_requiringEntity, implementation_correspondingTo_requiringEntity, interface_correspondingTo_requiringEntity), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operation_correspondingTo_requiringEntity.isPresent() ? operation_correspondingTo_requiringEntity.get() : null);
    userExecution.callRoutine1(affectedEObject, property_correspondingTo_role, parameter_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, operation_correspondingTo_role, property_correspondingTo_operationInterface, parameter_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, operation_correspondingTo_operationInterface, property_correspondingTo_requiringEntity, parameter_correspondingTo_requiringEntity, implementation_correspondingTo_requiringEntity, interface_correspondingTo_requiringEntity, operation_correspondingTo_requiringEntity, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
