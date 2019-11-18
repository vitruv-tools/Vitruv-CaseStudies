package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<OperationInterface> parentInterface_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_generalization) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:parentInterface";
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_interface(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceParentInterface_correspondingTo_interface(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceOperationInterface_correspondingTo_generalization(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<OperationInterface> parentInterface_correspondingTo_interface) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:parentInterface";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<OperationInterface> parentInterface_correspondingTo_interface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Generalization:generalization_with_OperationInterface:operationInterface";
    }
    
    public EObject getCorrepondenceSourceParentInterface_correspondingTo_generalization(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<OperationInterface> parentInterface_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_generalization) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<OperationInterface> operationInterface_correspondingTo_interface, final Optional<OperationInterface> parentInterface_correspondingTo_interface, final Optional<OperationInterface> operationInterface_correspondingTo_generalization, final Optional<OperationInterface> parentInterface_correspondingTo_generalization, @Extension final RoutinesFacade _routinesFacade) {
      OperationInterface operationInterface_ = null;
      OperationInterface parentInterface_ = null;
      boolean _isPresent = operationInterface_correspondingTo_interface.isPresent();
      if (_isPresent) {
        operationInterface_ = operationInterface_correspondingTo_interface.get();
      }
      boolean _isPresent_1 = parentInterface_correspondingTo_interface.isPresent();
      if (_isPresent_1) {
        parentInterface_ = parentInterface_correspondingTo_interface.get();
      }
      boolean _isPresent_2 = operationInterface_correspondingTo_generalization.isPresent();
      if (_isPresent_2) {
        operationInterface_ = operationInterface_correspondingTo_generalization.get();
      }
      boolean _isPresent_3 = parentInterface_correspondingTo_generalization.isPresent();
      if (_isPresent_3) {
        parentInterface_ = parentInterface_correspondingTo_generalization.get();
      }
      if (((operationInterface_ != null) && (parentInterface_ != null))) {
        _routinesFacade.operationInterfaceParent_DeleteMapping(operationInterface_, parentInterface_);
      }
    }
  }
  
  public OperationInterfaceParent_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_ElementDeletedCheckRoutine with input:");
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
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> parentInterface_correspondingTo_interface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceParentInterface_correspondingTo_interface(affectedEObject, operationInterface_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, operationInterface_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(parentInterface_correspondingTo_interface.isPresent() ? parentInterface_correspondingTo_interface.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> operationInterface_correspondingTo_generalization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceOperationInterface_correspondingTo_generalization(affectedEObject, operationInterface_correspondingTo_interface, parentInterface_correspondingTo_interface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, operationInterface_correspondingTo_interface, parentInterface_correspondingTo_interface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(operationInterface_correspondingTo_generalization.isPresent() ? operationInterface_correspondingTo_generalization.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> parentInterface_correspondingTo_generalization = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceParentInterface_correspondingTo_generalization(affectedEObject, operationInterface_correspondingTo_interface, parentInterface_correspondingTo_interface, operationInterface_correspondingTo_generalization), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, operationInterface_correspondingTo_interface, parentInterface_correspondingTo_interface, operationInterface_correspondingTo_generalization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(parentInterface_correspondingTo_generalization.isPresent() ? parentInterface_correspondingTo_generalization.get() : null);
    userExecution.callRoutine1(affectedEObject, operationInterface_correspondingTo_interface, parentInterface_correspondingTo_interface, operationInterface_correspondingTo_generalization, parentInterface_correspondingTo_generalization, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
