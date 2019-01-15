package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingRequiredFieldRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingRequiredFieldRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlRequiredInterface, final Property umlRequiredField) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSource1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return pcmIPRE;
    }
    
    public String getRetrieveTag1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public String getRetrieveTag2(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredInterface(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      OperationInterface _requiredInterface__OperationRequiredRole = pcmRequired.getRequiredInterface__OperationRequiredRole();
      return _requiredInterface__OperationRequiredRole;
    }
    
    public EObject getElement2(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlRequiredInterface, final Property umlRequiredField) {
      return umlRequiredField;
    }
    
    public String getRetrieveTag3(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getTag1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlRequiredInterface, final Property umlRequiredField) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public void updateUmlRequiredFieldElement(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Interface> umlRequiredInterface, final Property umlRequiredField) {
      umlRequiredField.setName(pcmRequired.getEntityName());
      umlRequiredField.setType(umlRequiredInterface.orElse(null));
    }
  }
  
  public CreateCorrespondingRequiredFieldRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.CreateCorrespondingRequiredFieldRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingRequiredFieldRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    getLogger().debug("   pcmIPRE: " + this.pcmIPRE);
    
    org.eclipse.uml2.uml.Class umlComponentImpl = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImpl(pcmRequired, pcmIPRE), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRequired, pcmIPRE), 
    	false // asserted
    	);
    if (umlComponentImpl == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImpl);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmRequired, pcmIPRE, umlComponentImpl), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmRequired, pcmIPRE, umlComponentImpl)
    ).isEmpty()) {
    	return false;
    }
    	Optional<org.eclipse.uml2.uml.Interface> umlRequiredInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlRequiredInterface(pcmRequired, pcmIPRE, umlComponentImpl), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmRequired, pcmIPRE, umlComponentImpl), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlRequiredInterface.isPresent() ? umlRequiredInterface.get() : null);
    org.eclipse.uml2.uml.Property umlRequiredField = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createProperty();
    notifyObjectCreated(umlRequiredField);
    userExecution.updateUmlRequiredFieldElement(pcmRequired, pcmIPRE, umlComponentImpl, umlRequiredInterface, umlRequiredField);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmRequired, pcmIPRE, umlComponentImpl, umlRequiredInterface, umlRequiredField), userExecution.getElement2(pcmRequired, pcmIPRE, umlComponentImpl, umlRequiredInterface, umlRequiredField), userExecution.getTag1(pcmRequired, pcmIPRE, umlComponentImpl, umlRequiredInterface, umlRequiredField));
    
    postprocessElements();
    
    return true;
  }
}
