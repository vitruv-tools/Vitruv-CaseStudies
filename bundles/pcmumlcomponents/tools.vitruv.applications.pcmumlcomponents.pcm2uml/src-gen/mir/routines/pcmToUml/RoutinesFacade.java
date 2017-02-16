package mir.routines.pcmToUml;

import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createUmlModel(final Repository pcmRepository) {
    mir.routines.pcmToUml.CreateUmlModelRoutine effect = new mir.routines.pcmToUml.CreateUmlModelRoutine(this.executionState, calledBy,
    	pcmRepository);
    effect.applyRoutine();
  }
  
  public void renameModelForRepository(final Repository repository) {
    mir.routines.pcmToUml.RenameModelForRepositoryRoutine effect = new mir.routines.pcmToUml.RenameModelForRepositoryRoutine(this.executionState, calledBy,
    	repository);
    effect.applyRoutine();
  }
  
  public void createUmlComponent(final RepositoryComponent pcmComponent) {
    mir.routines.pcmToUml.CreateUmlComponentRoutine effect = new mir.routines.pcmToUml.CreateUmlComponentRoutine(this.executionState, calledBy,
    	pcmComponent);
    effect.applyRoutine();
  }
}
