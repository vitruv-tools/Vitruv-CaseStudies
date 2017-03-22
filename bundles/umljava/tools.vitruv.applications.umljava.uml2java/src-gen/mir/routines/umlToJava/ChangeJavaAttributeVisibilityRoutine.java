package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaAttributeVisibilityRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeJavaAttributeVisibilityRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlAttr, final Field javaAttr) {
      return javaAttr;
    }
    
    public void update0Element(final Property umlAttr, final Field javaAttr) {
      VisibilityKind _visibility = umlAttr.getVisibility();
      UmlToJavaHelper.setJavaVisibility(javaAttr, _visibility);
    }
    
    public EObject getCorrepondenceSourceJavaAttr(final Property umlAttr) {
      return umlAttr;
    }
  }
  
  public ChangeJavaAttributeVisibilityRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlAttr) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.ChangeJavaAttributeVisibilityRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.umlAttr = umlAttr;
  }
  
  private Property umlAttr;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaAttributeVisibilityRoutine with input:");
    getLogger().debug("   Property: " + this.umlAttr);
    
    Field javaAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaAttr(umlAttr), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (javaAttr == null) {
    	return;
    }
    initializeRetrieveElementState(javaAttr);
    // val updatedElement userExecution.getElement1(umlAttr, javaAttr);
    userExecution.update0Element(umlAttr, javaAttr);
    
    postprocessElementStates();
  }
}
