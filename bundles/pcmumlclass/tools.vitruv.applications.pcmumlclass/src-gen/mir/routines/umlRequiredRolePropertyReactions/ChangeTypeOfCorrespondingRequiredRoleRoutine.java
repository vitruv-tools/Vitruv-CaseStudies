package mir.routines.umlRequiredRolePropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRequiredRolePropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmNewInterface(final Property umlProperty, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationRequiredRole> pcmRequired) {
      return umlNewInterface;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Property umlProperty, final Interface umlNewInterface) {
      Element _owner = umlProperty.getOwner();
      return _owner;
    }
    
    public void executeAction1(final Property umlProperty, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationRequiredRole> pcmRequired, final Optional<OperationInterface> pcmNewInterface, @Extension final RoutinesFacade _routinesFacade) {
      if (((!pcmRequired.isPresent()) && pcmNewInterface.isPresent())) {
        Element _owner = umlProperty.getOwner();
        _routinesFacade.createCorrespondingRequiredRole(umlProperty, ((org.eclipse.uml2.uml.Class) _owner));
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
            _routinesFacade.deleteCorrespondingRequiredRole(umlProperty);
          }
        }
      }
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Property umlProperty, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final Interface umlNewInterface) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public String getRetrieveTag3(final Property umlProperty, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationRequiredRole> pcmRequired) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public ChangeTypeOfCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final Interface umlNewInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRolePropertyReactions.ChangeTypeOfCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlNewInterface = umlNewInterface;
  }
  
  private Property umlProperty;
  
  private Interface umlNewInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlNewInterface: " + this.umlNewInterface);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlProperty, umlNewInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, umlNewInterface), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> pcmRequired = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRequired(umlProperty, umlNewInterface, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlProperty, umlNewInterface, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRequired.isPresent() ? pcmRequired.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmNewInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmNewInterface(umlProperty, umlNewInterface, pcmComponent, pcmRequired), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlProperty, umlNewInterface, pcmComponent, pcmRequired), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmNewInterface.isPresent() ? pcmNewInterface.get() : null);
    userExecution.executeAction1(umlProperty, umlNewInterface, pcmComponent, pcmRequired, pcmNewInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
