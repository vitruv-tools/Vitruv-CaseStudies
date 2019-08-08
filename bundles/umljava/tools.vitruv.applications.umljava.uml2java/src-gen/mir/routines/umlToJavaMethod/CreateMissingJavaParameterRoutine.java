package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateMissingJavaParameterRoutine extends AbstractRepairRoutineRealization {
  private CreateMissingJavaParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSource1(final Parameter uParameter) {
      return uParameter;
    }
    
    public void callRoutine1(final Parameter uParameter, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaParameter(uParameter.getOperation(), uParameter);
    }
  }
  
  public CreateMissingJavaParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter uParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateMissingJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.uParameter = uParameter;
  }
  
  private Parameter uParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateMissingJavaParameterRoutine with input:");
    getLogger().debug("   uParameter: " + this.uParameter);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uParameter), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    userExecution.callRoutine1(uParameter, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
