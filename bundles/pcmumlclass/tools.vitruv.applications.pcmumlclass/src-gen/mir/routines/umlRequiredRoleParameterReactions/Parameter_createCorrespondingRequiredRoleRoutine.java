package mir.routines.umlRequiredRoleParameterReactions;

import java.io.IOException;
import mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Parameter_createCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_createCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePcmRequiredElement(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity = pcmComponent.getRequiredRoles_InterfaceRequiringEntity();
      _requiredRoles_InterfaceRequiringEntity.add(pcmRequired);
    }
    
    public EObject getElement1(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Parameter umlParameter, final Operation umlConstructor) {
      return umlConstructor;
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final Operation umlConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent) {
      Type _type = umlParameter.getType();
      return _type;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      return umlParameter;
    }
    
    public String getRetrieveTag3(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public String getTag1(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public void callRoutine2(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.parameter_changeNameOfCorrespondingRequiredRole(umlParameter, umlParameter.getName());
    }
    
    public void callRoutine1(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      Type _type = umlParameter.getType();
      _routinesFacade.parameter_changeTypeOfCorrespondingRequiredRole(umlParameter, ((Interface) _type));
    }
  }
  
  public Parameter_createCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final Operation umlConstructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_createCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.umlConstructor = umlConstructor;
  }
  
  private Parameter umlParameter;
  
  private Operation umlConstructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_createCorrespondingRequiredRoleRoutine with input:");
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
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlParameter, umlConstructor, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParameter, umlConstructor, pcmComponent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParameter, umlConstructor, pcmComponent, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlParameter, umlConstructor, pcmComponent, pcmInterface)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    notifyObjectCreated(pcmRequired);
    userExecution.updatePcmRequiredElement(umlParameter, umlConstructor, pcmComponent, pcmInterface, pcmRequired);
    
    addCorrespondenceBetween(userExecution.getElement1(umlParameter, umlConstructor, pcmComponent, pcmInterface, pcmRequired), userExecution.getElement2(umlParameter, umlConstructor, pcmComponent, pcmInterface, pcmRequired), userExecution.getTag1(umlParameter, umlConstructor, pcmComponent, pcmInterface, pcmRequired));
    
    userExecution.callRoutine1(umlParameter, umlConstructor, pcmComponent, pcmInterface, pcmRequired, this.getRoutinesFacade());
    
    userExecution.callRoutine2(umlParameter, umlConstructor, pcmComponent, pcmInterface, pcmRequired, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
