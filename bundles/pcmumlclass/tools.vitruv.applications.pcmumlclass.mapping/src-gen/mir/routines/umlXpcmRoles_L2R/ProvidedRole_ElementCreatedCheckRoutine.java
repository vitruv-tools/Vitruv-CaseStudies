package mir.routines.umlXpcmRoles_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRoles_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ProvidedRole_ElementCreatedCheckRoutine extends AbstractRepairRoutineRealization {
  private ProvidedRole_ElementCreatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof InterfaceRealization)) {
        InterfaceRealization interfaceRealization_ = ((InterfaceRealization)affectedEObject);
        {
          EObject implementation__candidate = interfaceRealization_.eContainer();
          if (((implementation__candidate != null) && (implementation__candidate instanceof org.eclipse.uml2.uml.Class))) {
            org.eclipse.uml2.uml.Class implementation_ = ((org.eclipse.uml2.uml.Class) implementation__candidate);
            {
              Interface interface__candidate = interfaceRealization_.getContract();
              if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
                Interface interface_ = ((Interface) interface__candidate);
                {
                  _routinesFacade.providedRole_CreateMapping(interfaceRealization_, implementation_, interface_);
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Class)) {
        org.eclipse.uml2.uml.Class implementation_ = ((org.eclipse.uml2.uml.Class)affectedEObject);
        EList<InterfaceRealization> _interfaceRealizations = implementation_.getInterfaceRealizations();
        for (final InterfaceRealization interfaceRealization__candidate : _interfaceRealizations) {
          if (((interfaceRealization__candidate != null) && (interfaceRealization__candidate instanceof InterfaceRealization))) {
            InterfaceRealization interfaceRealization__1 = ((InterfaceRealization) interfaceRealization__candidate);
            {
              Interface interface__candidate = interfaceRealization__1.getContract();
              if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
                Interface interface_ = ((Interface) interface__candidate);
                {
                  _routinesFacade.providedRole_CreateMapping(interfaceRealization__1, implementation_, interface_);
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof Interface)) {
        Interface interface_ = ((Interface)affectedEObject);
        EList<EObject> _eCrossReferences = interface_.eCrossReferences();
        for (final EObject interfaceRealization__candidate_1 : _eCrossReferences) {
          if (((interfaceRealization__candidate_1 != null) && (interfaceRealization__candidate_1 instanceof InterfaceRealization))) {
            InterfaceRealization interfaceRealization__2 = ((InterfaceRealization) interfaceRealization__candidate_1);
            Interface _contract = interfaceRealization__2.getContract();
            boolean _tripleEquals = (interface_ == _contract);
            if (_tripleEquals) {
              EObject implementation__candidate = interfaceRealization__2.eContainer();
              if (((implementation__candidate != null) && (implementation__candidate instanceof org.eclipse.uml2.uml.Class))) {
                org.eclipse.uml2.uml.Class implementation__1 = ((org.eclipse.uml2.uml.Class) implementation__candidate);
                {
                  _routinesFacade.providedRole_CreateMapping(interfaceRealization__2, implementation__1, interface_);
                  return;
                }
              }
            }
          }
        }
      }
    }
  }
  
  public ProvidedRole_ElementCreatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_L2R.ProvidedRole_ElementCreatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ProvidedRole_ElementCreatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
