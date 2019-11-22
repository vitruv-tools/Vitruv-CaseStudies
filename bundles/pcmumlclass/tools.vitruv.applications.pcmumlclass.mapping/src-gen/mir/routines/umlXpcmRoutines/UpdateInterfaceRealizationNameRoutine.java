package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateInterfaceRealizationNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateInterfaceRealizationNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity, final InterfaceRealization interfaceRealization, @Extension final RoutinesFacade _routinesFacade) {
      interfaceRealization.setName(role.getEntityName());
    }
    
    public String getRetrieveTag1(final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getCorrepondenceSourceInterfaceRealization(final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity) {
      return role;
    }
  }
  
  public UpdateInterfaceRealizationNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity providingEntity) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateInterfaceRealizationNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.role = role;this.operationInterface = operationInterface;this.providingEntity = providingEntity;
  }
  
  private OperationProvidedRole role;
  
  private OperationInterface operationInterface;
  
  private InterfaceProvidingRequiringEntity providingEntity;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateInterfaceRealizationNameRoutine with input:");
    getLogger().debug("   role: " + this.role);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    getLogger().debug("   providingEntity: " + this.providingEntity);
    
    org.eclipse.uml2.uml.InterfaceRealization interfaceRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceRealization(role, operationInterface, providingEntity), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(role, operationInterface, providingEntity), 
    	false // asserted
    	);
    if (interfaceRealization == null) {
    	return false;
    }
    registerObjectUnderModification(interfaceRealization);
    userExecution.executeAction1(role, operationInterface, providingEntity, interfaceRealization, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
