package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionOptions;

@SuppressWarnings("all")
public class UnsupportedPrimitiveTypeSetWarningRoutine extends AbstractRepairRoutineRealization {
  private UnsupportedPrimitiveTypeSetWarningRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final PrimitiveDataType primitive, final EObject entity, @Extension final RoutinesFacade _routinesFacade) {
      this.userInteractor.getNotificationDialogBuilder().message(((((("The pcm::PrimitiveDataType " + primitive) + " set at the pcm element ") + entity) + " has no good match in the predefined set of uml::PrimitiveTypes ") + " used by these transformations and is therefore not supported.")).notificationType(UserInteractionOptions.NotificationType.WARNING).startInteraction();
    }
  }
  
  public UnsupportedPrimitiveTypeSetWarningRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveDataType primitive, final EObject entity) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.UnsupportedPrimitiveTypeSetWarningRoutine.ActionUserExecution(getExecutionState(), this);
    this.primitive = primitive;this.entity = entity;
  }
  
  private PrimitiveDataType primitive;
  
  private EObject entity;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UnsupportedPrimitiveTypeSetWarningRoutine with input:");
    getLogger().debug("   primitive: " + this.primitive);
    getLogger().debug("   entity: " + this.entity);
    
    userExecution.executeAction1(primitive, entity, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
