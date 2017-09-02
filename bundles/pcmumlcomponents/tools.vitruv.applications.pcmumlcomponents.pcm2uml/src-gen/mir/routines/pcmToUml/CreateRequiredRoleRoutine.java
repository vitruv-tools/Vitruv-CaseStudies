package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Usage;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole, final Component umlComponent, final Usage usage) {
      return umlComponent;
    }
    
    public void update0Element(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole, final Component umlComponent, final Usage usage) {
      EList<PackageableElement> _packagedElements = umlComponent.getPackagedElements();
      _packagedElements.add(usage);
    }
    
    public EObject getElement2(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole, final Component umlComponent, final Usage usage) {
      return usage;
    }
    
    public void updateUsageElement(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole, final Component umlComponent, final Usage usage) {
      usage.setName(requiredRole.getEntityName());
      EList<NamedElement> _clients = usage.getClients();
      _clients.add(umlComponent);
    }
    
    public EObject getElement3(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole, final Component umlComponent, final Usage usage) {
      return requiredRole;
    }
    
    public EObject getCorrepondenceSourceUmlComponent(final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole) {
      return pcmComponent;
    }
  }
  
  public CreateRequiredRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRequiringEntity pcmComponent, final OperationRequiredRole requiredRole) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.requiredRole = requiredRole;
  }
  
  private InterfaceRequiringEntity pcmComponent;
  
  private OperationRequiredRole requiredRole;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateRequiredRoleRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   requiredRole: " + this.requiredRole);
    
    org.eclipse.uml2.uml.Component umlComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponent(pcmComponent, requiredRole), // correspondence source supplier
    	org.eclipse.uml2.uml.Component.class,
    	(org.eclipse.uml2.uml.Component _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlComponent == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponent);
    org.eclipse.uml2.uml.Usage usage = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createUsage();
    notifyObjectCreated(usage);
    userExecution.updateUsageElement(pcmComponent, requiredRole, umlComponent, usage);
    
    // val updatedElement userExecution.getElement1(pcmComponent, requiredRole, umlComponent, usage);
    userExecution.update0Element(pcmComponent, requiredRole, umlComponent, usage);
    
    addCorrespondenceBetween(userExecution.getElement2(pcmComponent, requiredRole, umlComponent, usage), userExecution.getElement3(pcmComponent, requiredRole, umlComponent, usage), "");
    
    postprocessElements();
    
    return true;
  }
}
