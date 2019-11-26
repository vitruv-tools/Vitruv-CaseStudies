package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterfaceParent_ElementCreatedCheckRoutine extends AbstractRepairRoutineRealization {
  private OperationInterfaceParent_ElementCreatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof Interface)) {
        Interface interface_ = ((Interface)affectedEObject);
        EList<Generalization> _generalizations = interface_.getGeneralizations();
        for (final Generalization generalization__candidate : _generalizations) {
          if (((generalization__candidate != null) && (generalization__candidate instanceof Generalization))) {
            Generalization generalization_ = ((Generalization) generalization__candidate);
            {
              _routinesFacade.operationInterfaceParent_CreateMapping(interface_, generalization_);
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof Generalization)) {
        Generalization generalization__1 = ((Generalization)affectedEObject);
        {
          EObject interface__candidate = generalization__1.eContainer();
          if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
            Interface interface__1 = ((Interface) interface__candidate);
            {
              _routinesFacade.operationInterfaceParent_CreateMapping(interface__1, generalization__1);
              return;
            }
          }
        }
      }
    }
  }
  
  public OperationInterfaceParent_ElementCreatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterfaceParent_ElementCreatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterfaceParent_ElementCreatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
