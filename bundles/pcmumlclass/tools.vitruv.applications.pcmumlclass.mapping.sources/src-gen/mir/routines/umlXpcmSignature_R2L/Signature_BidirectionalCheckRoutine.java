package mir.routines.umlXpcmSignature_R2L;

import java.io.IOException;
import mir.routines.umlXpcmSignature_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
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
      if ((affectedEObject instanceof OperationSignature)) {
        OperationSignature operationSignature_ = ((OperationSignature)affectedEObject);
        {
          DataType returnType__candidate = operationSignature_.getReturnType__OperationSignature();
          if (((returnType__candidate != null) && (returnType__candidate instanceof DataType))) {
            DataType returnType_ = ((DataType) returnType__candidate);
            {
              EObject operationInterface__candidate = operationSignature_.eContainer();
              if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
                OperationInterface operationInterface_ = ((OperationInterface) operationInterface__candidate);
                {
                  {
                    if ((routineName == "updateSignatureUmlName")) {
                      _routinesFacade.updateSignatureUmlName(operationSignature_, returnType_, operationInterface_);
                    }
                    if ((routineName == "updateSignatureUmlReturnType")) {
                      _routinesFacade.updateSignatureUmlReturnType(operationSignature_, returnType_, operationInterface_);
                    }
                  }
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof DataType)) {
        DataType returnType_ = ((DataType)affectedEObject);
        EList<EObject> _eCrossReferences = returnType_.eCrossReferences();
        for (final EObject operationSignature__candidate : _eCrossReferences) {
          if (((operationSignature__candidate != null) && (operationSignature__candidate instanceof OperationSignature))) {
            OperationSignature operationSignature__1 = ((OperationSignature) operationSignature__candidate);
            DataType _returnType__OperationSignature = operationSignature__1.getReturnType__OperationSignature();
            boolean _tripleEquals = (returnType_ == _returnType__OperationSignature);
            if (_tripleEquals) {
              EObject operationInterface__candidate = operationSignature__1.eContainer();
              if (((operationInterface__candidate != null) && (operationInterface__candidate instanceof OperationInterface))) {
                OperationInterface operationInterface_ = ((OperationInterface) operationInterface__candidate);
                {
                  {
                    if ((routineName == "updateSignatureUmlName")) {
                      _routinesFacade.updateSignatureUmlName(operationSignature__1, returnType_, operationInterface_);
                    }
                    if ((routineName == "updateSignatureUmlReturnType")) {
                      _routinesFacade.updateSignatureUmlReturnType(operationSignature__1, returnType_, operationInterface_);
                    }
                  }
                  return;
                }
              }
            }
          }
        }
      }
      if ((affectedEObject instanceof OperationInterface)) {
        OperationInterface operationInterface__1 = ((OperationInterface)affectedEObject);
        EList<OperationSignature> _signatures__OperationInterface = operationInterface__1.getSignatures__OperationInterface();
        for (final OperationSignature operationSignature__candidate_1 : _signatures__OperationInterface) {
          if (((operationSignature__candidate_1 != null) && (operationSignature__candidate_1 instanceof OperationSignature))) {
            OperationSignature operationSignature__2 = ((OperationSignature) operationSignature__candidate_1);
            {
              DataType returnType__candidate = operationSignature__2.getReturnType__OperationSignature();
              if (((returnType__candidate != null) && (returnType__candidate instanceof DataType))) {
                DataType returnType__1 = ((DataType) returnType__candidate);
                {
                  {
                    if ((routineName == "updateSignatureUmlName")) {
                      _routinesFacade.updateSignatureUmlName(operationSignature__2, returnType__1, operationInterface__1);
                    }
                    if ((routineName == "updateSignatureUmlReturnType")) {
                      _routinesFacade.updateSignatureUmlReturnType(operationSignature__2, returnType__1, operationInterface__1);
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
    this.userExecution = new mir.routines.umlXpcmSignature_R2L.Signature_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
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
