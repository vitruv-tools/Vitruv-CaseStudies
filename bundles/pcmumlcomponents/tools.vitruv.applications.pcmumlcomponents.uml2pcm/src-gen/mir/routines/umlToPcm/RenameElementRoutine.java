package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement umlElement, final org.palladiosimulator.pcm.core.entity.NamedElement pcmElement) {
      return pcmElement;
    }
    
    public void update0Element(final NamedElement umlElement, final org.palladiosimulator.pcm.core.entity.NamedElement pcmElement) {
      pcmElement.setEntityName(umlElement.getName());
    }
    
    public EObject getCorrepondenceSourcePcmElement(final NamedElement umlElement) {
      return umlElement;
    }
  }
  
  public RenameElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement umlElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.RenameElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlElement = umlElement;
  }
  
  private NamedElement umlElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameElementRoutine with input:");
    getLogger().debug("   NamedElement: " + this.umlElement);
    
    org.palladiosimulator.pcm.core.entity.NamedElement pcmElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmElement(umlElement), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.NamedElement.class,
    	(org.palladiosimulator.pcm.core.entity.NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (pcmElement == null) {
    	return;
    }
    initializeRetrieveElementState(pcmElement);
    // val updatedElement userExecution.getElement1(umlElement, pcmElement);
    userExecution.update0Element(umlElement, pcmElement);
    
    postprocessElementStates();
  }
}
