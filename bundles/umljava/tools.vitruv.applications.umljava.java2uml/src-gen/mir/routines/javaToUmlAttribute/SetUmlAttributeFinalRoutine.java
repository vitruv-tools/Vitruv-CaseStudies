package mir.routines.javaToUmlAttribute;

import java.io.IOException;
import mir.routines.javaToUmlAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlAttributeFinalRoutine extends AbstractRepairRoutineRealization {
  private SetUmlAttributeFinalRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr, final Boolean isFinal) {
      return jAttr;
    }
    
    public EObject getElement1(final Field jAttr, final Boolean isFinal, final Property uAttr) {
      return uAttr;
    }
    
    public void update0Element(final Field jAttr, final Boolean isFinal, final Property uAttr) {
      uAttr.setIsReadOnly((isFinal).booleanValue());
    }
  }
  
  public SetUmlAttributeFinalRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr, final Boolean isFinal) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine.ActionUserExecution(getExecutionState(), this);
    this.jAttr = jAttr;this.isFinal = isFinal;
  }
  
  private Field jAttr;
  
  private Boolean isFinal;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlAttributeFinalRoutine with input:");
    getLogger().debug("   jAttr: " + this.jAttr);
    getLogger().debug("   isFinal: " + this.isFinal);
    
    org.eclipse.uml2.uml.Property uAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUAttr(jAttr, isFinal), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uAttr == null) {
    	return false;
    }
    registerObjectUnderModification(uAttr);
    // val updatedElement userExecution.getElement1(jAttr, isFinal, uAttr);
    userExecution.update0Element(jAttr, isFinal, uAttr);
    
    postprocessElements();
    
    return true;
  }
}
