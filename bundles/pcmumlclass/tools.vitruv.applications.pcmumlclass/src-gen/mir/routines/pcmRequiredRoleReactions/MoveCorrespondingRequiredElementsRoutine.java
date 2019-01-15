package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class MoveCorrespondingRequiredElementsRoutine extends AbstractRepairRoutineRealization {
  private MoveCorrespondingRequiredElementsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag4(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Parameter umlConstructorParameter, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getElement1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Parameter umlConstructorParameter, final org.eclipse.uml2.uml.Class umlComponentImpl, final Property umlRequiredField) {
      return umlComponentImpl;
    }
    
    public void update0Element(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Parameter umlConstructorParameter, final org.eclipse.uml2.uml.Class umlComponentImpl, final Property umlRequiredField) {
      EList<Parameter> _ownedParameters = umlComponentConstructor.getOwnedParameters();
      _ownedParameters.add(umlConstructorParameter);
      EList<Property> _ownedAttributes = umlComponentImpl.getOwnedAttributes();
      _ownedAttributes.add(umlRequiredField);
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Parameter umlConstructorParameter) {
      return pcmIPRE;
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
    
    public String getRetrieveTag3(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Parameter umlConstructorParameter) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredField(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Parameter umlConstructorParameter, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourceUmlConstructorParameter(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor) {
      return pcmRequired;
    }
  }
  
  public MoveCorrespondingRequiredElementsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.MoveCorrespondingRequiredElementsRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine MoveCorrespondingRequiredElementsRoutine with input:");
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
    org.eclipse.uml2.uml.Parameter umlConstructorParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlConstructorParameter(pcmRequired, pcmIPRE, umlComponentConstructor), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmRequired, pcmIPRE, umlComponentConstructor), 
    	false // asserted
    	);
    if (umlConstructorParameter == null) {
    	return false;
    }
    registerObjectUnderModification(umlConstructorParameter);
    org.eclipse.uml2.uml.Class umlComponentImpl = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlComponentImpl(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag3(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter), 
    	false // asserted
    	);
    if (umlComponentImpl == null) {
    	return false;
    }
    registerObjectUnderModification(umlComponentImpl);
    org.eclipse.uml2.uml.Property umlRequiredField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRequiredField(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, umlComponentImpl), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag4(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, umlComponentImpl), 
    	false // asserted
    	);
    if (umlRequiredField == null) {
    	return false;
    }
    registerObjectUnderModification(umlRequiredField);
    // val updatedElement userExecution.getElement1(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, umlComponentImpl, umlRequiredField);
    userExecution.update0Element(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, umlComponentImpl, umlRequiredField);
    
    postprocessElements();
    
    return true;
  }
}
