package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateRequiredRoleCorrespondingUmlNamesRoutine extends AbstractRepairRoutineRealization {
  private UpdateRequiredRoleCorrespondingUmlNamesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceParameter(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity, final Property property) {
      return role;
    }
    
    public void executeAction1(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity, final Property property, final Parameter parameter, @Extension final RoutinesFacade _routinesFacade) {
      property.setName(role.getEntityName());
      parameter.setName(role.getEntityName());
    }
    
    public String getRetrieveTag1(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public String getRetrieveTag2(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity, final Property property) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public EObject getCorrepondenceSourceProperty(final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity) {
      return role;
    }
  }
  
  public UpdateRequiredRoleCorrespondingUmlNamesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole role, final OperationInterface operationInterface, final InterfaceProvidingRequiringEntity requiringEntity) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateRequiredRoleCorrespondingUmlNamesRoutine.ActionUserExecution(getExecutionState(), this);
    this.role = role;this.operationInterface = operationInterface;this.requiringEntity = requiringEntity;
  }
  
  private OperationRequiredRole role;
  
  private OperationInterface operationInterface;
  
  private InterfaceProvidingRequiringEntity requiringEntity;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRequiredRoleCorrespondingUmlNamesRoutine with input:");
    getLogger().debug("   role: " + this.role);
    getLogger().debug("   operationInterface: " + this.operationInterface);
    getLogger().debug("   requiringEntity: " + this.requiringEntity);
    
    org.eclipse.uml2.uml.Property property = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceProperty(role, operationInterface, requiringEntity), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(role, operationInterface, requiringEntity), 
    	false // asserted
    	);
    if (property == null) {
    	return false;
    }
    registerObjectUnderModification(property);
    org.eclipse.uml2.uml.Parameter parameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceParameter(role, operationInterface, requiringEntity, property), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(role, operationInterface, requiringEntity, property), 
    	false // asserted
    	);
    if (parameter == null) {
    	return false;
    }
    registerObjectUnderModification(parameter);
    userExecution.executeAction1(role, operationInterface, requiringEntity, property, parameter, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
