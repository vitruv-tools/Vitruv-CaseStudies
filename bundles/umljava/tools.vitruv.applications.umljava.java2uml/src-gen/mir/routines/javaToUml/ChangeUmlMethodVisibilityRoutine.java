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
    
    public EObject getElement1(final Modifier mod, final ClassMethod jMeth, final Operation uMeth) {
      return uMeth;
    }
    
    public void update0Element(final Modifier mod, final ClassMethod jMeth, final Operation uMeth) {
      VisibilityKind _umlVisibilityKind = JavaToUmlHelper.getUmlVisibilityKind(mod);
      uMeth.setVisibility(_umlVisibilityKind);
    }
    
    public EObject getCorrepondenceSourceUMeth(final Modifier mod, final ClassMethod jMeth) {
      return jMeth;
    }
  }
  
  public ChangeUmlMethodVisibilityRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Modifier mod, final ClassMethod jMeth) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.ChangeUmlMethodVisibilityRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.mod = mod;this.jMeth = jMeth;
  }
  
  private Modifier mod;
  
  private ClassMethod jMeth;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeUmlMethodVisibilityRoutine with input:");
    getLogger().debug("   Modifier: " + this.mod);
    getLogger().debug("   ClassMethod: " + this.jMeth);
    
    Operation uMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMeth(mod, jMeth), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uMeth == null) {
    	return;
    }
    initializeRetrieveElementState(uMeth);
    // val updatedElement userExecution.getElement1(mod, jMeth, uMeth);
    userExecution.update0Element(mod, jMeth, uMeth);
    
    postprocessElementStates();
  }
}
