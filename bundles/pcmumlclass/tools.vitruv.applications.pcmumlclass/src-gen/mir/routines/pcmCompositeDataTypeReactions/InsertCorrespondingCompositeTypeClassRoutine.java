package mir.routines.pcmCompositeDataTypeReactions;

import java.io.IOException;
import mir.routines.pcmCompositeDataTypeReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingCompositeTypeClassRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingCompositeTypeClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final CompositeDataType pcmType, final Repository pcmRepo, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingCompositeTypeClass(pcmType, pcmRepo);
    }
    
    public void callRoutine1(final CompositeDataType pcmType, final Repository pcmRepo, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingCompositeTypeClass(pcmType, pcmRepo);
    }
  }
  
  public InsertCorrespondingCompositeTypeClassRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType pcmType, final Repository pcmRepo) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmCompositeDataTypeReactions.InsertCorrespondingCompositeTypeClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.pcmRepo = pcmRepo;
  }
  
  private CompositeDataType pcmType;
  
  private Repository pcmRepo;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingCompositeTypeClassRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   pcmRepo: " + this.pcmRepo);
    
    userExecution.callRoutine1(pcmType, pcmRepo, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmType, pcmRepo, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
