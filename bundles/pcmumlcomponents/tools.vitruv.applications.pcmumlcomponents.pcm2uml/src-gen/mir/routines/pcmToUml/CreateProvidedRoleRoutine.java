package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
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
    
    public EObject getElement1(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole, final Component umlComponent, final InterfaceRealization interfaceRealization) {
      return umlComponent;
    }
    
    public void updateInterfaceRealizationElement(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole, final Component umlComponent, final InterfaceRealization interfaceRealization) {
      interfaceRealization.setName(pcmProvidedRole.getEntityName());
      EList<NamedElement> _clients = interfaceRealization.getClients();
      _clients.add(umlComponent);
    }
    
    public void update0Element(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole, final Component umlComponent, final InterfaceRealization interfaceRealization) {
      EList<InterfaceRealization> _interfaceRealizations = umlComponent.getInterfaceRealizations();
      _interfaceRealizations.add(interfaceRealization);
    }
    
    public EObject getElement2(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole, final Component umlComponent, final InterfaceRealization interfaceRealization) {
      return interfaceRealization;
    }
    
    public EObject getElement3(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole, final Component umlComponent, final InterfaceRealization interfaceRealization) {
      return pcmProvidedRole;
    }
    
    public EObject getCorrepondenceSourceUmlComponent(final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole) {
      return pcmComponent;
    }
  }
  
  public CreateProvidedRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceProvidingEntity pcmComponent, final ProvidedRole pcmProvidedRole) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.pcmProvidedRole = pcmProvidedRole;
  }
  
  private InterfaceProvidingEntity pcmComponent;
  
  private ProvidedRole pcmProvidedRole;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateProvidedRoleRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   pcmProvidedRole: " + this.pcmProvidedRole);
    
    org.eclipse.uml2.uml.Component umlComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponent(pcmComponent, pcmProvidedRole), // correspondence source supplier
    	org.eclipse.uml2.uml.Component.class,
    	(org.eclipse.uml2.uml.Component _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlComponent == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponent);
    org.eclipse.uml2.uml.InterfaceRealization interfaceRealization = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterfaceRealization();
    notifyObjectCreated(interfaceRealization);
    userExecution.updateInterfaceRealizationElement(pcmComponent, pcmProvidedRole, umlComponent, interfaceRealization);
    
    // val updatedElement userExecution.getElement1(pcmComponent, pcmProvidedRole, umlComponent, interfaceRealization);
    userExecution.update0Element(pcmComponent, pcmProvidedRole, umlComponent, interfaceRealization);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmComponent, pcmProvidedRole, umlComponent, interfaceRealization), userExecution.getElement3(pcmComponent, pcmProvidedRole, umlComponent, interfaceRealization), "");
    
    postprocessElements();
    
    return true;
  }
}
