package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.Modifier;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlAttributeVisibilityRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlAttributeVisibilityRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUAttr(final Field jAttr, final Modifier mod) {
      return jAttr;
    }
    
    public EObject getElement1(final Field jAttr, final Modifier mod, final Property uAttr) {
      return uAttr;
    }
    
    public void update0Element(final Field jAttr, final Modifier mod, final Property uAttr) {
      VisibilityKind _umlVisibilityKind = JavaToUmlHelper.getUmlVisibilityKind(mod);
      uAttr.setVisibility(_umlVisibilityKind);
    }
  }
  
  public ChangeUmlAttributeVisibilityRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttr, final Modifier mod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.ChangeUmlAttributeVisibilityRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jAttr = jAttr;this.mod = mod;
  }
  
  private Field jAttr;
  
  private Modifier mod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlAttributeVisibilityRoutine with input:");
    getLogger().debug("   Field: " + this.jAttr);
    getLogger().debug("   Modifier: " + this.mod);
    
    Property uAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUAttr(jAttr, mod), // correspondence source supplier
    	Property.class,
    	(Property _element) -> true, // correspondence precondition checker
    	null);
    if (uAttr == null) {
    	return;
    }
    initializeRetrieveElementState(uAttr);
    // val updatedElement userExecution.getElement1(jAttr, mod, uAttr);
    userExecution.update0Element(jAttr, mod, uAttr);
    
    postprocessElementStates();
  }
}
