package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmNewInterface(final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation, final OperationRequiredRole pcmRequired) {
      Type _type = parameter.getType();
      return _type;
    }
    
    public void executeAction1(final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation, final OperationRequiredRole pcmRequired, final OperationInterface pcmNewInterface, @Extension final RoutinesFacade _routinesFacade) {
      pcmRequired.setRequiredInterface__OperationRequiredRole(pcmNewInterface);
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation, final OperationRequiredRole pcmRequired) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public ChangeTypeOfCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final Parameter parameter, final org.eclipse.uml2.uml.Class implementation, final Interface interface_, final Operation operation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.ChangeTypeOfCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.parameter = parameter;this.implementation = implementation;this.interface_ = interface_;this.operation = operation;
  }
  
  private Property umlProperty;
  
  private Parameter parameter;
  
  private org.eclipse.uml2.uml.Class implementation;
  
  private Interface interface_;
  
  private Operation operation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   parameter: " + this.parameter);
    getLogger().debug("   implementation: " + this.implementation);
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   operation: " + this.operation);
    
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRequired(umlProperty, parameter, implementation, interface_, operation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, parameter, implementation, interface_, operation), 
    	false // asserted
    	);
    if (pcmRequired == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRequired);
    org.palladiosimulator.pcm.repository.OperationInterface pcmNewInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmNewInterface(umlProperty, parameter, implementation, interface_, operation, pcmRequired), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, parameter, implementation, interface_, operation, pcmRequired), 
    	false // asserted
    	);
    if (pcmNewInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmNewInterface);
    userExecution.executeAction1(umlProperty, parameter, implementation, interface_, operation, pcmRequired, pcmNewInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
