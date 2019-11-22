package mir.routines.umlXpcmRoutines;

import java.io.IOException;
import mir.routines.umlXpcmRoutines.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateInterfaceNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateInterfaceNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationInterface operationInterface, final Repository repository, final Interface umlInterface, @Extension final RoutinesFacade _routinesFacade) {
      umlInterface.setName(operationInterface.getEntityName());
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationInterface operationInterface, final Repository repository) {
      return operationInterface;
    }
    
    public String getRetrieveTag1(final OperationInterface operationInterface, final Repository repository) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public UpdateInterfaceNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface operationInterface, final Repository repository) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoutines.UpdateInterfaceNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.operationInterface = operationInterface;this.repository = repository;
  }
  
  private OperationInterface operationInterface;
  
  private Repository repository;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateInterfaceNameRoutine with input:");
    getLogger().debug("   operationInterface: " + this.operationInterface);
    getLogger().debug("   repository: " + this.repository);
    
    org.eclipse.uml2.uml.Interface umlInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlInterface(operationInterface, repository), // correspondence source supplier
    	org.eclipse.uml2.uml.Interface.class,
    	(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(operationInterface, repository), 
    	false // asserted
    	);
    if (umlInterface == null) {
    	return false;
    }
    registerObjectUnderModification(umlInterface);
    userExecution.executeAction1(operationInterface, repository, umlInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
