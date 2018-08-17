package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingRequiredConstructorParameterRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingRequiredConstructorParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
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
    
    public EObject getCorrepondenceSourceUmlConstructorParameter(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor) {
      return pcmRequired;
    }
    
    public void callRoutine1(final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE, final Operation umlComponentConstructor, final Optional<Parameter> umlConstructorParameter, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlConstructorParameter.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        _routinesFacade.createCorrespondingRequiredConstructorParameter(pcmRequired, pcmIPRE);
      }
    }
  }
  
  public DetectOrCreateCorrespondingRequiredConstructorParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final InterfaceProvidingRequiringEntity pcmIPRE) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.DetectOrCreateCorrespondingRequiredConstructorParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmIPRE = pcmIPRE;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private InterfaceProvidingRequiringEntity pcmIPRE;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingRequiredConstructorParameterRoutine with input:");
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
    userExecution.callRoutine1(pcmRequired, pcmIPRE, umlComponentConstructor, umlConstructorParameter, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
