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
public class SetUmlAttributeStaticRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlAttributeStaticRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr, final Integer isStatic) {
      return jAttr;
    }
    
    public EObject getElement1(final Field jAttr, final Integer isStatic, final Property uAttr) {
      return uAttr;
    }
    
    public void update0Element(final Field jAttr, final Integer isStatic, final Property uAttr) {
      if (((isStatic).intValue() == 1)) {
        uAttr.setIsStatic(true);
      } else {
        if (((isStatic).intValue() == 0)) {
          uAttr.setIsStatic(false);
        } else {
          throw new IllegalArgumentException(("Invalid isStatic value: " + isStatic));
        }
      }
    }
  }
  
  public SetUmlAttributeStaticRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr, final Integer isStatic) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.SetUmlAttributeStaticRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jAttr = jAttr;this.isStatic = isStatic;
  }
  
  private Field jAttr;
  
  private Integer isStatic;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlAttributeStaticRoutine with input:");
    getLogger().debug("   Field: " + this.jAttr);
    getLogger().debug("   Integer: " + this.isStatic);
    
    Property uAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUAttr(jAttr, isStatic), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (uAttr == null) {
    	return;
    }
    initializeRetrieveElementState(uAttr);
    // val updatedElement userExecution.getElement1(jAttr, isStatic, uAttr);
    userExecution.update0Element(jAttr, isStatic, uAttr);
    
    postprocessElementStates();
  }
}
