package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComponent, final InterfaceRealization interfaceRealization, final BasicComponent pcmComponent, final OperationProvidedRole pcmRole) {
      return pcmComponent;
    }
    
    public void update0Element(final Component umlComponent, final InterfaceRealization interfaceRealization, final BasicComponent pcmComponent, final OperationProvidedRole pcmRole) {
      EList<ProvidedRole> _providedRoles_InterfaceProvidingEntity = pcmComponent.getProvidedRoles_InterfaceProvidingEntity();
      _providedRoles_InterfaceProvidingEntity.add(pcmRole);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Component umlComponent, final InterfaceRealization interfaceRealization) {
      return umlComponent;
    }
    
    public EObject getElement2(final Component umlComponent, final InterfaceRealization interfaceRealization, final BasicComponent pcmComponent, final OperationProvidedRole pcmRole) {
      return interfaceRealization;
    }
    
    public EObject getElement3(final Component umlComponent, final InterfaceRealization interfaceRealization, final BasicComponent pcmComponent, final OperationProvidedRole pcmRole) {
      return pcmRole;
    }
    
    public void updatePcmRoleElement(final Component umlComponent, final InterfaceRealization interfaceRealization, final BasicComponent pcmComponent, final OperationProvidedRole pcmRole) {
      pcmRole.setEntityName(interfaceRealization.getName());
    }
  }
  
  public CreateProvidedRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComponent, final InterfaceRealization interfaceRealization) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlComponent = umlComponent;this.interfaceRealization = interfaceRealization;
  }
  
  private Component umlComponent;
  
  private InterfaceRealization interfaceRealization;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateProvidedRoleRoutine with input:");
    getLogger().debug("   umlComponent: " + this.umlComponent);
    getLogger().debug("   interfaceRealization: " + this.interfaceRealization);
    
    org.palladiosimulator.pcm.repository.BasicComponent pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlComponent, interfaceRealization), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.BasicComponent.class,
    	(org.palladiosimulator.pcm.repository.BasicComponent _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    org.palladiosimulator.pcm.repository.OperationProvidedRole pcmRole = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationProvidedRole();
    notifyObjectCreated(pcmRole);
    userExecution.updatePcmRoleElement(umlComponent, interfaceRealization, pcmComponent, pcmRole);
    
    // val updatedElement userExecution.getElement1(umlComponent, interfaceRealization, pcmComponent, pcmRole);
    userExecution.update0Element(umlComponent, interfaceRealization, pcmComponent, pcmRole);
    
    addCorrespondenceBetween(userExecution.getElement2(umlComponent, interfaceRealization, pcmComponent, pcmRole), userExecution.getElement3(umlComponent, interfaceRealization, pcmComponent, pcmRole), "");
    
    postprocessElements();
    
    return true;
  }
}
