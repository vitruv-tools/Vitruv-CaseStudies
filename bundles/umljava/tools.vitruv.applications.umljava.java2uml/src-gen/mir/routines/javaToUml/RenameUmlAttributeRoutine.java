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
public class RenameUmlAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameUmlAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr) {
      return jAttr;
    }
    
    public EObject getElement1(final Field jAttr, final Property uAttr) {
      return uAttr;
    }
    
    public void update0Element(final Field jAttr, final Property uAttr) {
      String _name = jAttr.getName();
      uAttr.setName(_name);
    }
  }
  
  public RenameUmlAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.RenameUmlAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jAttr = jAttr;
  }
  
  private Field jAttr;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameUmlAttributeRoutine with input:");
    getLogger().debug("   Field: " + this.jAttr);
    
    Property uAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUAttr(jAttr), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (uAttr == null) {
    	return;
    }
    initializeRetrieveElementState(uAttr);
    // val updatedElement userExecution.getElement1(jAttr, uAttr);
    userExecution.update0Element(jAttr, uAttr);
    
    postprocessElementStates();
  }
}
