package mir.routines.pcmInterfaceReactions;

import java.io.IOException;
import mir.routines.pcmInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateInterfaceCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private CreateInterfaceCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage, final Interface umlInterface) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSource1(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage) {
      return pcmInterface;
    }
    
    public String getRetrieveTag1(final OperationInterface pcmInterface, final Repository pcmRepository) {
      return TagLiterals.REPOSITORY_TO_CONTRACTS_PACKAGE;
    }
    
    public String getRetrieveTag2(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void updateUmlInterfaceElement(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage, final Interface umlInterface) {
      umlInterface.setName(pcmInterface.getEntityName());
    }
    
    public EObject getElement2(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage, final Interface umlInterface) {
      return umlInterface;
    }
    
    public EObject getCorrepondenceSourceUmlContractsPackage(final OperationInterface pcmInterface, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public String getTag1(final OperationInterface pcmInterface, final Repository pcmRepository, final org.eclipse.uml2.uml.Package umlContractsPackage, final Interface umlInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public CreateInterfaceCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final Repository pcmRepository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmInterfaceReactions.CreateInterfaceCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.pcmRepository = pcmRepository;
  }
  
  private OperationInterface pcmInterface;
  
  private Repository pcmRepository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateInterfaceCorrespondenceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    
    org.eclipse.uml2.uml.Package umlContractsPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlContractsPackage(pcmInterface, pcmRepository), // correspondence source supplier
    	org.eclipse.uml2.uml.Package.class,
    	(org.eclipse.uml2.uml.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmInterface, pcmRepository), 
    	false // asserted
    	);
    if (umlContractsPackage == null) {
    	return false;
    }
    registerObjectUnderModification(umlContractsPackage);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmInterface, pcmRepository, umlContractsPackage), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmInterface, pcmRepository, umlContractsPackage)
    ).isEmpty()) {
    	return false;
    }
    org.eclipse.uml2.uml.Interface umlInterface = org.eclipse.uml2.uml.internal.impl.UMLFactoryImpl.eINSTANCE.createInterface();
    notifyObjectCreated(umlInterface);
    userExecution.updateUmlInterfaceElement(pcmInterface, pcmRepository, umlContractsPackage, umlInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmInterface, pcmRepository, umlContractsPackage, umlInterface), userExecution.getElement2(pcmInterface, pcmRepository, umlContractsPackage, umlInterface), userExecution.getTag1(pcmInterface, pcmRepository, umlContractsPackage, umlInterface));
    
    postprocessElements();
    
    return true;
  }
}
