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
public class SetUmlMethodAbstractRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetUmlMethodAbstractRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ClassMethod jMeth, final Integer isAbstract, final Operation uMeth) {
      return uMeth;
    }
    
    public void update0Element(final ClassMethod jMeth, final Integer isAbstract, final Operation uMeth) {
      if (((isAbstract).intValue() == 1)) {
        uMeth.setIsAbstract(true);
      } else {
        if (((isAbstract).intValue() == 0)) {
          uMeth.setIsAbstract(false);
        } else {
          throw new IllegalArgumentException(("Invalid isAbstract Value: " + isAbstract));
        }
      }
    }
    
    public EObject getCorrepondenceSourceUMeth(final ClassMethod jMeth, final Integer isAbstract) {
      return jMeth;
    }
  }
  
  public SetUmlMethodAbstractRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ClassMethod jMeth, final Integer isAbstract) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.SetUmlMethodAbstractRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jMeth = jMeth;this.isAbstract = isAbstract;
  }
  
  private ClassMethod jMeth;
  
  private Integer isAbstract;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlMethodAbstractRoutine with input:");
    getLogger().debug("   ClassMethod: " + this.jMeth);
    getLogger().debug("   Integer: " + this.isAbstract);
    
    Operation uMeth = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUMeth(jMeth, isAbstract), // correspondence source supplier
    	Operation.class,
    	(Operation _element) -> true, // correspondence precondition checker
    	null);
    if (uMeth == null) {
    	return;
    }
    initializeRetrieveElementState(uMeth);
    // val updatedElement userExecution.getElement1(jMeth, isAbstract, uMeth);
    userExecution.update0Element(jMeth, isAbstract, uMeth);
    
    postprocessElementStates();
  }
}
