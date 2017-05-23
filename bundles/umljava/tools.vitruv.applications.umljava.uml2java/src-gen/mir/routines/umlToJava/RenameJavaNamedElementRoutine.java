package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaNamedElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaNamedElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement uElem, final String name, final org.emftext.language.java.commons.NamedElement jElem) {
      return jElem;
    }
    
    public void update0Element(final NamedElement uElem, final String name, final org.emftext.language.java.commons.NamedElement jElem) {
      jElem.setName(name);
    }
    
    public EObject getCorrepondenceSourceJElem(final NamedElement uElem, final String name) {
      return uElem;
    }
  }
  
  public RenameJavaNamedElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement uElem, final String name) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.RenameJavaNamedElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uElem = uElem;this.name = name;
  }
  
  private NamedElement uElem;
  
  private String name;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaNamedElementRoutine with input:");
    getLogger().debug("   NamedElement: " + this.uElem);
    getLogger().debug("   String: " + this.name);
    
    org.emftext.language.java.commons.NamedElement jElem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJElem(uElem, name), // correspondence source supplier
    	org.emftext.language.java.commons.NamedElement.class,
    	(org.emftext.language.java.commons.NamedElement _element) -> true, // correspondence precondition checker
    	null);
    if (jElem == null) {
    	return;
    }
    registerObjectUnderModification(jElem);
    // val updatedElement userExecution.getElement1(uElem, name, jElem);
    userExecution.update0Element(uElem, name, jElem);
    
    postprocessElements();
  }
}