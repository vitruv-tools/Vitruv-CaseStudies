package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateUmlModelRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateUmlModelRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Repository pcmRepository, final Model umlModel) {
      return pcmRepository;
    }
    
    public EObject getElement2(final Repository pcmRepository, final Model umlModel) {
      return umlModel;
    }
    
    public void updateUmlModelElement(final Repository pcmRepository, final Model umlModel) {
      String _entityName = pcmRepository.getEntityName();
      umlModel.setName(_entityName);
      String _entityName_1 = pcmRepository.getEntityName();
      String _plus = ("model/" + _entityName_1);
      String _plus_1 = (_plus + ".uml");
      this.persistProjectRelative(pcmRepository, umlModel, _plus_1);
    }
  }
  
  public CreateUmlModelRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Repository pcmRepository) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.CreateUmlModelRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmRepository = pcmRepository;
  }
  
  private Repository pcmRepository;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateUmlModelRoutine with input:");
    getLogger().debug("   Repository: " + this.pcmRepository);
    
    Model umlModel = UMLFactoryImpl.eINSTANCE.createModel();
    initializeCreateElementState(umlModel);
    userExecution.updateUmlModelElement(pcmRepository, umlModel);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmRepository, umlModel), userExecution.getElement2(pcmRepository, umlModel), "");
    
    postprocessElementStates();
  }
}
