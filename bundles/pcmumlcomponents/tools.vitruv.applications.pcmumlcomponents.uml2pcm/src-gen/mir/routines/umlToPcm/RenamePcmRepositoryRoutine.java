package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenamePcmRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenamePcmRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model model, final Repository repository) {
      return repository;
    }
    
    public void update0Element(final Model model, final Repository repository) {
      String _name = model.getName();
      repository.setEntityName(_name);
    }
    
    public EObject getCorrepondenceSourceRepository(final Model model) {
      return model;
    }
  }
  
  public RenamePcmRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model model) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.RenamePcmRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.model = model;
  }
  
  private Model model;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenamePcmRepositoryRoutine with input:");
    getLogger().debug("   Model: " + this.model);
    
    Repository repository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRepository(model), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	null);
    if (repository == null) {
    	return;
    }
    initializeRetrieveElementState(repository);
    // val updatedElement userExecution.getElement1(model, repository);
    userExecution.update0Element(model, repository);
    
    postprocessElementStates();
  }
}
