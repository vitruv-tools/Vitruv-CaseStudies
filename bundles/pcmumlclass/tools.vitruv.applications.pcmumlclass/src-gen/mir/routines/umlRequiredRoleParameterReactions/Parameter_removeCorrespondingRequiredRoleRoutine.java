package mir.routines.umlRequiredRoleParameterReactions;

import java.io.IOException;
import mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Parameter_removeCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_removeCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationRequiredRole pcmRequired) {
      return pcmComponent;
    }
    
    public void update0Element(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationRequiredRole pcmRequired) {
      EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity = pcmComponent.getRequiredRoles_InterfaceRequiringEntity();
      _requiredRoles_InterfaceRequiringEntity.remove(pcmRequired);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Parameter umlParameter, final Operation umlConstructor) {
      return umlConstructor;
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final Operation umlConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
  }
  
  public Parameter_removeCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final Operation umlConstructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_removeCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.umlConstructor = umlConstructor;
  }
  
  private Parameter umlParameter;
  
  private Operation umlConstructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_removeCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   umlConstructor: " + this.umlConstructor);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlParameter, umlConstructor), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, umlConstructor), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRequired(umlParameter, umlConstructor, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParameter, umlConstructor, pcmComponent), 
    	false // asserted
    	);
    if (pcmRequired == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRequired);
    // val updatedElement userExecution.getElement1(umlParameter, umlConstructor, pcmComponent, pcmRequired);
    userExecution.update0Element(umlParameter, umlConstructor, pcmComponent, pcmRequired);
    
    postprocessElements();
    
    return true;
  }
}
