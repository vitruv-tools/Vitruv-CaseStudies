package mir.routines.umlToPcm;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.uml2.uml.Component;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreatePcmComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePcmComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Component umlComponent, @Extension final RoutinesFacade _routinesFacade) {
      final String userPromptMsg = "Please select whether this component can have subcomponents.";
      final List<String> options = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList("Yes", "No"));
      final int choice = this.userInteracting.selectFromMessage(UserInteractionType.MODAL, userPromptMsg, ((String[])Conversions.unwrapArray(options, String.class)));
      if ((choice == 0)) {
        _routinesFacade.createCompositeComponent(umlComponent);
      } else {
        _routinesFacade.createBasicComponent(umlComponent);
      }
    }
  }
  
  public CreatePcmComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreatePcmComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlComponent = umlComponent;
  }
  
  private Component umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePcmComponentRoutine with input:");
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    userExecution.callRoutine1(umlComponent, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
