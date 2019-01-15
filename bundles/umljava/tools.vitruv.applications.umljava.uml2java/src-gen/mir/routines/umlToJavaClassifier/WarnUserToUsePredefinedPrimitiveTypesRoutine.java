package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionOptions;

@SuppressWarnings("all")
public class WarnUserToUsePredefinedPrimitiveTypesRoutine extends AbstractRepairRoutineRealization {
  private WarnUserToUsePredefinedPrimitiveTypesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(@Extension final RoutinesFacade _routinesFacade) {
      this.userInteractor.getNotificationDialogBuilder().message(
        ("Only predefined uml::PrimitiveTypes will be mapped." + "Please use the types defined in \"pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml\" instead.")).title("Warning").notificationType(UserInteractionOptions.NotificationType.WARNING).windowModality(UserInteractionOptions.WindowModality.MODAL).startInteraction();
    }
  }
  
  public WarnUserToUsePredefinedPrimitiveTypesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.WarnUserToUsePredefinedPrimitiveTypesRoutine.ActionUserExecution(getExecutionState(), this);
  }
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine WarnUserToUsePredefinedPrimitiveTypesRoutine with input:");
    
    userExecution.executeAction1(this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
