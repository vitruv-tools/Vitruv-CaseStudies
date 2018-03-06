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
  
  public DeleteUmlMethodRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Member jMem) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.DeleteUmlMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.jMem = jMem;
  }
  
  private Member jMem;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteUmlMethodRoutine with input:");
    getLogger().debug("   jMem: " + this.jMem);
    
    org.eclipse.uml2.uml.NamedElement uMem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMem(jMem), // correspondence source supplier
    	org.eclipse.uml2.uml.NamedElement.class,
    	(org.eclipse.uml2.uml.NamedElement _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uMem == null) {
    	return false;
    }
    registerObjectUnderModification(uMem);
    deleteObject(userExecution.getElement1(jMem, uMem));
    
    postprocessElements();
    
    return true;
  }
}
