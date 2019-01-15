package mir.routines.umlInterfaceReactions;

import java.io.IOException;
import mir.routines.umlInterfaceReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCorrespondingInterfaceRoutine extends AbstractRepairRoutineRealization {
  private CreateCorrespondingInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final OperationInterface pcmInterface) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSource1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
      return umlInterface;
    }
    
    public String getRetrieveTag1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getElement2(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final OperationInterface pcmInterface) {
      return umlInterface;
    }
    
    public String getTag1(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final OperationInterface pcmInterface) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void updatePcmInterfaceElement(final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage, final OperationInterface pcmInterface) {
      pcmInterface.setEntityName(umlInterface.getName());
    }
  }
  
  public CreateCorrespondingInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface umlInterface, final org.eclipse.uml2.uml.Package umlPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInterfaceReactions.CreateCorrespondingInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlInterface = umlInterface;this.umlPackage = umlPackage;
  }
  
  private Interface umlInterface;
  
  private org.eclipse.uml2.uml.Package umlPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCorrespondingInterfaceRoutine with input:");
    getLogger().debug("   umlInterface: " + this.umlInterface);
    getLogger().debug("   umlPackage: " + this.umlPackage);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlInterface, umlPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlInterface, umlPackage)
    ).isEmpty()) {
    	return false;
    }
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(pcmInterface);
    userExecution.updatePcmInterfaceElement(umlInterface, umlPackage, pcmInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(umlInterface, umlPackage, pcmInterface), userExecution.getElement2(umlInterface, umlPackage, pcmInterface), userExecution.getTag1(umlInterface, umlPackage, pcmInterface));
    
    postprocessElements();
    
    return true;
  }
}
