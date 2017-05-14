package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateMultiplicityTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UpdateMultiplicityTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmElement(final MultiplicityElement umlElement) {
      return umlElement;
    }
    
    public void callRoutine1(final MultiplicityElement umlElement, final EObject pcmElement, @Extension final RoutinesFacade _routinesFacade) {
      final boolean isCollection = ((umlElement.lowerBound() != 1) || (umlElement.upperBound() != 1));
    }
  }
  
  public UpdateMultiplicityTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final MultiplicityElement umlElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.UpdateMultiplicityTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlElement = umlElement;
  }
  
  private MultiplicityElement umlElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateMultiplicityTypeRoutine with input:");
    getLogger().debug("   MultiplicityElement: " + this.umlElement);
    
    EObject pcmElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmElement(umlElement), // correspondence source supplier
    	EObject.class,
    	(EObject _element) -> true, // correspondence precondition checker
    	null);
    if (pcmElement == null) {
    	return;
    }
    registerObjectUnderModification(pcmElement);
    userExecution.callRoutine1(umlElement, pcmElement, actionsFacade);
    
    postprocessElements();
  }
}
