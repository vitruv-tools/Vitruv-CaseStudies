package mir.routines.umlProvidedRoleRealizationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlProvidedRoleRealizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmNewInterface(final InterfaceRealization umlRealization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationProvidedRole> pcmProvided) {
      return umlNewInterface;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final InterfaceRealization umlRealization, final Interface umlNewInterface) {
      BehavioredClassifier _implementingClassifier = umlRealization.getImplementingClassifier();
      return _implementingClassifier;
    }
    
    public void executeAction1(final InterfaceRealization umlRealization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationProvidedRole> pcmProvided, final Optional<OperationInterface> pcmNewInterface, @Extension final RoutinesFacade _routinesFacade) {
      if (((!pcmProvided.isPresent()) && pcmNewInterface.isPresent())) {
        BehavioredClassifier _implementingClassifier = umlRealization.getImplementingClassifier();
        _routinesFacade.createCorrespondingProvidedRole(umlRealization, ((org.eclipse.uml2.uml.Class) _implementingClassifier));
      } else {
        if ((pcmProvided.isPresent() && pcmNewInterface.isPresent())) {
          OperationProvidedRole _get = pcmProvided.get();
          _get.setProvidedInterface__OperationProvidedRole(pcmNewInterface.get());
        } else {
          if ((pcmProvided.isPresent() && (umlNewInterface == null))) {
            OperationProvidedRole _get_1 = pcmProvided.get();
            _get_1.setProvidedInterface__OperationProvidedRole(null);
          } else {
            this.getLogger().warn(
              ("The general-type of a uml::InterfaceRealization in a pcm::OperationProvidedRole ~ uml::InterfaceRealization correspondence" + "has been set to a non-OperationInterface type. This is against convention and the corresponding OperationProvidedRole will be deleted."));
            _routinesFacade.deleteCorrespondingProvidedRole(umlRealization);
          }
        }
      }
    }
    
    public String getRetrieveTag1(final InterfaceRealization umlRealization, final Interface umlNewInterface) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final InterfaceRealization umlRealization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public String getRetrieveTag3(final InterfaceRealization umlRealization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationProvidedRole> pcmProvided) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourcePcmProvided(final InterfaceRealization umlRealization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return umlRealization;
    }
  }
  
  public ChangeTypeOfCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization umlRealization, final Interface umlNewInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleRealizationReactions.ChangeTypeOfCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlRealization = umlRealization;this.umlNewInterface = umlNewInterface;
  }
  
  private InterfaceRealization umlRealization;
  
  private Interface umlNewInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlRealization: " + this.umlRealization);
    getLogger().debug("   umlNewInterface: " + this.umlNewInterface);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlRealization, umlNewInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlRealization, umlNewInterface), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    	Optional<org.palladiosimulator.pcm.repository.OperationProvidedRole> pcmProvided = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmProvided(umlRealization, umlNewInterface, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    		(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlRealization, umlNewInterface, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmProvided.isPresent() ? pcmProvided.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmNewInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmNewInterface(umlRealization, umlNewInterface, pcmComponent, pcmProvided), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlRealization, umlNewInterface, pcmComponent, pcmProvided), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmNewInterface.isPresent() ? pcmNewInterface.get() : null);
    userExecution.executeAction1(umlRealization, umlNewInterface, pcmComponent, pcmProvided, pcmNewInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
