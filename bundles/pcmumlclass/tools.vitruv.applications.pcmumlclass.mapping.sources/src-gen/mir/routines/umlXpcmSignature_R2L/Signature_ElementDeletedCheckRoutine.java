package mir.routines.umlXpcmSignature_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmSignature_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Signature_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private Signature_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_DataType:returnType";
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_DataType:returnType";
    }
    
    public EObject getCorrepondenceSourceOperation_correspondingTo_operationSignature(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_returnType(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType) {
      return affectedEObject;
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_DataType:returnType";
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType, final Optional<Interface> interface_correspondingTo_returnType) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationSignature:operationSignature";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationSignature:operationSignature";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationSignature:operationSignature";
    }
    
    public EObject getCorrepondenceSourceOperation_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType, final Optional<Interface> interface_correspondingTo_returnType) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType, final Optional<Interface> interface_correspondingTo_returnType, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Parameter> returnParameter_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_operationInterface, @Extension final RoutinesFacade _routinesFacade) {
      Operation operation_ = null;
      Parameter returnParameter_ = null;
      Interface interface_ = null;
      boolean _isPresent = operation_correspondingTo_operationSignature.isPresent();
      if (_isPresent) {
        operation_ = operation_correspondingTo_operationSignature.get();
      }
      boolean _isPresent_1 = returnParameter_correspondingTo_operationSignature.isPresent();
      if (_isPresent_1) {
        returnParameter_ = returnParameter_correspondingTo_operationSignature.get();
      }
      boolean _isPresent_2 = interface_correspondingTo_operationSignature.isPresent();
      if (_isPresent_2) {
        interface_ = interface_correspondingTo_operationSignature.get();
      }
      boolean _isPresent_3 = operation_correspondingTo_returnType.isPresent();
      if (_isPresent_3) {
        operation_ = operation_correspondingTo_returnType.get();
      }
      boolean _isPresent_4 = returnParameter_correspondingTo_returnType.isPresent();
      if (_isPresent_4) {
        returnParameter_ = returnParameter_correspondingTo_returnType.get();
      }
      boolean _isPresent_5 = interface_correspondingTo_returnType.isPresent();
      if (_isPresent_5) {
        interface_ = interface_correspondingTo_returnType.get();
      }
      boolean _isPresent_6 = operation_correspondingTo_operationInterface.isPresent();
      if (_isPresent_6) {
        operation_ = operation_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_7 = returnParameter_correspondingTo_operationInterface.isPresent();
      if (_isPresent_7) {
        returnParameter_ = returnParameter_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_8 = interface_correspondingTo_operationInterface.isPresent();
      if (_isPresent_8) {
        interface_ = interface_correspondingTo_operationInterface.get();
      }
      if ((((operation_ != null) && (returnParameter_ != null)) && (interface_ != null))) {
        _routinesFacade.signature_DeleteMapping(operation_, returnParameter_, interface_);
      }
    }
    
    public EObject getCorrepondenceSourceReturnParameter_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType, final Optional<Interface> interface_correspondingTo_returnType, final Optional<Operation> operation_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType, final Optional<Interface> interface_correspondingTo_returnType, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Parameter> returnParameter_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperation_correspondingTo_returnType(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceReturnParameter_correspondingTo_operationSignature(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_operationSignature(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceReturnParameter_correspondingTo_returnType(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType) {
      return affectedEObject;
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType, final Optional<Interface> interface_correspondingTo_returnType, final Optional<Operation> operation_correspondingTo_operationInterface) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag9(final EObject affectedEObject, final Optional<Operation> operation_correspondingTo_operationSignature, final Optional<Parameter> returnParameter_correspondingTo_operationSignature, final Optional<Interface> interface_correspondingTo_operationSignature, final Optional<Operation> operation_correspondingTo_returnType, final Optional<Parameter> returnParameter_correspondingTo_returnType, final Optional<Interface> interface_correspondingTo_returnType, final Optional<Operation> operation_correspondingTo_operationInterface, final Optional<Parameter> returnParameter_correspondingTo_operationInterface) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
  }
  
  public Signature_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_R2L.Signature_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.eclipse.uml2.uml.Operation> operation_correspondingTo_operationSignature = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperation_correspondingTo_operationSignature(affectedEObject), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operation_correspondingTo_operationSignature.isPresent() ? operation_correspondingTo_operationSignature.get() : null);
    	Optional<org.eclipse.uml2.uml.Parameter> returnParameter_correspondingTo_operationSignature = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnParameter_correspondingTo_operationSignature(affectedEObject, operation_correspondingTo_operationSignature), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, operation_correspondingTo_operationSignature), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnParameter_correspondingTo_operationSignature.isPresent() ? returnParameter_correspondingTo_operationSignature.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_operationSignature = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_operationSignature(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_operationSignature.isPresent() ? interface_correspondingTo_operationSignature.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> operation_correspondingTo_returnType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperation_correspondingTo_returnType(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operation_correspondingTo_returnType.isPresent() ? operation_correspondingTo_returnType.get() : null);
    	Optional<org.eclipse.uml2.uml.Parameter> returnParameter_correspondingTo_returnType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnParameter_correspondingTo_returnType(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnParameter_correspondingTo_returnType.isPresent() ? returnParameter_correspondingTo_returnType.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_returnType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_returnType(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_returnType.isPresent() ? interface_correspondingTo_returnType.get() : null);
    	Optional<org.eclipse.uml2.uml.Operation> operation_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperation_correspondingTo_operationInterface(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType, interface_correspondingTo_returnType), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType, interface_correspondingTo_returnType), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operation_correspondingTo_operationInterface.isPresent() ? operation_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Parameter> returnParameter_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnParameter_correspondingTo_operationInterface(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType, interface_correspondingTo_returnType, operation_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType, interface_correspondingTo_returnType, operation_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnParameter_correspondingTo_operationInterface.isPresent() ? returnParameter_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_operationInterface(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType, interface_correspondingTo_returnType, operation_correspondingTo_operationInterface, returnParameter_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag9(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType, interface_correspondingTo_returnType, operation_correspondingTo_operationInterface, returnParameter_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_operationInterface.isPresent() ? interface_correspondingTo_operationInterface.get() : null);
    userExecution.callRoutine1(affectedEObject, operation_correspondingTo_operationSignature, returnParameter_correspondingTo_operationSignature, interface_correspondingTo_operationSignature, operation_correspondingTo_returnType, returnParameter_correspondingTo_returnType, interface_correspondingTo_returnType, operation_correspondingTo_operationInterface, returnParameter_correspondingTo_operationInterface, interface_correspondingTo_operationInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
