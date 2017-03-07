package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.palladiosimulator.pcm.core.entity.Entity;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Element umlElement, final Entity pcmElement) {
      return pcmElement;
    }
    
    public EObject getCorrepondenceSourcePcmElement(final Element umlElement) {
      return umlElement;
    }
  }
  
  public DeleteElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Element umlElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.DeleteElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlElement = umlElement;
  }
  
  private Element umlElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteElementRoutine with input:");
    getLogger().debug("   Element: " + this.umlElement);
    
    Entity pcmElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmElement(umlElement), // correspondence source supplier
    	Entity.class,
    	(Entity _element) -> true, // correspondence precondition checker
    	null);
    if (pcmElement == null) {
    	return;
    }
    initializeRetrieveElementState(pcmElement);
    deleteObject(userExecution.getElement1(umlElement, pcmElement));
    
    postprocessElementStates();
  }
}
