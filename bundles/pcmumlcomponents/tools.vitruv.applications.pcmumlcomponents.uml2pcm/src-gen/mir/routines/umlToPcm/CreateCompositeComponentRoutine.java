package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompositeComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Component umlComponent, final Repository pcmRepository, final CompositeComponent pcmComponent) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final Component umlComponent) {
      Model _model = umlComponent.getModel();
      return _model;
    }
    
    public void update0Element(final Component umlComponent, final Repository pcmRepository, final CompositeComponent pcmComponent) {
      EList<RepositoryComponent> _components__Repository = pcmRepository.getComponents__Repository();
      _components__Repository.add(pcmComponent);
    }
    
    public void updatePcmComponentElement(final Component umlComponent, final Repository pcmRepository, final CompositeComponent pcmComponent) {
      pcmComponent.setEntityName(umlComponent.getName());
    }
    
    public EObject getElement2(final Component umlComponent, final Repository pcmRepository, final CompositeComponent pcmComponent) {
      return umlComponent;
    }
    
    public EObject getElement3(final Component umlComponent, final Repository pcmRepository, final CompositeComponent pcmComponent) {
      return pcmComponent;
    }
  }
  
  public CreateCompositeComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Component umlComponent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateCompositeComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlComponent = umlComponent;
  }
  
  private Component umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeComponentRoutine with input:");
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    org.palladiosimulator.pcm.repository.CompositeComponent pcmComponent = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createCompositeComponent();
    notifyObjectCreated(pcmComponent);
    userExecution.updatePcmComponentElement(umlComponent, pcmRepository, pcmComponent);
    
    // val updatedElement userExecution.getElement1(umlComponent, pcmRepository, pcmComponent);
    userExecution.update0Element(umlComponent, pcmRepository, pcmComponent);
    
    addCorrespondenceBetween(userExecution.getElement2(umlComponent, pcmRepository, pcmComponent), userExecution.getElement3(umlComponent, pcmRepository, pcmComponent), "");
    
    postprocessElements();
    
    return true;
  }
}
