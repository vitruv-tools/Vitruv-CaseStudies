package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetTypeOfUmlParameterOrPropertyRoutine extends AbstractRepairRoutineRealization {
  private SetTypeOfUmlParameterOrPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final DataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag, @Extension final RoutinesFacade _routinesFacade) {
      if (((pcmType != null) && (pcmType instanceof CollectionDataType))) {
        _routinesFacade.setTypeOfUmlParameterOrProperty_Collection(((CollectionDataType) pcmType), umlElement, umlMultiplicity, tag);
      } else {
        _routinesFacade.setTypeOfUmlParameterOrProperty_NonCollection(pcmType, umlElement, umlMultiplicity, tag);
      }
    }
  }
  
  public SetTypeOfUmlParameterOrPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.SetTypeOfUmlParameterOrPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.umlElement = umlElement;this.umlMultiplicity = umlMultiplicity;this.tag = tag;
  }
  
  private DataType pcmType;
  
  private TypedElement umlElement;
  
  private MultiplicityElement umlMultiplicity;
  
  private String tag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetTypeOfUmlParameterOrPropertyRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   umlElement: " + this.umlElement);
    getLogger().debug("   umlMultiplicity: " + this.umlMultiplicity);
    getLogger().debug("   tag: " + this.tag);
    
    userExecution.callRoutine1(pcmType, umlElement, umlMultiplicity, tag, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
