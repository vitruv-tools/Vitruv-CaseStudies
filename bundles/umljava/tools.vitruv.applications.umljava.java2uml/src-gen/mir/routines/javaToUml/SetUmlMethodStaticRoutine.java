package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlMethodStaticRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlMethodStaticRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ClassMethod jMeth, final Integer isStatic, final Operation uMeth) {
      return uMeth;
    }
    
    public void update0Element(final ClassMethod jMeth, final Integer isStatic, final Operation uMeth) {
      if (((isStatic).intValue() == 1)) {
        uMeth.setIsStatic(true);
      } else {
        if (((isStatic).intValue() == 0)) {
          uMeth.setIsStatic(false);
        } else {
          throw new IllegalArgumentException(("Invalid isStatic Value: " + isStatic));
        }
      }
    }
    
    public EObject getCorrepondenceSourceUMeth(final ClassMethod jMeth, final Integer isStatic) {
      return jMeth;
    }
  }
  
  public SetUmlMethodStaticRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod jMeth, final Integer isStatic) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.SetUmlMethodStaticRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.isStatic = isStatic;
  }
  
  private ClassMethod jMeth;
  
  private Integer isStatic;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlMethodStaticRoutine with input:");
    getLogger().debug("   ClassMethod: " + this.jMeth);
    getLogger().debug("   Integer: " + this.isStatic);
    
    Operation uMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMeth(jMeth, isStatic), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uMeth == null) {
    	return;
    }
    initializeRetrieveElementState(uMeth);
    // val updatedElement userExecution.getElement1(jMeth, isStatic, uMeth);
    userExecution.update0Element(jMeth, isStatic, uMeth);
    
    postprocessElementStates();
  }
}
