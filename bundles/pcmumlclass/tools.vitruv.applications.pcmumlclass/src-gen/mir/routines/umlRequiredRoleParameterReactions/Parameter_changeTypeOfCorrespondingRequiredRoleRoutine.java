package mir.routines.umlRequiredRoleParameterReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Parameter_changeTypeOfCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_changeTypeOfCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmNewInterface(final Parameter umlParameter, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationRequiredRole> pcmRequired) {
      return umlNewInterface;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Parameter umlParameter, final Interface umlNewInterface) {
      Element _owner = umlParameter.getOwner();
      return _owner;
    }
    
    public void executeAction1(final Parameter umlParameter, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationRequiredRole> pcmRequired, final Optional<OperationInterface> pcmNewInterface, @Extension final RoutinesFacade _routinesFacade) {
      if (((!pcmRequired.isPresent()) && pcmNewInterface.isPresent())) {
        Element _owner = umlParameter.getOwner();
        _routinesFacade.parameter_createCorrespondingRequiredRole(umlParameter, ((Operation) _owner));
      } else {
        if ((pcmRequired.isPresent() && pcmNewInterface.isPresent())) {
          OperationRequiredRole _get = pcmRequired.get();
          _get.setRequiredInterface__OperationRequiredRole(pcmNewInterface.get());
        } else {
          if ((pcmRequired.isPresent() && (umlNewInterface == null))) {
            OperationRequiredRole _get_1 = pcmRequired.get();
            _get_1.setRequiredInterface__OperationRequiredRole(null);
          } else {
            this.getLogger().warn(
              ("The type of a uml::Property in a pcm::OperationRequiredRole ~ uml::Property correspondence" + "has been set to a non-OperationInterface type. This is against convention and the corresponding OperationRequiredRole will be deleted."));
            _routinesFacade.parameter_deleteCorrespondingRequiredRole(umlParameter);
          }
        }
      }
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Parameter umlParameter, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final Interface umlNewInterface) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public String getRetrieveTag3(final Parameter umlParameter, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationRequiredRole> pcmRequired) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public Parameter_changeTypeOfCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final Interface umlNewInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_changeTypeOfCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.umlNewInterface = umlNewInterface;
  }
  
  private Parameter umlParameter;
  
  private Interface umlNewInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_changeTypeOfCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   umlNewInterface: " + this.umlNewInterface);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlParameter, umlNewInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, umlNewInterface), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> pcmRequired = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRequired(umlParameter, umlNewInterface, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlParameter, umlNewInterface, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRequired.isPresent() ? pcmRequired.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmNewInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmNewInterface(umlParameter, umlNewInterface, pcmComponent, pcmRequired), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlParameter, umlNewInterface, pcmComponent, pcmRequired), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmNewInterface.isPresent() ? pcmNewInterface.get() : null);
    userExecution.executeAction1(umlParameter, umlNewInterface, pcmComponent, pcmRequired, pcmNewInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
