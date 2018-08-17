package mir.routines.pcmParameterReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateRegularParameterCandidateRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateRegularParameterCandidateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final Parameter pcmParam, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourceUmlParam(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation) {
      return pcmParam;
    }
    
    public String getRetrieveTag2(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final Parameter pcmParam, final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public void callRoutine1(final Parameter pcmParam, final OperationSignature pcmSignature, final Operation umlOperation, final Optional<org.eclipse.uml2.uml.Parameter> umlParam, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlParam.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<org.eclipse.uml2.uml.Parameter, Boolean> _function = (org.eclipse.uml2.uml.Parameter it) -> {
          String _name = it.getName();
          String _parameterName = pcmParam.getParameterName();
          return Boolean.valueOf(Objects.equal(_name, _parameterName));
        };
        final org.eclipse.uml2.uml.Parameter umlParamCandidate = IterableExtensions.<org.eclipse.uml2.uml.Parameter>findFirst(umlOperation.getOwnedParameters(), _function);
        if ((umlParamCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingRegularParameter(pcmParam, umlParamCandidate);
        } else {
          _routinesFacade.createCorrespondingRegularParameter(pcmParam, pcmSignature);
        }
      }
    }
  }
  
  public DetectOrCreateRegularParameterCandidateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParam, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmParameterReactions.DetectOrCreateRegularParameterCandidateRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmParam = pcmParam;this.pcmSignature = pcmSignature;
  }
  
  private Parameter pcmParam;
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateRegularParameterCandidateRoutine with input:");
    getLogger().debug("   pcmParam: " + this.pcmParam);
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmParam, pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmParam, pcmSignature), 
    	false // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    	Optional<org.eclipse.uml2.uml.Parameter> umlParam = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlParam(pcmParam, pcmSignature, umlOperation), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmParam, pcmSignature, umlOperation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlParam.isPresent() ? umlParam.get() : null);
    userExecution.callRoutine1(pcmParam, pcmSignature, umlOperation, umlParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
