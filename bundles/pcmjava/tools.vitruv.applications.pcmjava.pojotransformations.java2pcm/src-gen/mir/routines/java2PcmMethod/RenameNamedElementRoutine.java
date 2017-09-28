package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameNamedElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameNamedElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement javaElement, final org.palladiosimulator.pcm.core.entity.NamedElement pcmElement) {
      return pcmElement;
    }
    
    public void update0Element(final NamedElement javaElement, final org.palladiosimulator.pcm.core.entity.NamedElement pcmElement) {
      pcmElement.setEntityName(javaElement.getName());
    }
    
    public EObject getCorrepondenceSourcePcmElement(final NamedElement javaElement) {
      return javaElement;
    }
  }
  
  public RenameNamedElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement javaElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.RenameNamedElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.javaElement = javaElement;
  }
  
  private NamedElement javaElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameNamedElementRoutine with input:");
    getLogger().debug("   javaElement: " + this.javaElement);
    
    org.palladiosimulator.pcm.core.entity.NamedElement pcmElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmElement(javaElement), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.NamedElement.class,
    	(org.palladiosimulator.pcm.core.entity.NamedElement _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmElement == null) {
    	return false;
    }
    registerObjectUnderModification(pcmElement);
    // val updatedElement userExecution.getElement1(javaElement, pcmElement);
    userExecution.update0Element(javaElement, pcmElement);
    
    postprocessElements();
    
    return true;
  }
}
