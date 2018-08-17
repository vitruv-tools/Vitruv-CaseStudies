package mir.routines.umlProvidedRoleGeneralizationReactions;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public String getRetrieveTag1(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      Classifier _general = umlGeneralization.getGeneral();
      return _general;
    }
    
    public String getRetrieveTag2(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag3(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public EObject getCorrepondenceSourcePcmProvided(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return umlGeneralization;
    }
    
    public void callRoutine1(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final Optional<OperationProvidedRole> pcmProvided, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmProvided.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<OperationProvidedRole, Boolean> _function = (OperationProvidedRole it) -> {
          OperationInterface _providedInterface__OperationProvidedRole = it.getProvidedInterface__OperationProvidedRole();
          return Boolean.valueOf((_providedInterface__OperationProvidedRole == pcmInterface));
        };
        final OperationProvidedRole pcmProvidedCandidate = IterableExtensions.<OperationProvidedRole>findFirst(Iterables.<OperationProvidedRole>filter(pcmComponent.getProvidedRoles_InterfaceProvidingEntity(), OperationProvidedRole.class), _function);
        if ((pcmProvidedCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingProvidedRole(umlGeneralization, pcmProvidedCandidate);
        } else {
          _routinesFacade.createCorrespondingProvidedRole(umlGeneralization, umlComponent);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleGeneralizationReactions.DetectOrCreateCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlGeneralization = umlGeneralization;this.umlComponent = umlComponent;
  }
  
  private Generalization umlGeneralization;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlGeneralization: " + this.umlGeneralization);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlGeneralization, umlComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlGeneralization, umlComponent), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlGeneralization, umlComponent, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlGeneralization, umlComponent, pcmComponent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    	Optional<org.palladiosimulator.pcm.repository.OperationProvidedRole> pcmProvided = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmProvided(umlGeneralization, umlComponent, pcmComponent, pcmInterface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    		(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlGeneralization, umlComponent, pcmComponent, pcmInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmProvided.isPresent() ? pcmProvided.get() : null);
    userExecution.callRoutine1(umlGeneralization, umlComponent, pcmComponent, pcmInterface, pcmProvided, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
