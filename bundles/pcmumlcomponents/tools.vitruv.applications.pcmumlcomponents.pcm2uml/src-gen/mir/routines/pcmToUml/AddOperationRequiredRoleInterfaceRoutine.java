package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Usage;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddOperationRequiredRoleInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddOperationRequiredRoleInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationRequiredRole pcmRole, final OperationInterface pcmInterface, final Usage umlUsage, final Interface umlInterface) {
      return umlUsage;
    }
    
    public void update0Element(final OperationRequiredRole pcmRole, final OperationInterface pcmInterface, final Usage umlUsage, final Interface umlInterface) {
      umlUsage.getSuppliers().clear();
      EList<NamedElement> _suppliers = umlUsage.getSuppliers();
      _suppliers.add(umlInterface);
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationRequiredRole pcmRole, final OperationInterface pcmInterface, final Usage umlUsage) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSourceUmlUsage(final OperationRequiredRole pcmRole, final OperationInterface pcmInterface) {
      return pcmRole;
    }
  }
  
  public AddOperationRequiredRoleInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRole, final OperationInterface pcmInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.AddOperationRequiredRoleInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmRole = pcmRole;this.pcmInterface = pcmInterface;
  }
  
  private OperationRequiredRole pcmRole;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddOperationRequiredRoleInterfaceRoutine with input:");
    getLogger().debug("   pcmRole: " + this.pcmRole);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Usage umlUsage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlUsage(pcmRole, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Usage.class,
    	(org.eclipse.uml2.uml.Usage _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlUsage == null) {
    	return false;
    }
    registerObjectUnderModification(umlUsage);
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmRole, pcmInterface, umlUsage), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    // val updatedElement userExecution.getElement1(pcmRole, pcmInterface, umlUsage, umlInterface);
    userExecution.update0Element(pcmRole, pcmInterface, umlUsage, umlInterface);
    
    postprocessElements();
    
    return true;
  }
}
