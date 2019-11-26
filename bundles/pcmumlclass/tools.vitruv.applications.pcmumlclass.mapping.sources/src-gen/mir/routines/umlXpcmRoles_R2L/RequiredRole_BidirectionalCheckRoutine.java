package mir.routines.umlXpcmRoles_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRoles_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RequiredRole_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private RequiredRole_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof OperationRequiredRole)) {
        OperationRequiredRole role_ = ((OperationRequiredRole)affectedEObject);
        {
          EObject requiringEntity__candidate = role_.eContainer();
          if (((requiringEntity__candidate != null) && (requiringEntity__candidate instanceof InterfaceProvidingRequiringEntity))) {
            InterfaceProvidingRequiringEntity requiringEntity_ = ((InterfaceProvidingRequiringEntity) requiringEntity__candidate);
            {
              OperationInterface operationInterface__candidate = role_.getRequiredInterface__OperationRequiredRole();
              if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
                OperationInterface operationInterface_ = ((OperationInterface) operationInterface__candidate);
                {
                  if ((routineName == "updateRequiredRoleCorrespondingUmlNames")) {
                    _routinesFacade.updateRequiredRoleCorrespondingUmlNames(role_, operationInterface_, requiringEntity_);
                  }
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof OperationInterface)) {
        OperationInterface operationInterface_ = ((OperationInterface)affectedEObject);
        EList<EObject> _eCrossReferences = operationInterface_.eCrossReferences();
        for (final EObject role__candidate : _eCrossReferences) {
          if (((role__candidate != null) && (role__candidate instanceof OperationRequiredRole))) {
            OperationRequiredRole role__1 = ((OperationRequiredRole) role__candidate);
            OperationInterface _requiredInterface__OperationRequiredRole = role__1.getRequiredInterface__OperationRequiredRole();
            boolean _tripleEquals = (operationInterface_ == _requiredInterface__OperationRequiredRole);
            if (_tripleEquals) {
              EObject requiringEntity__candidate = role__1.eContainer();
              if (((requiringEntity__candidate != null) && (requiringEntity__candidate instanceof InterfaceProvidingRequiringEntity))) {
                InterfaceProvidingRequiringEntity requiringEntity_ = ((InterfaceProvidingRequiringEntity) requiringEntity__candidate);
                {
                  if ((routineName == "updateRequiredRoleCorrespondingUmlNames")) {
                    _routinesFacade.updateRequiredRoleCorrespondingUmlNames(role__1, operationInterface_, requiringEntity_);
                  }
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof InterfaceProvidingRequiringEntity)) {
        InterfaceProvidingRequiringEntity requiringEntity__1 = ((InterfaceProvidingRequiringEntity)affectedEObject);
        EList<RequiredRole> _requiredRoles_InterfaceRequiringEntity = requiringEntity__1.getRequiredRoles_InterfaceRequiringEntity();
        for (final RequiredRole role__candidate_1 : _requiredRoles_InterfaceRequiringEntity) {
          if (((role__candidate_1 != null) && (role__candidate_1 instanceof OperationRequiredRole))) {
            OperationRequiredRole role__2 = ((OperationRequiredRole) role__candidate_1);
            {
              OperationInterface operationInterface__candidate = role__2.getRequiredInterface__OperationRequiredRole();
              if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
                OperationInterface operationInterface__1 = ((OperationInterface) operationInterface__candidate);
                {
                  if ((routineName == "updateRequiredRoleCorrespondingUmlNames")) {
                    _routinesFacade.updateRequiredRoleCorrespondingUmlNames(role__2, operationInterface__1, requiringEntity__1);
                  }
                  return;
                }
              }
            }
          }
        }
      }
    }
  }
  
  public RequiredRole_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRoles_R2L.RequiredRole_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RequiredRole_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
