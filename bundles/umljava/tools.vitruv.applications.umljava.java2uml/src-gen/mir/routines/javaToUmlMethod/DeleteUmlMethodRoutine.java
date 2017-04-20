package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.emftext.language.java.members.Member;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteUmlMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteUmlMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Member jMem, final NamedElement uMem) {
      return uMem;
    }
    
    public EObject getCorrepondenceSourceUMem(final Member jMem) {
      return jMem;
    }
  }
  
  public DeleteUmlMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Member jMem) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jMem = jMem;
  }
  
  private Member jMem;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteUmlMethodRoutine with input:");
    getLogger().debug("   Member: " + this.jMem);
    
    NamedElement uMem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMem(jMem), // correspondence source supplier
    	NamedElement.class,
    	(NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (uMem == null) {
    	return;
    }
    registerObjectUnderModification(uMem);
    deleteObject(userExecution.getElement1(jMem, uMem));
    
    postprocessElements();
  }
}
