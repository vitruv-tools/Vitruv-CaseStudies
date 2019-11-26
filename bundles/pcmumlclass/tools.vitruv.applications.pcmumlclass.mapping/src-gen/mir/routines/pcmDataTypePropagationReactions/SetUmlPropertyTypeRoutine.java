package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetUmlPropertyTypeRoutine extends AbstractRepairRoutineRealization {
  private SetUmlPropertyTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final DataType pcmType, final Property umlProperty, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.setTypeOfUmlParameterOrProperty(pcmType, umlProperty, umlProperty, TagLiterals.COLLECTION_DATATYPE__PROPERTY);
    }
  }
  
  public SetUmlPropertyTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType pcmType, final Property umlProperty) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.SetUmlPropertyTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.umlProperty = umlProperty;
  }
  
  private DataType pcmType;
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetUmlPropertyTypeRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    userExecution.callRoutine1(pcmType, umlProperty, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
