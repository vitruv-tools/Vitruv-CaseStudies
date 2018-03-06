package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaNamedElementRoutine extends AbstractRepairRoutineRealization {
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
  
  public RenameJavaNamedElementRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement uElem, final String name) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.RenameJavaNamedElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.uElem = uElem;this.name = name;
  }
  
  private NamedElement uElem;
  
  private String name;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaNamedElementRoutine with input:");
    getLogger().debug("   uElem: " + this.uElem);
    getLogger().debug("   name: " + this.name);
    
    org.emftext.language.java.commons.NamedElement jElem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJElem(uElem, name), // correspondence source supplier
    	org.emftext.language.java.commons.NamedElement.class,
    	(org.emftext.language.java.commons.NamedElement _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jElem == null) {
    	return false;
    }
    registerObjectUnderModification(jElem);
    // val updatedElement userExecution.getElement1(uElem, name, jElem);
    userExecution.update0Element(uElem, name, jElem);
    
    postprocessElements();
    
    return true;
  }
}
