package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final PrimitiveType uPrimType, @Extension final RoutinesFacade _routinesFacade) {
      org.emftext.language.java.types.PrimitiveType _mapToJavaPrimitiveType = UmlToJavaHelper.mapToJavaPrimitiveType(uPrimType);
      boolean _tripleEquals = (_mapToJavaPrimitiveType == null);
      if (_tripleEquals) {
        this.userInteracting.showMessage(UserInteractionType.MODAL, ("There is no corresponding Java-PrimitiveType for " + uPrimType));
      }
    }
  }
  
  public CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final PrimitiveType uPrimType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaClassifier.RoutinesFacade(getExecutionState(), this);
    this.uPrimType = uPrimType;
  }
  
  private PrimitiveType uPrimType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CheckIfCorrespondingJavaPrimitiveTypeExistsRoutine with input:");
    getLogger().debug("   PrimitiveType: " + this.uPrimType);
    
    userExecution.callRoutine1(uPrimType, actionsFacade);
    
    postprocessElements();
  }
}
