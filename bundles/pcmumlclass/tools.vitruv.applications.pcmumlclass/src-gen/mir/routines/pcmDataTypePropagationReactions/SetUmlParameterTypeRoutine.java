package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private SetUmlParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final DataType pcmType, final Parameter umlParameter, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.setTypeOfUmlParameterOrProperty(pcmType, umlParameter, umlParameter, TagLiterals.COLLECTION_DATATYPE__PARAMETER);
    }
  }
  
  public SetUmlParameterTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType pcmType, final Parameter umlParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.SetUmlParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.umlParameter = umlParameter;
  }
  
  private DataType pcmType;
  
  private Parameter umlParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlParameterTypeRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    userExecution.callRoutine1(pcmType, umlParameter, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
