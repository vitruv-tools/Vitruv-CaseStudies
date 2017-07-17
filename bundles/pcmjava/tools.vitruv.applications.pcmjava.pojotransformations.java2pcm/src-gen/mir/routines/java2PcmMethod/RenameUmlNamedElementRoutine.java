package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameUmlNamedElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameUmlNamedElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUElement(final NamedElement jElement) {
      return jElement;
    }
    
    public EObject getElement1(final NamedElement jElement, final org.palladiosimulator.pcm.core.entity.NamedElement uElement) {
      return uElement;
    }
    
    public void update0Element(final NamedElement jElement, final org.palladiosimulator.pcm.core.entity.NamedElement uElement) {
      uElement.setEntityName(jElement.getName());
    }
  }
  
  public RenameUmlNamedElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement jElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.RenameUmlNamedElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.jElement = jElement;
  }
  
  private NamedElement jElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameUmlNamedElementRoutine with input:");
    getLogger().debug("   NamedElement: " + this.jElement);
    
    org.palladiosimulator.pcm.core.entity.NamedElement uElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUElement(jElement), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.NamedElement.class,
    	(org.palladiosimulator.pcm.core.entity.NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (uElement == null) {
    	return;
    }
    registerObjectUnderModification(uElement);
    // val updatedElement userExecution.getElement1(jElement, uElement);
    userExecution.update0Element(jElement, uElement);
    
    postprocessElements();
  }
}
