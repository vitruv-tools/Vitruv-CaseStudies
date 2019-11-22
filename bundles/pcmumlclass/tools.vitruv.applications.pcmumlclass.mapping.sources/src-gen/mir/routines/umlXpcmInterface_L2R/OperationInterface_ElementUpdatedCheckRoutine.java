package mir.routines.umlXpcmInterface_L2R;

import java.io.IOException;
import mir.routines.umlXpcmInterface_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OperationInterface_ElementUpdatedCheckRoutine extends AbstractRepairRoutineRealization {
  private OperationInterface_ElementUpdatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof Interface)) {
        Interface interface_ = ((Interface)affectedEObject);
        {
          EObject contractsPackage__candidate = interface_.eContainer();
          if (((contractsPackage__candidate != null) && (contractsPackage__candidate instanceof org.eclipse.uml2.uml.Package))) {
            org.eclipse.uml2.uml.Package contractsPackage_ = ((org.eclipse.uml2.uml.Package) contractsPackage__candidate);
            {
              _routinesFacade.operationInterface_CreateMapping(interface_, contractsPackage_);
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Package)) {
        org.eclipse.uml2.uml.Package contractsPackage_ = ((org.eclipse.uml2.uml.Package)affectedEObject);
        EList<PackageableElement> _packagedElements = contractsPackage_.getPackagedElements();
        for (final PackageableElement interface__candidate : _packagedElements) {
          if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
            Interface interface__1 = ((Interface) interface__candidate);
            {
              _routinesFacade.operationInterface_CreateMapping(interface__1, contractsPackage_);
              return;
            }
          }
        }
      }
      _routinesFacade.operationInterface_ElementDeletedCheck(affectedEObject);
    }
  }
  
  public OperationInterface_ElementUpdatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmInterface_L2R.OperationInterface_ElementUpdatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OperationInterface_ElementUpdatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
