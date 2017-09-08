package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameUmlElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameUmlElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement pcmElement, final org.eclipse.uml2.uml.NamedElement umlElement) {
      return umlElement;
    }
    
    public void update0Element(final NamedElement pcmElement, final org.eclipse.uml2.uml.NamedElement umlElement) {
      umlElement.setName(pcmElement.getEntityName());
    }
    
    public EObject getCorrepondenceSourceUmlElement(final NamedElement pcmElement) {
      return pcmElement;
    }
  }
  
  public RenameUmlElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement pcmElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.RenameUmlElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmElement = pcmElement;
  }
  
  private NamedElement pcmElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameUmlElementRoutine with input:");
    getLogger().debug("   pcmElement: " + this.pcmElement);
    
    org.eclipse.uml2.uml.NamedElement umlElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlElement(pcmElement), // correspondence source supplier
    	org.eclipse.uml2.uml.NamedElement.class,
    	(org.eclipse.uml2.uml.NamedElement _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlElement == null) {
    	return false;
    }
    registerObjectUnderModification(umlElement);
    // val updatedElement userExecution.getElement1(pcmElement, umlElement);
    userExecution.update0Element(pcmElement, umlElement);
    
    postprocessElements();
    
    return true;
  }
}
