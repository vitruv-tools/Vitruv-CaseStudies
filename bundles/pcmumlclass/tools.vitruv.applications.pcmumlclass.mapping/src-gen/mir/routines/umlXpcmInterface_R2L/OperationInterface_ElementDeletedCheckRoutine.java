package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
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
    
    public String getRetrieveTag4(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_repository) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_operationInterface(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag2(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Package:contractsPackage_with_OperationInterface:operationInterface";
    }
    
    public String getRetrieveTag3(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_operationInterface) {
      return "umlXpcmInterface_map_UML_and_PCM_correspondence_Interface:interface_with_Repository:repository";
    }
    
    public EObject getCorrepondenceSourceContractsPackage_correspondingTo_repository(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_repository) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceContractsPackage_correspondingTo_operationInterface(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public EObject getCorrepondenceSourceInterface_correspondingTo_repository(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_operationInterface) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<Interface> interface_correspondingTo_operationInterface, final Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_operationInterface, final Optional<Interface> interface_correspondingTo_repository, final Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_repository, @Extension final RoutinesFacade _routinesFacade) {
      Interface interface_ = null;
      org.eclipse.uml2.uml.Package contractsPackage_ = null;
      boolean _isPresent = interface_correspondingTo_operationInterface.isPresent();
      if (_isPresent) {
        interface_ = interface_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_1 = contractsPackage_correspondingTo_operationInterface.isPresent();
      if (_isPresent_1) {
        contractsPackage_ = contractsPackage_correspondingTo_operationInterface.get();
      }
      boolean _isPresent_2 = interface_correspondingTo_repository.isPresent();
      if (_isPresent_2) {
        interface_ = interface_correspondingTo_repository.get();
      }
      boolean _isPresent_3 = contractsPackage_correspondingTo_repository.isPresent();
      if (_isPresent_3) {
        contractsPackage_ = contractsPackage_correspondingTo_repository.get();
      }
      if (((interface_ != null) && (contractsPackage_ != null))) {
        _routinesFacade.operationInterface_DeleteMapping(interface_, contractsPackage_);
      }
    }
  }
  
  public OperationInterface_ElementDeletedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterface_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_ElementDeletedCheckRoutine with input:");
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
    	Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_operationInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceContractsPackage_correspondingTo_operationInterface(affectedEObject, interface_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(affectedEObject, interface_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(contractsPackage_correspondingTo_operationInterface.isPresent() ? contractsPackage_correspondingTo_operationInterface.get() : null);
    	Optional<org.eclipse.uml2.uml.Interface> interface_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceInterface_correspondingTo_repository(affectedEObject, interface_correspondingTo_operationInterface, contractsPackage_correspondingTo_operationInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(affectedEObject, interface_correspondingTo_operationInterface, contractsPackage_correspondingTo_operationInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(interface_correspondingTo_repository.isPresent() ? interface_correspondingTo_repository.get() : null);
    	Optional<org.eclipse.uml2.uml.Package> contractsPackage_correspondingTo_repository = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceContractsPackage_correspondingTo_repository(affectedEObject, interface_correspondingTo_operationInterface, contractsPackage_correspondingTo_operationInterface, interface_correspondingTo_repository), // correspondence source supplier
    		org.eclipse.uml2.uml.Package.class,
    		(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(affectedEObject, interface_correspondingTo_operationInterface, contractsPackage_correspondingTo_operationInterface, interface_correspondingTo_repository), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(contractsPackage_correspondingTo_repository.isPresent() ? contractsPackage_correspondingTo_repository.get() : null);
    userExecution.callRoutine1(affectedEObject, interface_correspondingTo_operationInterface, contractsPackage_correspondingTo_operationInterface, interface_correspondingTo_repository, contractsPackage_correspondingTo_repository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
