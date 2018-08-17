package mir.routines.umlProvidedRoleGeneralizationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
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
    
    public EObject getCorrepondenceSourcePcmNewInterface(final Generalization umlGeneralization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationProvidedRole> pcmProvided) {
      return umlNewInterface;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Generalization umlGeneralization, final Interface umlNewInterface) {
      Classifier _specific = umlGeneralization.getSpecific();
      return _specific;
    }
    
    public void executeAction1(final Generalization umlGeneralization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationProvidedRole> pcmProvided, final Optional<OperationInterface> pcmNewInterface, @Extension final RoutinesFacade _routinesFacade) {
      if (((!pcmProvided.isPresent()) && pcmNewInterface.isPresent())) {
        Classifier _specific = umlGeneralization.getSpecific();
        _routinesFacade.createCorrespondingProvidedRole(umlGeneralization, ((org.eclipse.uml2.uml.Class) _specific));
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
              ("The general-type of a uml::Generalization in a pcm::OperationProvidedRole ~ uml::Generalization correspondence" + "has been set to a non-OperationInterface type. This is against convention and the corresponding OperationProvidedRole will be deleted."));
            _routinesFacade.deleteCorrespondingProvidedRole(umlGeneralization);
          }
        }
      }
    }
    
    public String getRetrieveTag1(final Generalization umlGeneralization, final Interface umlNewInterface) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final Generalization umlGeneralization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public String getRetrieveTag3(final Generalization umlGeneralization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent, final Optional<OperationProvidedRole> pcmProvided) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourcePcmProvided(final Generalization umlGeneralization, final Interface umlNewInterface, final InterfaceProvidingRequiringEntity pcmComponent) {
      return umlGeneralization;
    }
  }
  
  public ChangeTypeOfCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization umlGeneralization, final Interface umlNewInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleGeneralizationReactions.ChangeTypeOfCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlGeneralization = umlGeneralization;this.umlNewInterface = umlNewInterface;
  }
  
  private Generalization umlGeneralization;
  
  private Interface umlNewInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlGeneralization: " + this.umlGeneralization);
    getLogger().debug("   umlNewInterface: " + this.umlNewInterface);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlGeneralization, umlNewInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlGeneralization, umlNewInterface), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    	Optional<org.palladiosimulator.pcm.repository.OperationProvidedRole> pcmProvided = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmProvided(umlGeneralization, umlNewInterface, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    		(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlGeneralization, umlNewInterface, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmProvided.isPresent() ? pcmProvided.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmNewInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmNewInterface(umlGeneralization, umlNewInterface, pcmComponent, pcmProvided), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlGeneralization, umlNewInterface, pcmComponent, pcmProvided), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmNewInterface.isPresent() ? pcmNewInterface.get() : null);
    userExecution.executeAction1(umlGeneralization, umlNewInterface, pcmComponent, pcmProvided, pcmNewInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
