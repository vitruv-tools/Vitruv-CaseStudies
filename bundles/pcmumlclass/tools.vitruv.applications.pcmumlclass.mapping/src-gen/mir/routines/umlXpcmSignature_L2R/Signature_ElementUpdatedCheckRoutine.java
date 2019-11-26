package mir.routines.umlXpcmSignature_L2R;

import java.io.IOException;
import mir.routines.umlXpcmSignature_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Signature_ElementUpdatedCheckRoutine extends AbstractRepairRoutineRealization {
  private Signature_ElementUpdatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof Operation)) {
        Operation operation_ = ((Operation)affectedEObject);
        EList<Parameter> _ownedParameters = operation_.getOwnedParameters();
        for (final Parameter returnParameter__candidate : _ownedParameters) {
          if (((returnParameter__candidate != null) && (returnParameter__candidate instanceof Parameter))) {
            Parameter returnParameter_ = ((Parameter) returnParameter__candidate);
            String _name = returnParameter_.getName();
            boolean _tripleEquals = ("returnParam" == _name);
            if (_tripleEquals) {
              EObject interface__candidate = operation_.eContainer();
              if (((interface__candidate != null) && (interface__candidate instanceof Interface))) {
                Interface interface_ = ((Interface) interface__candidate);
                {
                  _routinesFacade.signature_CreateMapping(operation_, returnParameter_, interface_);
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof Parameter)) {
        Parameter returnParameter__1 = ((Parameter)affectedEObject);
        String _name_1 = returnParameter__1.getName();
        boolean _tripleEquals_1 = ("returnParam" == _name_1);
        if (_tripleEquals_1) {
          EObject operation__candidate = returnParameter__1.eContainer();
          if (((operation__candidate != null) && (operation__candidate instanceof Operation))) {
            Operation operation__1 = ((Operation) operation__candidate);
            {
              EObject interface__candidate_1 = operation__1.eContainer();
              if (((interface__candidate_1 != null) && (interface__candidate_1 instanceof Interface))) {
                Interface interface__1 = ((Interface) interface__candidate_1);
                {
                  _routinesFacade.signature_CreateMapping(operation__1, returnParameter__1, interface__1);
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof Interface)) {
        Interface interface__1 = ((Interface)affectedEObject);
        EList<Operation> _ownedOperations = interface__1.getOwnedOperations();
        for (final Operation operation__candidate_1 : _ownedOperations) {
          if (((operation__candidate_1 != null) && (operation__candidate_1 instanceof Operation))) {
            Operation operation__2 = ((Operation) operation__candidate_1);
            EList<Parameter> _ownedParameters_1 = operation__2.getOwnedParameters();
            for (final Parameter returnParameter__candidate_1 : _ownedParameters_1) {
              if (((returnParameter__candidate_1 != null) && (returnParameter__candidate_1 instanceof Parameter))) {
                Parameter returnParameter__2 = ((Parameter) returnParameter__candidate_1);
                String _name_2 = returnParameter__2.getName();
                boolean _tripleEquals_2 = ("returnParam" == _name_2);
                if (_tripleEquals_2) {
                  _routinesFacade.signature_CreateMapping(operation__2, returnParameter__2, interface__1);
                  return;
                }
              }
            }
          }
        }
      }
      _routinesFacade.signature_ElementDeletedCheck(affectedEObject);
    }
  }
  
  public Signature_ElementUpdatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_L2R.Signature_ElementUpdatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_ElementUpdatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
