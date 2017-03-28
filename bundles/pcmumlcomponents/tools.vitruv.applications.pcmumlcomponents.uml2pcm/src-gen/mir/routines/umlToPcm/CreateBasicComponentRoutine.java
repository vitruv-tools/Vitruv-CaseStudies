package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateBasicComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateBasicComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComponent, final Repository pcmRepository, final BasicComponent pcmComponent) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final Component umlComponent) {
      Model _model = umlComponent.getModel();
      return _model;
    }
    
    public void update0Element(final Component umlComponent, final Repository pcmRepository, final BasicComponent pcmComponent) {
      EList<RepositoryComponent> _components__Repository = pcmRepository.getComponents__Repository();
      _components__Repository.add(pcmComponent);
    }
    
    public void updatePcmComponentElement(final Component umlComponent, final Repository pcmRepository, final BasicComponent pcmComponent) {
      pcmComponent.setEntityName(umlComponent.getName());
    }
    
    public EObject getElement2(final Component umlComponent, final Repository pcmRepository, final BasicComponent pcmComponent) {
      return umlComponent;
    }
    
    public EObject getElement3(final Component umlComponent, final Repository pcmRepository, final BasicComponent pcmComponent) {
      return pcmComponent;
    }
    
    public String getTag1(final Component umlComponent, final Repository pcmRepository, final BasicComponent pcmComponent) {
      return "basic";
    }
  }
  
  public CreateBasicComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateBasicComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlComponent = umlComponent;
  }
  
  private Component umlComponent;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateBasicComponentRoutine with input:");
    getLogger().debug("   Component: " + this.umlComponent);
    
    Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlComponent), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	null);
    if (pcmRepository == null) {
    	return;
    }
    registerObjectUnderModification(pcmRepository);
    BasicComponent pcmComponent = RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    userExecution.updatePcmComponentElement(umlComponent, pcmRepository, pcmComponent);
    
    // val updatedElement userExecution.getElement1(umlComponent, pcmRepository, pcmComponent);
    userExecution.update0Element(umlComponent, pcmRepository, pcmComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(umlComponent, pcmRepository, pcmComponent), userExecution.getElement3(umlComponent, pcmRepository, pcmComponent), userExecution.getTag1(umlComponent, pcmRepository, pcmComponent));
    
    postprocessElements();
  }
}
