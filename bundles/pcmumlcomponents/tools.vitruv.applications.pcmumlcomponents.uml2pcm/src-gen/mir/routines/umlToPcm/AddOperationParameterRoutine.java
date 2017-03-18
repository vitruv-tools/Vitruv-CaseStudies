package mir.routines.umlToPcm;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Signature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddOperationParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddOperationParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation, final Parameter umlParameter) {
      return umlOperation;
    }
    
    public void callRoutine1(final Operation umlOperation, final Parameter umlParameter, final Signature pcmSignature, @Extension final RoutinesFacade _routinesFacade) {
      ParameterDirectionKind _direction = umlParameter.getDirection();
      boolean _equals = Objects.equal(_direction, ParameterDirectionKind.RETURN_LITERAL);
      if (_equals) {
        _routinesFacade.changeInterfaceOperationType(umlOperation, umlParameter);
      } else {
        _routinesFacade.addInterfaceOperationParameter(umlOperation, umlParameter);
      }
    }
  }
  
  public AddOperationParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Parameter umlParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.AddOperationParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlParameter = umlParameter;
  }
  
  private Operation umlOperation;
  
  private Parameter umlParameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddOperationParameterRoutine with input:");
    getLogger().debug("   Operation: " + this.umlOperation);
    getLogger().debug("   Parameter: " + this.umlParameter);
    
    Signature pcmSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmSignature(umlOperation, umlParameter), // correspondence source supplier
    	Signature.class,
    	(Signature _element) -> true, // correspondence precondition checker
    	null);
    if (pcmSignature == null) {
    	return;
    }
    initializeRetrieveElementState(pcmSignature);
    userExecution.callRoutine1(umlOperation, umlParameter, pcmSignature, actionsFacade);
    
    postprocessElementStates();
  }
}
