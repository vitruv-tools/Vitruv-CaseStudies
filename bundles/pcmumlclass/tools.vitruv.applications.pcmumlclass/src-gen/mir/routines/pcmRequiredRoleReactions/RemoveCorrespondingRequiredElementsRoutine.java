package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingRequiredElementsRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondingRequiredElementsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag4(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Parameter> umlConstructorParameter, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public void executeAction1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Parameter> umlConstructorParameter, final org.eclipse.uml2.uml.Class umlComponentImpl, final Optional<Property> umlRequiredField, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlConstructorParameter.isPresent();
      if (_isPresent) {
        EList<Parameter> _ownedParameters = umlComponentConstructor.getOwnedParameters();
        Parameter _get = umlConstructorParameter.get();
        _ownedParameters.remove(_get);
      }
      boolean _isPresent_1 = umlRequiredField.isPresent();
      if (_isPresent_1) {
        EList<Property> _ownedAttributes = umlComponentImpl.getOwnedAttributes();
        Property _get_1 = umlRequiredField.get();
        _ownedAttributes.remove(_get_1);
      }
    }
    
    public EObject getCorrepondenceSourceUmlComponentImpl(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Parameter> umlConstructorParameter) {
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
    
    public String getRetrieveTag3(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Parameter> umlConstructorParameter) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredField(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Parameter> umlConstructorParameter, final org.eclipse.uml2.uml.Class umlComponentImpl) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourceUmlConstructorParameter(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor) {
      return pcmRequired;
    }
  }
  
  public RemoveCorrespondingRequiredElementsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.RemoveCorrespondingRequiredElementsRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingRequiredElementsRoutine with input:");
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
    	Optional<org.eclipse.uml2.uml.Parameter> umlConstructorParameter = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlConstructorParameter(pcmRequired, pcmIPRE, umlComponentConstructor), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmRequired, pcmIPRE, umlComponentConstructor), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlConstructorParameter.isPresent() ? umlConstructorParameter.get() : null);
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
    	Optional<org.eclipse.uml2.uml.Property> umlRequiredField = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlRequiredField(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, umlComponentImpl), // correspondence source supplier
    		org.eclipse.uml2.uml.Property.class,
    		(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag4(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, umlComponentImpl), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlRequiredField.isPresent() ? umlRequiredField.get() : null);
    userExecution.executeAction1(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, umlComponentImpl, umlRequiredField, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
