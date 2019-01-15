package mir.routines.umlSignatureOperationReactions;

import com.google.common.base.Objects;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlSignatureOperationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
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
public class DetectOrCreateCorrespondingSignatureRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingSignatureRoutine.ActionUserExecution userExecution;
  
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
    
    public String getRetrieveTag2(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface) {
      return TagLiterals.SIGNATURE__OPERATION;
    }
    
    public EObject getCorrepondenceSourcePcmSignature(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface) {
      return umlOperation;
    }
    
    public void callRoutine1(final Operation umlOperation, final Interface umlInterface, final OperationInterface pcmInterface, final Optional<OperationSignature> pcmSignature, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmSignature.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<OperationSignature, Boolean> _function = (OperationSignature it) -> {
          String _entityName = it.getEntityName();
          String _name = umlOperation.getName();
          return Boolean.valueOf(Objects.equal(_entityName, _name));
        };
        final OperationSignature pcmSignatureCandidate = IterableExtensions.<OperationSignature>findFirst(pcmInterface.getSignatures__OperationInterface(), _function);
        if ((pcmSignatureCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingSignature(umlOperation, pcmSignatureCandidate);
        } else {
          _routinesFacade.createCorrespondingSignature(umlOperation, umlInterface);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingSignatureRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOperation, final Interface umlInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlSignatureOperationReactions.DetectOrCreateCorrespondingSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlOperation = umlOperation;this.umlInterface = umlInterface;
  }
  
  private Operation umlOperation;
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingSignatureRoutine with input:");
    getLogger().debug("   umlOperation: " + this.umlOperation);
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlOperation, umlInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlOperation, umlInterface), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    	Optional<org.palladiosimulator.pcm.repository.OperationSignature> pcmSignature = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmSignature(umlOperation, umlInterface, pcmInterface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationSignature.class,
    		(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlOperation, umlInterface, pcmInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmSignature.isPresent() ? pcmSignature.get() : null);
    userExecution.callRoutine1(umlOperation, umlInterface, pcmInterface, pcmSignature, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
