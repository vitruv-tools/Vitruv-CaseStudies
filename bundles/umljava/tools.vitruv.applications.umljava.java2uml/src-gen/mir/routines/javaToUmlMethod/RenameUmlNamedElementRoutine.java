package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.commons.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameUmlNamedElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameUmlNamedElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUElement(final NamedElement jElement) {
      return jElement;
    }
    
    public EObject getElement1(final NamedElement jElement, final org.eclipse.uml2.uml.NamedElement uElement) {
      return uElement;
    }
    
    public void update0Element(final NamedElement jElement, final org.eclipse.uml2.uml.NamedElement uElement) {
      uElement.setName(jElement.getName());
    }
  }
  
  public RenameUmlNamedElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement jElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.RenameUmlNamedElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUmlMethod.RoutinesFacade(getExecutionState(), this);
    this.jElement = jElement;
  }
  
  private NamedElement jElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameUmlNamedElementRoutine with input:");
    getLogger().debug("   jElement: " + this.jElement);
    
    org.eclipse.uml2.uml.NamedElement uElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUElement(jElement), // correspondence source supplier
    	org.eclipse.uml2.uml.NamedElement.class,
    	(org.eclipse.uml2.uml.NamedElement _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uElement == null) {
    	return false;
    }
    registerObjectUnderModification(uElement);
    // val updatedElement userExecution.getElement1(jElement, uElement);
    userExecution.update0Element(jElement, uElement);
    
    postprocessElements();
    
    return true;
  }
}
