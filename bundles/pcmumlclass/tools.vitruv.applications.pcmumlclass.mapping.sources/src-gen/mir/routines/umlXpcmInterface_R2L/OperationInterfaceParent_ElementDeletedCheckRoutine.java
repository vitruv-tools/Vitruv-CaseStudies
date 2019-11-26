package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterfaceParent_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private OperationInterfaceParent_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Generalization> generalization_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_parentInterface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:parentInterface";
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_operationInterface(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceGeneralization_correspondingTo_parentInterface(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Generalization> generalization_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_parentInterface) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Generalization> generalization_correspondingTo_operationInterface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:parentInterface";
    }
    
    public EObject getCorrepondenceSourceGeneralization_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_parentInterface(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Generalization> generalization_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<Generalization> generalization_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_parentInterface, final Optional<Generalization> generalization_correspondingTo_parentInterface, @Extension final RoutinesFacade _routinesFacade) {
      Interface interface_ = null;
      Generalization generalization_ = null;
      boolean _isPresent = interface_correspondingTo_operationInterface.isPresent();
      if (_isPresent) {
        interface_ = interface_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_1 = generalization_correspondingTo_operationInterface.isPresent();
      if (_isPresent_1) {
        generalization_ = generalization_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_2 = interface_correspondingTo_parentInterface.isPresent();
      if (_isPresent_2) {
        interface_ = interface_correspondingTo_parentInterface.get();
      }
      boolean _isPresent_3 = generalization_correspondingTo_parentInterface.isPresent();
      if (_isPresent_3) {
        generalization_ = generalization_correspondingTo_parentInterface.get();
      }
      if (((interface_ != null) && (generalization_ != null))) {
        _routinesFacade.operationInterfaceParent_DeleteMapping(interface_, generalization_);
      }
    }
  }
  
  public OperationInterfaceParent_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterfaceParent_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_operationInterface(affectedEObject), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_operationInterface.isPresent() ? interface_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Generalization> generalization_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceGeneralization_correspondingTo_operationInterface(affectedEObject, interface_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Generalization.class,
    		(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, interface_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(generalization_correspondingTo_operationInterface.isPresent() ? generalization_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_parentInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_parentInterface(affectedEObject, interface_correspondingTo_operationInterface, generalization_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, interface_correspondingTo_operationInterface, generalization_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_parentInterface.isPresent() ? interface_correspondingTo_parentInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Generalization> generalization_correspondingTo_parentInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceGeneralization_correspondingTo_parentInterface(affectedEObject, interface_correspondingTo_operationInterface, generalization_correspondingTo_operationInterface, interface_correspondingTo_parentInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Generalization.class,
    		(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, interface_correspondingTo_operationInterface, generalization_correspondingTo_operationInterface, interface_correspondingTo_parentInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(generalization_correspondingTo_parentInterface.isPresent() ? generalization_correspondingTo_parentInterface.get() : null);
    userExecution.callRoutine1(affectedEObject, interface_correspondingTo_operationInterface, generalization_correspondingTo_operationInterface, interface_correspondingTo_parentInterface, generalization_correspondingTo_parentInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
