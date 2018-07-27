package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameParameterRoutine extends AbstractRepairRoutineRealization {
  private RenameParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter parameter, final OrdinaryParameter javaParameter) {
      return javaParameter;
    }
    
    public void update0Element(final Parameter parameter, final OrdinaryParameter javaParameter) {
      javaParameter.setName(parameter.getParameterName());
    }
    
    public EObject getCorrepondenceSourceJavaParameter(final Parameter parameter) {
      return parameter;
    }
  }
  
  public RenameParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter parameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.RenameParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.parameter = parameter;
  }
  
  private Parameter parameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameParameterRoutine with input:");
    getLogger().debug("   parameter: " + this.parameter);
    
    org.emftext.language.java.parameters.OrdinaryParameter javaParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaParameter(parameter), // correspondence source supplier
    	org.emftext.language.java.parameters.OrdinaryParameter.class,
    	(org.emftext.language.java.parameters.OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaParameter == null) {
    	return false;
    }
    registerObjectUnderModification(javaParameter);
    // val updatedElement userExecution.getElement1(parameter, javaParameter);
    userExecution.update0Element(parameter, javaParameter);
    
    postprocessElements();
    
    return true;
  }
}
