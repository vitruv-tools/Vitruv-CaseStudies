package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Classifier uClassifier, final Operation uOperation, @Extension final RoutinesFacade _routinesFacade) {
      String _name = uClassifier.getName();
      String _name_1 = uOperation.getName();
      boolean _equals = _name.equals(_name_1);
      if (_equals) {
        _routinesFacade.createJavaConstructor(uClassifier, uOperation);
      } else {
        if (((uClassifier instanceof org.eclipse.uml2.uml.Class) || (uClassifier instanceof DataType))) {
          _routinesFacade.createJavaClassMethod(uClassifier, uOperation);
        } else {
          if ((uClassifier instanceof Interface)) {
            _routinesFacade.createJavaInterfaceMethod(((Interface)uClassifier), uOperation);
          } else {
            Logger _logger = this.getLogger();
            _logger.warn((("Invalid creation of " + uOperation) + ", containing UML-Classifier is neither a Class, nor an Interface nor a DataType"));
          }
        }
      }
    }
  }
  
  public CreateJavaMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier uClassifier, final Operation uOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uClassifier = uClassifier;this.uOperation = uOperation;
  }
  
  private Classifier uClassifier;
  
  private Operation uOperation;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaMethodRoutine with input:");
    getLogger().debug("   Classifier: " + this.uClassifier);
    getLogger().debug("   Operation: " + this.uOperation);
    
    userExecution.callRoutine1(uClassifier, uOperation, actionsFacade);
    
    postprocessElements();
  }
}
