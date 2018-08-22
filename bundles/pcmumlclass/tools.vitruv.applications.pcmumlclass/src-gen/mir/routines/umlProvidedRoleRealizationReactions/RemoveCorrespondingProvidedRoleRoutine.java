package mir.routines.umlProvidedRoleRealizationReactions;

import java.io.IOException;
import mir.routines.umlProvidedRoleRealizationReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationProvidedRole pcmProvided) {
      return umlRealization;
    }
    
    public void update0Element(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationProvidedRole pcmProvided) {
      EList<NamedElement> _clients = umlRealization.getClients();
      _clients.remove(umlComponent);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public String getRetrieveTag1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getElement2(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationProvidedRole pcmProvided) {
      return pcmComponent;
    }
    
    public void update1Element(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationProvidedRole pcmProvided) {
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = pcmComponent.getProvidedRoles_InterfaceProvidingEntity();
      _providedRoles_InterfaceProvidingEntity.remove(pcmProvided);
    }
    
    public EObject getCorrepondenceSourcePcmProvided(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      return umlRealization;
    }
  }
  
  public RemoveCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleRealizationReactions.RemoveCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlRealization = umlRealization;this.umlComponent = umlComponent;
  }
  
  private InterfaceRealization umlRealization;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlRealization: " + this.umlRealization);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlRealization, umlComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlRealization, umlComponent), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    org.palladiosimulator.pcm.repository.OperationProvidedRole pcmProvided = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmProvided(umlRealization, umlComponent, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlRealization, umlComponent, pcmComponent), 
    	false // asserted
    	);
    if (pcmProvided == null) {
    	return false;
    }
    registerObjectUnderModification(pcmProvided);
    // val updatedElement userExecution.getElement1(umlRealization, umlComponent, pcmComponent, pcmProvided);
    userExecution.update0Element(umlRealization, umlComponent, pcmComponent, pcmProvided);
    
    // val updatedElement userExecution.getElement2(umlRealization, umlComponent, pcmComponent, pcmProvided);
    userExecution.update1Element(umlRealization, umlComponent, pcmComponent, pcmProvided);
    
    postprocessElements();
    
    return true;
  }
}
