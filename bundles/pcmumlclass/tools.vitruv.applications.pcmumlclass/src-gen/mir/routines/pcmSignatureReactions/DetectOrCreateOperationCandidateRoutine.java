package mir.routines.pcmSignatureReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmSignatureReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateOperationCandidateRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateOperationCandidateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag2(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public String getRetrieveTag3(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Optional<Operation> umlOperation) {
      return TagLiterals.SIGNATURE__RETURN_PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlOperation(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface) {
      return pcmSignature;
    }
    
    public EObject getCorrepondenceSourceUmlReturnParam(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Optional<Operation> umlOperation) {
      return pcmSignature;
    }
    
    public void callRoutine1(final OperationSignature pcmSignature, final OperationInterface pcmInterface, final Interface umlInterface, final Optional<Operation> umlOperation, final Optional<Parameter> umlReturnParam, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = umlOperation.isPresent();
      if (_isPresent) {
        _routinesFacade.detectOrCreateReturnParameterCandidate(pcmSignature);
      } else {
        final Function1<Operation, Boolean> _function = (Operation it) -> {
          String _name = it.getName();
          String _entityName = pcmSignature.getEntityName();
          return Boolean.valueOf(Objects.equal(_name, _entityName));
        };
        final Operation umlOperationCandidate = IterableExtensions.<Operation>findFirst(umlInterface.getOwnedOperations(), _function);
        if ((umlOperationCandidate != null)) {
          _routinesFacade.attemptAddingCorrespondenceForExistingOperationCandidate(pcmSignature, pcmInterface, umlOperationCandidate);
          _routinesFacade.detectOrCreateReturnParameterCandidate(pcmSignature);
        } else {
          _routinesFacade.createCorrespondingOperation(pcmSignature, pcmInterface);
        }
      }
    }
  }
  
  public DetectOrCreateOperationCandidateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature pcmSignature, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmSignatureReactions.DetectOrCreateOperationCandidateRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmSignature = pcmSignature;this.pcmInterface = pcmInterface;
  }
  
  private OperationSignature pcmSignature;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateOperationCandidateRoutine with input:");
    getLogger().debug("   pcmSignature: " + this.pcmSignature);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(pcmSignature, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmSignature, pcmInterface), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    	Optional<org.eclipse.uml2.uml.Operation> umlOperation = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlOperation(pcmSignature, pcmInterface, umlInterface), // correspondence source supplier
    		org.eclipse.uml2.uml.Operation.class,
    		(org.eclipse.uml2.uml.Operation _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmSignature, pcmInterface, umlInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlOperation.isPresent() ? umlOperation.get() : null);
    	Optional<org.eclipse.uml2.uml.Parameter> umlReturnParam = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlReturnParam(pcmSignature, pcmInterface, umlInterface, umlOperation), // correspondence source supplier
    		org.eclipse.uml2.uml.Parameter.class,
    		(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmSignature, pcmInterface, umlInterface, umlOperation), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlReturnParam.isPresent() ? umlReturnParam.get() : null);
    userExecution.callRoutine1(pcmSignature, pcmInterface, umlInterface, umlOperation, umlReturnParam, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
