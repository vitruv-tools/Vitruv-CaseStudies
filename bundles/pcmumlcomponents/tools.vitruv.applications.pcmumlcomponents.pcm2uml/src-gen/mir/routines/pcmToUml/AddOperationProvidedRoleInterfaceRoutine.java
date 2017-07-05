package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddOperationProvidedRoleInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddOperationProvidedRoleInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface, final InterfaceRealization interfaceRealization, final Interface umlInterface, final Component umlComponent) {
      return interfaceRealization;
    }
    
    public void update0Element(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface, final InterfaceRealization interfaceRealization, final Interface umlInterface, final Component umlComponent) {
      EList<NamedElement> _suppliers = interfaceRealization.getSuppliers();
      _suppliers.clear();
      EList<NamedElement> _suppliers_1 = interfaceRealization.getSuppliers();
      _suppliers_1.add(umlInterface);
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface, final InterfaceRealization interfaceRealization) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSourceUmlComponent(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface, final InterfaceRealization interfaceRealization, final Interface umlInterface) {
      InterfaceProvidingEntity _providingEntity_ProvidedRole = pcmRole.getProvidingEntity_ProvidedRole();
      return _providingEntity_ProvidedRole;
    }
    
    public EObject getCorrepondenceSourceInterfaceRealization(final OperationProvidedRole pcmRole, final OperationInterface pcmInterface) {
      return pcmRole;
    }
  }
  
  public AddOperationProvidedRoleInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmRole, final OperationInterface pcmInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.AddOperationProvidedRoleInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmRole = pcmRole;this.pcmInterface = pcmInterface;
  }
  
  private OperationProvidedRole pcmRole;
  
  private OperationInterface pcmInterface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddOperationProvidedRoleInterfaceRoutine with input:");
    getLogger().debug("   OperationProvidedRole: " + this.pcmRole);
    getLogger().debug("   OperationInterface: " + this.pcmInterface);
    
    InterfaceRealization interfaceRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceRealization(pcmRole, pcmInterface), // correspondence source supplier
    	InterfaceRealization.class,
    	(InterfaceRealization _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceRealization == null) {
    	return;
    }
    registerObjectUnderModification(interfaceRealization);
    Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmRole, pcmInterface, interfaceRealization), // correspondence source supplier
    	Interface.class,
    	(Interface _element) -> true, // correspondence precondition checker
    	null);
    if (umlInterface == null) {
    	return;
    }
    registerObjectUnderModification(umlInterface);
    Component umlComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponent(pcmRole, pcmInterface, interfaceRealization, umlInterface), // correspondence source supplier
    	Component.class,
    	(Component _element) -> true, // correspondence precondition checker
    	null);
    if (umlComponent == null) {
    	return;
    }
    registerObjectUnderModification(umlComponent);
    // val updatedElement userExecution.getElement1(pcmRole, pcmInterface, interfaceRealization, umlInterface, umlComponent);
    userExecution.update0Element(pcmRole, pcmInterface, interfaceRealization, umlInterface, umlComponent);
    
    postprocessElements();
  }
}
