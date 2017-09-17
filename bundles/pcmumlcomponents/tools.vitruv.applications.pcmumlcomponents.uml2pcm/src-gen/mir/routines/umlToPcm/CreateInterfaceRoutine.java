package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final Repository pcmRepository, final OperationInterface pcmInterface) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final Interface umlInterface) {
      Model _model = umlInterface.getModel();
      return _model;
    }
    
    public void update0Element(final Interface umlInterface, final Repository pcmRepository, final OperationInterface pcmInterface) {
      EList<org.palladiosimulator.pcm.repository.Interface> _interfaces__Repository = pcmRepository.getInterfaces__Repository();
      _interfaces__Repository.add(pcmInterface);
    }
    
    public EObject getElement2(final Interface umlInterface, final Repository pcmRepository, final OperationInterface pcmInterface) {
      return umlInterface;
    }
    
    public EObject getElement3(final Interface umlInterface, final Repository pcmRepository, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public void updatePcmInterfaceElement(final Interface umlInterface, final Repository pcmRepository, final OperationInterface pcmInterface) {
      pcmInterface.setEntityName(umlInterface.getName());
    }
  }
  
  public CreateInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.CreateInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlInterface = umlInterface;
  }
  
  private Interface umlInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlInterface), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(pcmInterface);
    userExecution.updatePcmInterfaceElement(umlInterface, pcmRepository, pcmInterface);
    
    // val updatedElement userExecution.getElement1(umlInterface, pcmRepository, pcmInterface);
    userExecution.update0Element(umlInterface, pcmRepository, pcmInterface);
    
    addCorrespondenceBetween(userExecution.getElement2(umlInterface, pcmRepository, pcmInterface), userExecution.getElement3(umlInterface, pcmRepository, pcmInterface), "");
    
    postprocessElements();
    
    return true;
  }
}
