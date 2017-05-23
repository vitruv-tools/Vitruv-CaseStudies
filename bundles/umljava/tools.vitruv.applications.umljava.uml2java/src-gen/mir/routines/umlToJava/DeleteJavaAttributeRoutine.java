package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteJavaAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteJavaAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlAttr, final Field jAttr) {
      return jAttr;
    }
    
    public EObject getCorrepondenceSourceJAttr(final Property umlAttr) {
      return umlAttr;
    }
  }
  
  public DeleteJavaAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.DeleteJavaAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlAttr = umlAttr;
  }
  
  private Property umlAttr;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaAttributeRoutine with input:");
    getLogger().debug("   Property: " + this.umlAttr);
    
    Field jAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJAttr(umlAttr), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (jAttr == null) {
    	return;
    }
    registerObjectUnderModification(jAttr);
    deleteObject(userExecution.getElement1(umlAttr, jAttr));
    
    postprocessElements();
  }
}