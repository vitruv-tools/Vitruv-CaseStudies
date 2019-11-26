package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterface_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private OperationInterface_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof OperationInterface)) {
        OperationInterface operationInterface_ = ((OperationInterface)affectedEObject);
        {
          EObject repository__candidate = operationInterface_.eContainer();
          if (((repository__candidate != null) && (repository__candidate instanceof Repository))) {
            Repository repository_ = ((Repository) repository__candidate);
            {
              if ((routineName == "updateInterfaceName")) {
                _routinesFacade.updateInterfaceName(operationInterface_, repository_);
              }
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof Repository)) {
        Repository repository_ = ((Repository)affectedEObject);
        EList<Interface> _interfaces__Repository = repository_.getInterfaces__Repository();
        for (final Interface operationInterface__candidate : _interfaces__Repository) {
          if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
            OperationInterface operationInterface__1 = ((OperationInterface) operationInterface__candidate);
            {
              if ((routineName == "updateInterfaceName")) {
                _routinesFacade.updateInterfaceName(operationInterface__1, repository_);
              }
              return;
            }
          }
        }
      }
    }
  }
  
  public OperationInterface_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterface_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
