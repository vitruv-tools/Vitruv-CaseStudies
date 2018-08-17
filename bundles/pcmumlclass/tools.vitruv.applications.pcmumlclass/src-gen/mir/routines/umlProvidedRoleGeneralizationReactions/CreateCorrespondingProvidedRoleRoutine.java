package mir.routines.umlProvidedRoleGeneralizationReactions;

import java.io.IOException;
import mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
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
    
    public EObject getElement1(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      return pcmProvided;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public EObject getCorrepondenceSource1(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return umlGeneralization;
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
    
    public EObject getElement2(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      return umlGeneralization;
    }
    
    public String getRetrieveTag3(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public String getTag1(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public void updatePcmProvidedElement(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationProvidedRole pcmProvided) {
      pcmProvided.setProvidedInterface__OperationProvidedRole(pcmInterface);
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = pcmComponent.getProvidedRoles_InterfaceProvidingEntity();
      _providedRoles_InterfaceProvidingEntity.add(pcmProvided);
    }
  }
  
  public CreateCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleGeneralizationReactions.CreateCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlGeneralization = umlGeneralization;this.umlComponent = umlComponent;
  }
  
  private Generalization umlGeneralization;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingProvidedRoleRoutine with input:");
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
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlGeneralization, umlComponent, pcmComponent, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationProvidedRole.class,
    	(org.palladiosimulator.pcm.repository.OperationProvidedRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlGeneralization, umlComponent, pcmComponent, pcmInterface)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationProvidedRole pcmProvided = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationProvidedRole();
    notifyObjectCreated(pcmProvided);
    userExecution.updatePcmProvidedElement(umlGeneralization, umlComponent, pcmComponent, pcmInterface, pcmProvided);
    
    addCorrespondenceBetween(userExecution.getElement1(umlGeneralization, umlComponent, pcmComponent, pcmInterface, pcmProvided), userExecution.getElement2(umlGeneralization, umlComponent, pcmComponent, pcmInterface, pcmProvided), userExecution.getTag1(umlGeneralization, umlComponent, pcmComponent, pcmInterface, pcmProvided));
    
    postprocessElements();
    
    return true;
  }
}
