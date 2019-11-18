package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingRoutineUpdateHelper;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateSource;
import tools.vitruv.extensions.dslsruntime.mappings.updates.MappingUpdateTarget;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateRequiredRoleNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateRequiredRoleNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation, final OperationRequiredRole role, @Extension final RoutinesFacade _routinesFacade) {
      final MappingUpdateTarget roleTarget = MappingRoutineUpdateHelper.eObjectTarget(role, "entityName");
      final MappingUpdateSource propertySource = MappingRoutineUpdateHelper.simpleEObjectSource(property, "name");
      final MappingUpdateSource parameterSource = MappingRoutineUpdateHelper.simpleEObjectSource(parameter, "name");
      MappingRoutineUpdateHelper.updateFromSources(roleTarget, propertySource, parameterSource);
    }
    
    public String getRetrieveTag1(final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceRole(final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
      return property;
    }
  }
  
  public UpdateRequiredRoleNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property property, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateRequiredRoleNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.property = property;this.parameter = parameter;this.implementation = implementation;this.interface_ = interface_;this.operation = operation;
  }
  
  private Property property;
  
  private Parameter parameter;
  
  private org.eclipse.uml2.uml.Class implementation;
  
  private Interface interface_;
  
  private Operation operation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRequiredRoleNameRoutine with input:");
    getLogger().debug("   property: " + this.property);
    getLogger().debug("   parameter: " + this.parameter);
    getLogger().debug("   implementation: " + this.implementation);
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   operation: " + this.operation);
    
    org.palladiosimulator.pcm.repository.OperationRequiredRole role = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRole(property, parameter, implementation, interface_, operation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(property, parameter, implementation, interface_, operation), 
    	false // asserted
    	);
    if (role == null) {
    	return false;
    }
    registerObjectUnderModification(role);
    userExecution.executeAction1(property, parameter, implementation, interface_, operation, role, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
