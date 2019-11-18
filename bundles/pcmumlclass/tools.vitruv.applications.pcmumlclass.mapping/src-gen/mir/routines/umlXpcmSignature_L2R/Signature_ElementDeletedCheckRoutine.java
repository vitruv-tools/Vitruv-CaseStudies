package mir.routines.umlXpcmSignature_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmSignature_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationSignature:operationSignature";
    }
    
    public EObject getCorrepondenceSourceOperationSignature_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter, final Optional<OperationInterface> operationInterface_correspondingTo_returnParameter) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceReturnType_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter, final Optional<OperationInterface> operationInterface_correspondingTo_returnParameter, final Optional<OperationSignature> operationSignature_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public String getRetrieveTag5(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_DataType:returnType";
    }
    
    public String getRetrieveTag6(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Parameter:returnParameter_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag7(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter, final Optional<OperationInterface> operationInterface_correspondingTo_returnParameter) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationSignature:operationSignature";
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationSignature:operationSignature";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_DataType:returnType";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Operation:operation_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceReturnType_correspondingTo_operation(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_operation(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_returnParameter(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationSignature_correspondingTo_returnParameter(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter, final Optional<OperationInterface> operationInterface_correspondingTo_returnParameter, final Optional<OperationSignature> operationSignature_correspondingTo_interface, final Optional<DataType> returnType_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_interface, @Extension final RoutinesFacade _routinesFacade) {
      OperationSignature operationSignature_ = null;
      DataType returnType_ = null;
      OperationInterface operationInterface_ = null;
      boolean _isPresent = operationSignature_correspondingTo_operation.isPresent();
      if (_isPresent) {
        operationSignature_ = operationSignature_correspondingTo_operation.get();
      }
      boolean _isPresent_1 = returnType_correspondingTo_operation.isPresent();
      if (_isPresent_1) {
        returnType_ = returnType_correspondingTo_operation.get();
      }
      boolean _isPresent_2 = operationInterface_correspondingTo_operation.isPresent();
      if (_isPresent_2) {
        operationInterface_ = operationInterface_correspondingTo_operation.get();
      }
      boolean _isPresent_3 = operationSignature_correspondingTo_returnParameter.isPresent();
      if (_isPresent_3) {
        operationSignature_ = operationSignature_correspondingTo_returnParameter.get();
      }
      boolean _isPresent_4 = returnType_correspondingTo_returnParameter.isPresent();
      if (_isPresent_4) {
        returnType_ = returnType_correspondingTo_returnParameter.get();
      }
      boolean _isPresent_5 = operationInterface_correspondingTo_returnParameter.isPresent();
      if (_isPresent_5) {
        operationInterface_ = operationInterface_correspondingTo_returnParameter.get();
      }
      boolean _isPresent_6 = operationSignature_correspondingTo_interface.isPresent();
      if (_isPresent_6) {
        operationSignature_ = operationSignature_correspondingTo_interface.get();
      }
      boolean _isPresent_7 = returnType_correspondingTo_interface.isPresent();
      if (_isPresent_7) {
        returnType_ = returnType_correspondingTo_interface.get();
      }
      boolean _isPresent_8 = operationInterface_correspondingTo_interface.isPresent();
      if (_isPresent_8) {
        operationInterface_ = operationInterface_correspondingTo_interface.get();
      }
      if ((((operationSignature_ != null) && (returnType_ != null)) && (operationInterface_ != null))) {
        _routinesFacade.signature_DeleteMapping(operationSignature_, returnType_, operationInterface_);
      }
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter, final Optional<OperationInterface> operationInterface_correspondingTo_returnParameter, final Optional<OperationSignature> operationSignature_correspondingTo_interface, final Optional<DataType> returnType_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceReturnType_correspondingTo_returnParameter(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationSignature_correspondingTo_operation(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag8(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter, final Optional<OperationInterface> operationInterface_correspondingTo_returnParameter, final Optional<OperationSignature> operationSignature_correspondingTo_interface) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_DataType:returnType";
    }
    
    public String getRetrieveTag9(final EObject affectedEObject, final Optional<OperationSignature> operationSignature_correspondingTo_operation, final Optional<DataType> returnType_correspondingTo_operation, final Optional<OperationInterface> operationInterface_correspondingTo_operation, final Optional<OperationSignature> operationSignature_correspondingTo_returnParameter, final Optional<DataType> returnType_correspondingTo_returnParameter, final Optional<OperationInterface> operationInterface_correspondingTo_returnParameter, final Optional<OperationSignature> operationSignature_correspondingTo_interface, final Optional<DataType> returnType_correspondingTo_interface) {
      return "umlXpcmSignature_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
  }
  
  public Signature_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_L2R.Signature_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<org.palladiosimulator.pcm.repository.OperationSignature> operationSignature_correspondingTo_operation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationSignature_correspondingTo_operation(affectedEObject), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationSignature.class,
    		(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationSignature_correspondingTo_operation.isPresent() ? operationSignature_correspondingTo_operation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.DataType> returnType_correspondingTo_operation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnType_correspondingTo_operation(affectedEObject, operationSignature_correspondingTo_operation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.DataType.class,
    		(org.palladiosimulator.pcm.repository.DataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, operationSignature_correspondingTo_operation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnType_correspondingTo_operation.isPresent() ? returnType_correspondingTo_operation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_operation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_operation(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_operation.isPresent() ? operationInterface_correspondingTo_operation.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationSignature> operationSignature_correspondingTo_returnParameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationSignature_correspondingTo_returnParameter(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationSignature.class,
    		(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationSignature_correspondingTo_returnParameter.isPresent() ? operationSignature_correspondingTo_returnParameter.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.DataType> returnType_correspondingTo_returnParameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnType_correspondingTo_returnParameter(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.DataType.class,
    		(org.palladiosimulator.pcm.repository.DataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag5(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnType_correspondingTo_returnParameter.isPresent() ? returnType_correspondingTo_returnParameter.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_returnParameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_returnParameter(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag6(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_returnParameter.isPresent() ? operationInterface_correspondingTo_returnParameter.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationSignature> operationSignature_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationSignature_correspondingTo_interface(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter, operationInterface_correspondingTo_returnParameter), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationSignature.class,
    		(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag7(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter, operationInterface_correspondingTo_returnParameter), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationSignature_correspondingTo_interface.isPresent() ? operationSignature_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.DataType> returnType_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnType_correspondingTo_interface(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter, operationInterface_correspondingTo_returnParameter, operationSignature_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.DataType.class,
    		(org.palladiosimulator.pcm.repository.DataType _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag8(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter, operationInterface_correspondingTo_returnParameter, operationSignature_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnType_correspondingTo_interface.isPresent() ? returnType_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_interface(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter, operationInterface_correspondingTo_returnParameter, operationSignature_correspondingTo_interface, returnType_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag9(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter, operationInterface_correspondingTo_returnParameter, operationSignature_correspondingTo_interface, returnType_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_interface.isPresent() ? operationInterface_correspondingTo_interface.get() : null);
    userExecution.callRoutine1(affectedEObject, operationSignature_correspondingTo_operation, returnType_correspondingTo_operation, operationInterface_correspondingTo_operation, operationSignature_correspondingTo_returnParameter, returnType_correspondingTo_returnParameter, operationInterface_correspondingTo_returnParameter, operationSignature_correspondingTo_interface, returnType_correspondingTo_interface, operationInterface_correspondingTo_interface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
