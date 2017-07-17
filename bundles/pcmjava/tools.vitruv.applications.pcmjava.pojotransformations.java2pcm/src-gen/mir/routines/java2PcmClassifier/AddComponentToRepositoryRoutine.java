package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddComponentToRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddComponentToRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ImplementationComponentType pcmComponent, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public void update0Element(final ImplementationComponentType pcmComponent, final Repository pcmRepository) {
      EList<RepositoryComponent> _components__Repository = pcmRepository.getComponents__Repository();
      _components__Repository.add(pcmComponent);
    }
  }
  
  public AddComponentToRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ImplementationComponentType pcmComponent, final Repository pcmRepository) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.AddComponentToRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.pcmRepository = pcmRepository;
  }
  
  private ImplementationComponentType pcmComponent;
  
  private Repository pcmRepository;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddComponentToRepositoryRoutine with input:");
    getLogger().debug("   ImplementationComponentType: " + this.pcmComponent);
    getLogger().debug("   Repository: " + this.pcmRepository);
    
    // val updatedElement userExecution.getElement1(pcmComponent, pcmRepository);
    userExecution.update0Element(pcmComponent, pcmRepository);
    
    postprocessElements();
  }
}
