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
public class Signature_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private Signature_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
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
                  {
                    if ((routineName == "updateSignaturePcmName")) {
                      _routinesFacade.updateSignaturePcmName(operation_, returnParameter_, interface_);
                    }
                    if ((routineName == "updateSignaturePcmReturnType")) {
                      _routinesFacade.updateSignaturePcmReturnType(operation_, returnParameter_, interface_);
                    }
                  }
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
                  {
                    if ((routineName == "updateSignaturePcmName")) {
                      _routinesFacade.updateSignaturePcmName(operation__1, returnParameter__1, interface__1);
                    }
                    if ((routineName == "updateSignaturePcmReturnType")) {
                      _routinesFacade.updateSignaturePcmReturnType(operation__1, returnParameter__1, interface__1);
                    }
                  }
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
                  {
                    if ((routineName == "updateSignaturePcmName")) {
                      _routinesFacade.updateSignaturePcmName(operation__2, returnParameter__2, interface__1);
                    }
                    if ((routineName == "updateSignaturePcmReturnType")) {
                      _routinesFacade.updateSignaturePcmReturnType(operation__2, returnParameter__2, interface__1);
                    }
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
  
  public Signature_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmSignature_L2R.Signature_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Signature_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
