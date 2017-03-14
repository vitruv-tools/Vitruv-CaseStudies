package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.modifiers.Modifier;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlClassVisibilityRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlClassVisibilityRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.classifiers.Class jClass, final Modifier mod, final org.eclipse.uml2.uml.Class uClass) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceUClass(final org.emftext.language.java.classifiers.Class jClass, final Modifier mod) {
      return jClass;
    }
    
    public void update0Element(final org.emftext.language.java.classifiers.Class jClass, final Modifier mod, final org.eclipse.uml2.uml.Class uClass) {
      VisibilityKind _umlVisibilityKind = JavaToUmlHelper.getUmlVisibilityKind(mod);
      uClass.setVisibility(_umlVisibilityKind);
    }
  }
  
  public ChangeUmlClassVisibilityRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.classifiers.Class jClass, final Modifier mod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.ChangeUmlClassVisibilityRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jClass = jClass;this.mod = mod;
  }
  
  private org.emftext.language.java.classifiers.Class jClass;
  
  private Modifier mod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlClassVisibilityRoutine with input:");
    getLogger().debug("   Class: " + this.jClass);
    getLogger().debug("   Modifier: " + this.mod);
    
    org.eclipse.uml2.uml.Class uClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUClass(jClass, mod), // correspondence source supplier
    	org.eclipse.uml2.uml.Class.class,
    	(org.eclipse.uml2.uml.Class _element) -> true, // correspondence precondition checker
    	null);
    if (uClass == null) {
    	return;
    }
    initializeRetrieveElementState(uClass);
    // val updatedElement userExecution.getElement1(jClass, mod, uClass);
    userExecution.update0Element(jClass, mod, uClass);
    
    postprocessElementStates();
  }
}
