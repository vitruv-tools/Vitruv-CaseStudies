package mir.routines.javaToUmlMethod;

import java.io.IOException;
import mir.routines.javaToUmlMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Modifier;
import tools.vitruv.applications.umljava.util.java.JavaModifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlNamedElementVisibilityRoutine extends AbstractRepairRoutineRealization {
  private ChangeUmlNamedElementVisibilityRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final AnnotableAndModifiable jElem, final Modifier mod, final NamedElement uElem) {
      return uElem;
    }
    
    public void update0Element(final AnnotableAndModifiable jElem, final Modifier mod, final NamedElement uElem) {
      uElem.setVisibility(JavaModifierUtil.getUMLVisibilityKindFromJavaModifier(mod));
    }
    
    public EObject getCorrepondenceSourceUElem(final AnnotableAndModifiable jElem, final Modifier mod) {
      return jElem;
    }
  }
  
  public ChangeUmlNamedElementVisibilityRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AnnotableAndModifiable jElem, final Modifier mod) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlMethod.ChangeUmlNamedElementVisibilityRoutine.ActionUserExecution(getExecutionState(), this);
    this.jElem = jElem;this.mod = mod;
  }
  
  private AnnotableAndModifiable jElem;
  
  private Modifier mod;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlNamedElementVisibilityRoutine with input:");
    getLogger().debug("   jElem: " + this.jElem);
    getLogger().debug("   mod: " + this.mod);
    
    org.eclipse.uml2.uml.NamedElement uElem = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUElem(jElem, mod), // correspondence source supplier
    	org.eclipse.uml2.uml.NamedElement.class,
    	(org.eclipse.uml2.uml.NamedElement _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (uElem == null) {
    	return false;
    }
    registerObjectUnderModification(uElem);
    // val updatedElement userExecution.getElement1(jElem, mod, uElem);
    userExecution.update0Element(jElem, mod, uElem);
    
    postprocessElements();
    
    return true;
  }
}
