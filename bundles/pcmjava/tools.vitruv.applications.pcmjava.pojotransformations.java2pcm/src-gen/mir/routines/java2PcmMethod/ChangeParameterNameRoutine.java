package mir.routines.java2PcmMethod;

import edu.kit.ipd.sdq.commons.util.org.palladiosimulator.pcm.repository.ParameterUtil;
import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.parameters.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeParameterNameRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeParameterNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final String newName, final Parameter parameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public void update0Element(final String newName, final Parameter parameter, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      ParameterUtil.setName(pcmParameter, newName);
    }
    
    public EObject getCorrepondenceSourcePcmParameter(final String newName, final Parameter parameter) {
      return parameter;
    }
  }
  
  public ChangeParameterNameRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final String newName, final Parameter parameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.ChangeParameterNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.newName = newName;this.parameter = parameter;
  }
  
  private String newName;
  
  private Parameter parameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterNameRoutine with input:");
    getLogger().debug("   newName: " + this.newName);
    getLogger().debug("   parameter: " + this.parameter);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmParameter(newName, parameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Parameter.class,
    	(org.palladiosimulator.pcm.repository.Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (pcmParameter == null) {
    	return;
    }
    registerObjectUnderModification(pcmParameter);
    // val updatedElement userExecution.getElement1(newName, parameter, pcmParameter);
    userExecution.update0Element(newName, parameter, pcmParameter);
    
    postprocessElements();
  }
}
