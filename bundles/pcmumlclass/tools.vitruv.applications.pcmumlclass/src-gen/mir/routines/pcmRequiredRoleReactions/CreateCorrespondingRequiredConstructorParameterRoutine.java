package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingRequiredConstructorParameterRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingRequiredConstructorParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Interface> umlRequiredInterface, final Parameter umlConstructorParameter) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSource1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor) {
      return pcmRequired;
    }
    
    public String getRetrieveTag1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getCorrepondenceSourceUmlComponentConstructor(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public String getRetrieveTag2(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredInterface(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor) {
      OperationInterface _requiredInterface__OperationRequiredRole = pcmRequired.getRequiredInterface__OperationRequiredRole();
      return _requiredInterface__OperationRequiredRole;
    }
    
    public EObject getElement2(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Interface> umlRequiredInterface, final Parameter umlConstructorParameter) {
      return umlConstructorParameter;
    }
    
    public String getRetrieveTag3(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getTag1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Interface> umlRequiredInterface, final Parameter umlConstructorParameter) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public void updateUmlConstructorParameterElement(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Interface> umlRequiredInterface, final Parameter umlConstructorParameter) {
      umlConstructorParameter.setName(pcmRequired.getEntityName());
      umlConstructorParameter.setType(umlRequiredInterface.orElse(null));
    }
  }
  
  public CreateCorrespondingRequiredConstructorParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.CreateCorrespondingRequiredConstructorParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingRequiredConstructorParameterRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    getLogger().debug("   pcmIPRE: " + this.pcmIPRE);
    
    org.eclipse.uml2.uml.Operation umlComponentConstructor = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentConstructor(pcmRequired, pcmIPRE), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRequired, pcmIPRE), 
    	false // asserted
    	);
    if (umlComponentConstructor == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentConstructor);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmRequired, pcmIPRE, umlComponentConstructor), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmRequired, pcmIPRE, umlComponentConstructor)
    ).isEmpty()) {
    	return false;
    }
    	Optional<org.eclipse.uml2.uml.Interface> umlRequiredInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlRequiredInterface(pcmRequired, pcmIPRE, umlComponentConstructor), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmRequired, pcmIPRE, umlComponentConstructor), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlRequiredInterface.isPresent() ? umlRequiredInterface.get() : null);
    org.eclipse.uml2.uml.Parameter umlConstructorParameter = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createParameter();
    notifyObjectCreated(umlConstructorParameter);
    userExecution.updateUmlConstructorParameterElement(pcmRequired, pcmIPRE, umlComponentConstructor, umlRequiredInterface, umlConstructorParameter);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmRequired, pcmIPRE, umlComponentConstructor, umlRequiredInterface, umlConstructorParameter), userExecution.getElement2(pcmRequired, pcmIPRE, umlComponentConstructor, umlRequiredInterface, umlConstructorParameter), userExecution.getTag1(pcmRequired, pcmIPRE, umlComponentConstructor, umlRequiredInterface, umlConstructorParameter));
    
    postprocessElements();
    
    return true;
  }
}
