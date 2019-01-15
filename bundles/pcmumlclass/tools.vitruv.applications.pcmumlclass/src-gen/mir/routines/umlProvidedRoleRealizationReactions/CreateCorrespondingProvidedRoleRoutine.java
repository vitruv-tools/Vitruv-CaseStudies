package mir.routines.umlProvidedRoleRealizationReactions;

import java.io.IOException;
import mir.routines.umlProvidedRoleRealizationReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public EObject getCorrepondenceSource1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return umlRealization;
    }
    
    public String getRetrieveTag1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      Interface _contract = umlRealization.getContract();
      return _contract;
    }
    
    public String getRetrieveTag2(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      return umlRealization;
    }
    
    public String getRetrieveTag3(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public String getTag1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public void updatePcmProvidedElement(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      pcmProvided.setProvidedInterface__OperationProvidedRole(pcmInterface);
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = pcmComponent.getProvidedRoles_InterfaceProvidingEntity();
      _providedRoles_InterfaceProvidingEntity.add(pcmProvided);
    }
    
    public void callRoutine1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeNameOfCorrespondingProvidedRole(umlRealization, umlRealization.getName());
    }
  }
  
  public CreateCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleRealizationReactions.CreateCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlRealization = umlRealization;this.umlComponent = umlComponent;
  }
  
  private InterfaceRealization umlRealization;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingProvidedRoleRoutine with input:");
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
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlRealization, umlComponent, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlRealization, umlComponent, pcmComponent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlRealization, umlComponent, pcmComponent, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlRealization, umlComponent, pcmComponent, pcmInterface)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationProvidedRole pcmProvided = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationProvidedRole();
    notifyObjectCreated(pcmProvided);
    userExecution.updatePcmProvidedElement(umlRealization, umlComponent, pcmComponent, pcmInterface, pcmProvided);
    
    addCorrespondenceBetween(userExecution.getElement1(umlRealization, umlComponent, pcmComponent, pcmInterface, pcmProvided), userExecution.getElement2(umlRealization, umlComponent, pcmComponent, pcmInterface, pcmProvided), userExecution.getTag1(umlRealization, umlComponent, pcmComponent, pcmInterface, pcmProvided));
    
    userExecution.callRoutine1(umlRealization, umlComponent, pcmComponent, pcmInterface, pcmProvided, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
