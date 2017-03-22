package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.modifiers.Modifier;
import tools.vitruv.applications.umljava.java2uml.JavaToUmlHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeUmlMethodVisibilityRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeUmlMethodVisibilityRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ClassMethod jMeth, final Modifier mod, final Operation uMeth) {
      return uMeth;
    }
    
    public void update0Element(final ClassMethod jMeth, final Modifier mod, final Operation uMeth) {
      VisibilityKind _umlVisibilityKind = JavaToUmlHelper.getUmlVisibilityKind(mod);
      uMeth.setVisibility(_umlVisibilityKind);
    }
    
    public EObject getCorrepondenceSourceUMeth(final ClassMethod jMeth, final Modifier mod) {
      return jMeth;
    }
  }
  
  public ChangeUmlMethodVisibilityRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod jMeth, final Modifier mod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.ChangeUmlMethodVisibilityRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.mod = mod;
  }
  
  private ClassMethod jMeth;
  
  private Modifier mod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlMethodVisibilityRoutine with input:");
    getLogger().debug("   ClassMethod: " + this.jMeth);
    getLogger().debug("   Modifier: " + this.mod);
    
    Operation uMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMeth(jMeth, mod), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uMeth == null) {
    	return;
    }
    initializeRetrieveElementState(uMeth);
    // val updatedElement userExecution.getElement1(jMeth, mod, uMeth);
    userExecution.update0Element(jMeth, mod, uMeth);
    
    postprocessElementStates();
  }
}
