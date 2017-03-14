package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlAttributeFinalRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlAttributeFinalRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr, final Integer isFinal) {
      return jAttr;
    }
    
    public EObject getElement1(final Field jAttr, final Integer isFinal, final Property uAttr) {
      return uAttr;
    }
    
    public void update0Element(final Field jAttr, final Integer isFinal, final Property uAttr) {
      if (((isFinal).intValue() == 1)) {
        uAttr.setIsReadOnly(true);
      } else {
        if (((isFinal).intValue() == 0)) {
          uAttr.setIsReadOnly(false);
        } else {
          throw new IllegalArgumentException(("Invalid isFinal value: " + isFinal));
        }
      }
    }
  }
  
  public SetUmlAttributeFinalRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr, final Integer isFinal) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.SetUmlAttributeFinalRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jAttr = jAttr;this.isFinal = isFinal;
  }
  
  private Field jAttr;
  
  private Integer isFinal;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlAttributeFinalRoutine with input:");
    getLogger().debug("   Field: " + this.jAttr);
    getLogger().debug("   Integer: " + this.isFinal);
    
    Property uAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUAttr(jAttr, isFinal), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (uAttr == null) {
    	return;
    }
    initializeRetrieveElementState(uAttr);
    // val updatedElement userExecution.getElement1(jAttr, isFinal, uAttr);
    userExecution.update0Element(jAttr, isFinal, uAttr);
    
    postprocessElementStates();
  }
}
