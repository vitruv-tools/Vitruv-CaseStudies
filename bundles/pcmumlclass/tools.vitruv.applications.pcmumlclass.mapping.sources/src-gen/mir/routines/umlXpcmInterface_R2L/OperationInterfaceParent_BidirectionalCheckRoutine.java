package mir.routines.umlXpcmInterface_R2L;

import java.io.IOException;
import mir.routines.umlXpcmInterface_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterfaceParent_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private OperationInterfaceParent_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof OperationInterface)) {
        OperationInterface operationInterface_ = ((OperationInterface)affectedEObject);
        EList<Interface> _parentInterfaces__Interface = operationInterface_.getParentInterfaces__Interface();
        for (final Interface parentInterface__candidate : _parentInterfaces__Interface) {
          if (((parentInterface__candidate != null) && (parentInterface__candidate instanceof OperationInterface))) {
            OperationInterface parentInterface_ = ((OperationInterface) parentInterface__candidate);
            {
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof OperationInterface)) {
        OperationInterface parentInterface__1 = ((OperationInterface)affectedEObject);
        EList<EObject> _eCrossReferences = parentInterface__1.eCrossReferences();
        for (final EObject operationInterface__candidate : _eCrossReferences) {
          if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
            OperationInterface operationInterface__1 = ((OperationInterface) operationInterface__candidate);
            boolean _contains = operationInterface__1.getParentInterfaces__Interface().contains(parentInterface__1);
            if (_contains) {
              return;
            }
          }
        }
      }
    }
  }
  
  public OperationInterfaceParent_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_R2L.OperationInterfaceParent_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
