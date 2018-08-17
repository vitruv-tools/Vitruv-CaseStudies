package mir.routines.pcmSignatureReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateReturnParameterCandidateRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateReturnParameterCandidateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSourceUmlReturnParam(final OperationSignature pcmSignature, final Operation umlOperation) {
      return pcmSignature;
    }
    
    public void callRoutine1(final OperationSignature pcmSignature, final Operation umlOperation, final Optional<Parameter> umlReturnParam, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlReturnParam.isPresent();
      if (_isPresent) {
        return;
      } else {
        final Function1<Parameter, Boolean> _function = (Parameter it) -> {
          ParameterDirectionKind _direction = it.getDirection();
          return Boolean.valueOf((_direction == ParameterDirectionKind.RETURN_LITERAL));
        };
        final Parameter umlReturnParamCandidate = IterableExtensions.<Parameter>findFirst(umlOperation.getOwnedParameters(), _function);
        if ((umlReturnParamCandidate != null)) {
          _routinesFacade.attemptAddingCorrespondenceForExistingReturnParamCandidate(pcmSignature, umlReturnParamCandidate);
        } else {
          _routinesFacade.createCorrespondingReturnParameter(pcmSignature);
        }
      }
    }
  }
  
  public DetectOrCreateReturnParameterCandidateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.DetectOrCreateReturnParameterCandidateRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;
  }
  
  private OperationSignature pcmSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateReturnParameterCandidateRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    
    org.eclipse.uml2.uml.Operation umlOperation = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlOperation(pcmSignature), // correspondence source supplier
    	org.eclipse.uml2.uml.Operation.class,
    	(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature), 
    	true // asserted
    	);
    if (umlOperation == null) {
    	return false;
    }
    registerObjectUnderModification(umlOperation);
    	Optional<org.eclipse.uml2.uml.Parameter> umlReturnParam = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlReturnParam(pcmSignature, umlOperation), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSignature, umlOperation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlReturnParam.isPresent() ? umlReturnParam.get() : null);
    userExecution.callRoutine1(pcmSignature, umlOperation, umlReturnParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
