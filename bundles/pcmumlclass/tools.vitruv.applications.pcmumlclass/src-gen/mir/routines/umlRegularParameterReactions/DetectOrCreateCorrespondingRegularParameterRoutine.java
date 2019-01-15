package mir.routines.umlRegularParameterReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRegularParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingRegularParameterRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingRegularParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlParam;
    }
    
    public EObject getCorrepondenceSourcePcmParam(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return umlParam;
    }
    
    public String getRetrieveTag1(final Parameter umlParam, final Operation umlOperation) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag2(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public String getRetrieveTag3(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature) {
      return TagLiterals.PARAMETER__REGULAR_PARAMETER;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Parameter umlParam, final Operation umlOperation) {
      return umlOperation;
    }
    
    public void callRoutine1(final Parameter umlParam, final Operation umlOperation, final OperationSignature pcmSignature, final Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParam, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmParam.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<org.palladiosimulator.pcm.repository.Parameter, Boolean> _function = (org.palladiosimulator.pcm.repository.Parameter it) -> {
          String _parameterName = it.getParameterName();
          String _name = umlParam.getName();
          return Boolean.valueOf(Objects.equal(_parameterName, _name));
        };
        final org.palladiosimulator.pcm.repository.Parameter pcmParamCandidate = IterableExtensions.<org.palladiosimulator.pcm.repository.Parameter>findFirst(pcmSignature.getParameters__OperationSignature(), _function);
        if ((pcmParamCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingRegularParameter(umlParam, pcmParamCandidate);
        } else {
          _routinesFacade.createCorrespondingRegularParameter(umlParam, umlOperation);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingRegularParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParam, final Operation umlOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRegularParameterReactions.DetectOrCreateCorrespondingRegularParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParam = umlParam;this.umlOperation = umlOperation;
  }
  
  private Parameter umlParam;
  
  private Operation umlOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingRegularParameterRoutine with input:");
    getLogger().debug("   umlParam: " + this.umlParam);
    getLogger().debug("   umlOperation: " + this.umlOperation);
    
    org.palladiosimulator.pcm.repository.OperationSignature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlParam, umlOperation), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParam, umlOperation), 
    	false // asserted
    	);
    if (pcmSignature == null) {
    	return false;
    }
    registerObjectUnderModification(pcmSignature);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParam, umlOperation, pcmSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParam, umlOperation, pcmSignature)
    ).isEmpty()) {
    	return false;
    }
    	Optional<org.palladiosimulator.pcm.repository.Parameter> pcmParam = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmParam(umlParam, umlOperation, pcmSignature), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.Parameter.class,
    		(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlParam, umlOperation, pcmSignature), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmParam.isPresent() ? pcmParam.get() : null);
    userExecution.callRoutine1(umlParam, umlOperation, pcmSignature, pcmParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
