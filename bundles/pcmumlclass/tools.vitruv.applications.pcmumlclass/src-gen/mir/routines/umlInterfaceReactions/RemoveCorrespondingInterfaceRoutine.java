package mir.routines.umlInterfaceReactions;

import java.io.IOException;
import mir.routines.umlInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final OperationInterface pcmInterface) {
      return pcmRepository;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
      return umlPackage;
    }
    
    public void update0Element(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository, final OperationInterface pcmInterface) {
      EList<org.palladiosimulator.pcm.repository.Interface> _interfaces__Repository = pcmRepository.getInterfaces__Repository();
      _interfaces__Repository.remove(pcmInterface);
    }
    
    public String getRetrieveTag1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository) {
      return umlInterface;
    }
    
    public String getRetrieveTag2(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final Repository pcmRepository) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public RemoveCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceReactions.RemoveCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.umlPackage = umlPackage;
  }
  
  private Interface umlInterface;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(umlInterface, umlPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface, umlPackage), 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlInterface, umlPackage, pcmRepository), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlInterface, umlPackage, pcmRepository), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    // val updatedElement userExecution.getElement1(umlInterface, umlPackage, pcmRepository, pcmInterface);
    userExecution.update0Element(umlInterface, umlPackage, pcmRepository, pcmInterface);
    
    postprocessElements();
    
    return true;
  }
}
