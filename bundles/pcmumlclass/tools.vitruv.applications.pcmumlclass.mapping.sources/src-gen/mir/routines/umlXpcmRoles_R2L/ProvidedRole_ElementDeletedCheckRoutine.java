package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_providingEntity(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_providingEntity) {
      return affectedEObject;
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_InterfaceRealization:interfaceRealization_with_OperationProvidedRole:role";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_OperationProvidedRole:role";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_OperationProvidedRole:role";
    }
    
    public EObject getCorrepondenceSourceInterfaceRealization_correspondingTo_role(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_providingEntity(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_providingEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_providingEntity) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_role(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_providingEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_providingEntity, final Optional<Interface> interface_correspondingTo_providingEntity, @Extension final RoutinesFacade _routinesFacade) {
      InterfaceRealization interfaceRealization_ = null;
      org.eclipse.uml2.uml.Class implementation_ = null;
      Interface interface_ = null;
      boolean _isPresent = interfaceRealization_correspondingTo_role.isPresent();
      if (_isPresent) {
        interfaceRealization_ = interfaceRealization_correspondingTo_role.get();
      }
      boolean _isPresent_1 = implementation_correspondingTo_role.isPresent();
      if (_isPresent_1) {
        implementation_ = implementation_correspondingTo_role.get();
      }
      boolean _isPresent_2 = interface_correspondingTo_role.isPresent();
      if (_isPresent_2) {
        interface_ = interface_correspondingTo_role.get();
      }
      boolean _isPresent_3 = interfaceRealization_correspondingTo_operationInterface.isPresent();
      if (_isPresent_3) {
        interfaceRealization_ = interfaceRealization_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_4 = implementation_correspondingTo_operationInterface.isPresent();
      if (_isPresent_4) {
        implementation_ = implementation_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_5 = interface_correspondingTo_operationInterface.isPresent();
      if (_isPresent_5) {
        interface_ = interface_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_6 = interfaceRealization_correspondingTo_providingEntity.isPresent();
      if (_isPresent_6) {
        interfaceRealization_ = interfaceRealization_correspondingTo_providingEntity.get();
      }
      boolean _isPresent_7 = implementation_correspondingTo_providingEntity.isPresent();
      if (_isPresent_7) {
        implementation_ = implementation_correspondingTo_providingEntity.get();
      }
      boolean _isPresent_8 = interface_correspondingTo_providingEntity.isPresent();
      if (_isPresent_8) {
        interface_ = interface_correspondingTo_providingEntity.get();
      }
      if ((((interfaceRealization_ != null) && (implementation_ != null)) && (interface_ != null))) {
        _routinesFacade.providedRole_DeleteMapping(interfaceRealization_, implementation_, interface_);
      }
    }
    
    public EObject getCorrepondenceSourceInterfaceRealization_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_role(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role) {
      return affectedEObject;
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_providingEntity) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Class:implementation_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public String getRetrieveTag9(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_providingEntity, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_providingEntity) {
      return "umlXpcmRoles_map_UML_and_PCM_correspondence_Interface:interface_with_InterfaceProvidingRequiringEntity:providingEntity";
    }
    
    public EObject getCorrepondenceSourceImplementation_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterfaceRealization_correspondingTo_providingEntity(final EObject affectedEObject, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_role, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role, final Optional<Interface> interface_correspondingTo_role, final Optional<InterfaceRealization> interfaceRealization_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return affectedEObject;
    }
  }
  
  public ProvidedRole_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.ProvidedRole_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.eclipse.uml2.uml.InterfaceRealization> interfaceRealization_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterfaceRealization_correspondingTo_role(affectedEObject), // correspondence source supplier
    		org.eclipse.uml2.uml.InterfaceRealization.class,
    		(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interfaceRealization_correspondingTo_role.isPresent() ? interfaceRealization_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_role(affectedEObject, interfaceRealization_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, interfaceRealization_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_role.isPresent() ? implementation_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_role = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_role(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_role.isPresent() ? interface_correspondingTo_role.get() : null);
    	Optional<org.eclipse.uml2.uml.InterfaceRealization> interfaceRealization_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterfaceRealization_correspondingTo_operationInterface(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role), // correspondence source supplier
    		org.eclipse.uml2.uml.InterfaceRealization.class,
    		(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interfaceRealization_correspondingTo_operationInterface.isPresent() ? interfaceRealization_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_operationInterface(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_operationInterface.isPresent() ? implementation_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_operationInterface(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_operationInterface.isPresent() ? interface_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.InterfaceRealization> interfaceRealization_correspondingTo_providingEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterfaceRealization_correspondingTo_providingEntity(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.InterfaceRealization.class,
    		(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interfaceRealization_correspondingTo_providingEntity.isPresent() ? interfaceRealization_correspondingTo_providingEntity.get() : null);
    	Optional<org.eclipse.uml2.uml.Class> implementation_correspondingTo_providingEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceImplementation_correspondingTo_providingEntity(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, interfaceRealization_correspondingTo_providingEntity), // correspondence source supplier
    		org.eclipse.uml2.uml.Class.class,
    		(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, interfaceRealization_correspondingTo_providingEntity), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(implementation_correspondingTo_providingEntity.isPresent() ? implementation_correspondingTo_providingEntity.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_providingEntity = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_providingEntity(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, interfaceRealization_correspondingTo_providingEntity, implementation_correspondingTo_providingEntity), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag9(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, interfaceRealization_correspondingTo_providingEntity, implementation_correspondingTo_providingEntity), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_providingEntity.isPresent() ? interface_correspondingTo_providingEntity.get() : null);
    userExecution.callRoutine1(affectedEObject, interfaceRealization_correspondingTo_role, implementation_correspondingTo_role, interface_correspondingTo_role, interfaceRealization_correspondingTo_operationInterface, implementation_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, interfaceRealization_correspondingTo_providingEntity, implementation_correspondingTo_providingEntity, interface_correspondingTo_providingEntity, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
