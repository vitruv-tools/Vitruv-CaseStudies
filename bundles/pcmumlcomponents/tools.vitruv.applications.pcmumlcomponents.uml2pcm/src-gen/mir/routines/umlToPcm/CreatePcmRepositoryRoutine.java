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
public class CreatePcmRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePcmRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Model umlModel, final Repository pcmRepository) {
      return umlModel;
    }
    
    public void updatePcmRepositoryElement(final Model umlModel, final Repository pcmRepository) {
      pcmRepository.setEntityName(umlModel.getName());
      String _name = umlModel.getName();
      String _plus = ("repository/" + _name);
      String _plus_1 = (_plus + ".repository");
      this.persistProjectRelative(umlModel, pcmRepository, _plus_1);
    }
    
    public EObject getElement2(final Model umlModel, final Repository pcmRepository) {
      return pcmRepository;
    }
  }
  
  public CreatePcmRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Model umlModel) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreatePcmRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlModel = umlModel;
  }
  
  private Model umlModel;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePcmRepositoryRoutine with input:");
    getLogger().debug("   umlModel: " + this.umlModel);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createRepository();
    notifyObjectCreated(pcmRepository);
    userExecution.updatePcmRepositoryElement(umlModel, pcmRepository);
    
    addCorrespondenceBetween(userExecution.getElement1(umlModel, pcmRepository), userExecution.getElement2(umlModel, pcmRepository), "");
    
    postprocessElements();
    
    return true;
  }
}
