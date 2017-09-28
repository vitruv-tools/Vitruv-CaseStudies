package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateOperationProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateOperationProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final TypeReference typeReference, @Extension final RoutinesFacade _routinesFacade) {
      EObject _eContainer = typeReference.eContainer();
      final org.emftext.language.java.classifiers.Class javaClass = ((org.emftext.language.java.classifiers.Class) _eContainer);
      Classifier javaInterfaceClassifier = Java2PcmHelper.getTargetClassifierFromImplementsReferenceAndNormalizeURI(typeReference);
      _routinesFacade.createOperationProvidedRoleFromTypeReference(javaInterfaceClassifier, javaClass, typeReference);
    }
  }
  
  public CreateOperationProvidedRoleRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypeReference typeReference) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreateOperationProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.typeReference = typeReference;
  }
  
  private TypeReference typeReference;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateOperationProvidedRoleRoutine with input:");
    getLogger().debug("   typeReference: " + this.typeReference);
    
    userExecution.callRoutine1(typeReference, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
