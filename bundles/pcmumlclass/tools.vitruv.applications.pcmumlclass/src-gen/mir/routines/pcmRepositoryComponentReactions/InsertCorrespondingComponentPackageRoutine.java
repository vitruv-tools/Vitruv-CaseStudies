package mir.routines.pcmRepositoryComponentReactions;

import java.io.IOException;
import mir.routines.pcmRepositoryComponentReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingComponentPackageRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingComponentPackageRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final RepositoryComponent pcmComponent, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingComponentImplementation(pcmComponent);
    }
    
    public void callRoutine3(final RepositoryComponent pcmComponent, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingComponentConstructor(pcmComponent);
    }
    
    public void callRoutine4(final RepositoryComponent pcmComponent, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingComponentPackage(pcmComponent, pcmRepository);
    }
    
    public void callRoutine1(final RepositoryComponent pcmComponent, final Repository pcmRepository, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingComponentPackage(pcmComponent, pcmRepository);
    }
  }
  
  public InsertCorrespondingComponentPackageRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final RepositoryComponent pcmComponent, final Repository pcmRepository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRepositoryComponentReactions.InsertCorrespondingComponentPackageRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.pcmRepository = pcmRepository;
  }
  
  private RepositoryComponent pcmComponent;
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingComponentPackageRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    userExecution.callRoutine1(pcmComponent, pcmRepository, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmComponent, pcmRepository, this.getRoutinesFacade());
    
    userExecution.callRoutine3(pcmComponent, pcmRepository, this.getRoutinesFacade());
    
    userExecution.callRoutine4(pcmComponent, pcmRepository, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
