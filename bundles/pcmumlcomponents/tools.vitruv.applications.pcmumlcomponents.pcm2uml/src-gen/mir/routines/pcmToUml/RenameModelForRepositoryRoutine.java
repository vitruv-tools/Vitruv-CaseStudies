package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameModelForRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameModelForRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Repository repository, final Model umlModel) {
      return umlModel;
    }
    
    public void update0Element(final Repository repository, final Model umlModel) {
      String _entityName = repository.getEntityName();
      umlModel.setName(_entityName);
    }
    
    public EObject getCorrepondenceSourceUmlModel(final Repository repository) {
      return repository;
    }
  }
  
  public RenameModelForRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository repository) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.RenameModelForRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.repository = repository;
  }
  
  private Repository repository;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameModelForRepositoryRoutine with input:");
    getLogger().debug("   Repository: " + this.repository);
    
    Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(repository), // correspondence source supplier
    	Model.class,
    	(Model _element) -> true, // correspondence precondition checker
    	null);
    if (umlModel == null) {
    	return;
    }
    initializeRetrieveElementState(umlModel);
    // val updatedElement userExecution.getElement1(repository, umlModel);
    userExecution.update0Element(repository, umlModel);
    
    postprocessElementStates();
  }
}
