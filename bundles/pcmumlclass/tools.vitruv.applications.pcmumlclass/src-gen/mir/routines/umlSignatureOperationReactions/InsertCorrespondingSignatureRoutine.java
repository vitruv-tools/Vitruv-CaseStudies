package mir.routines.umlSignatureOperationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlSignatureOperationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingSignatureRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Operation umlOperation, final Interface umlInterface) {
      return umlInterface;
    }
    
    public String getRetrieveTag1(final Operation umlOperation, final Interface umlInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void callRoutine1(final Operation umlOperation, final Interface umlInterface, final Optional<OperationInterface> pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmInterface.isPresent();
      if (_isPresent) {
        _routinesFacade.detectOrCreateCorrespondingSignature(umlOperation, umlInterface);
        _routinesFacade.moveCorrespondingSignature(umlOperation, umlInterface);
      } else {
        _routinesFacade.deleteCorrespondingSignature(umlOperation);
      }
    }
  }
  
  public InsertCorrespondingSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Interface umlInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlSignatureOperationReactions.InsertCorrespondingSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlInterface = umlInterface;
  }
  
  private Operation umlOperation;
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingSignatureRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInterface(umlOperation, umlInterface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlOperation, umlInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInterface.isPresent() ? pcmInterface.get() : null);
    userExecution.callRoutine1(umlOperation, umlInterface, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
