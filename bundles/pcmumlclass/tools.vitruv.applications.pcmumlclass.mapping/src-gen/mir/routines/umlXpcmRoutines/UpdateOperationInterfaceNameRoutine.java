package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateOperationInterfaceNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateOperationInterfaceNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage, final OperationInterface pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      pcmInterface.setEntityName(interface_.getName());
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage) {
      return interface_;
    }
    
    public String getRetrieveTag1(final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public UpdateOperationInterfaceNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface interface_, final org.eclipse.uml2.uml.Package contractsPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateOperationInterfaceNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.interface_ = interface_;this.contractsPackage = contractsPackage;
  }
  
  private Interface interface_;
  
  private org.eclipse.uml2.uml.Package contractsPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateOperationInterfaceNameRoutine with input:");
    getLogger().debug("   interface_: " + this.interface_);
    getLogger().debug("   contractsPackage: " + this.contractsPackage);
    
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(interface_, contractsPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(interface_, contractsPackage), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    userExecution.executeAction1(interface_, contractsPackage, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
