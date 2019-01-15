package mir.routines.umlRequiredRolePropertyReactions;

import java.io.IOException;
import mir.routines.umlRequiredRolePropertyReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
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
public class CreateCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePcmRequiredElement(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity = pcmComponent.getRequiredRoles_InterfaceRequiringEntity();
      _requiredRoles_InterfaceRequiringEntity.add(pcmRequired);
    }
    
    public EObject getElement1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public EObject getCorrepondenceSource1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      Type _type = umlProperty.getType();
      return _type;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      return umlProperty;
    }
    
    public String getRetrieveTag3(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public String getTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public void callRoutine2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.changeNameOfCorrespondingRequiredRole(umlProperty, umlProperty.getName());
    }
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final OperationRequiredRole pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      Type _type = umlProperty.getType();
      _routinesFacade.changeTypeOfCorrespondingRequiredRole(umlProperty, ((Interface) _type));
    }
  }
  
  public CreateCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRolePropertyReactions.CreateCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlComponent = umlComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlProperty, umlComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, umlComponent), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlProperty, umlComponent, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, umlComponent, pcmComponent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlProperty, umlComponent, pcmComponent, pcmInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    	(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(umlProperty, umlComponent, pcmComponent, pcmInterface)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationRequiredRole pcmRequired = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationRequiredRole();
    notifyObjectCreated(pcmRequired);
    userExecution.updatePcmRequiredElement(umlProperty, umlComponent, pcmComponent, pcmInterface, pcmRequired);
    
    addCorrespondenceBetween(userExecution.getElement1(umlProperty, umlComponent, pcmComponent, pcmInterface, pcmRequired), userExecution.getElement2(umlProperty, umlComponent, pcmComponent, pcmInterface, pcmRequired), userExecution.getTag1(umlProperty, umlComponent, pcmComponent, pcmInterface, pcmRequired));
    
    userExecution.callRoutine1(umlProperty, umlComponent, pcmComponent, pcmInterface, pcmRequired, this.getRoutinesFacade());
    
    userExecution.callRoutine2(umlProperty, umlComponent, pcmComponent, pcmInterface, pcmRequired, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
