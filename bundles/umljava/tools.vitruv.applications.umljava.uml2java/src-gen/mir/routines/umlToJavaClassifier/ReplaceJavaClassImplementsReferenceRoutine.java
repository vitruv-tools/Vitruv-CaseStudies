package mir.routines.umlToJavaClassifier;

import java.io.IOException;
import mir.routines.umlToJavaClassifier.RoutinesFacade;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceJavaClassImplementsReferenceRoutine extends AbstractRepairRoutineRealization {
  private ReplaceJavaClassImplementsReferenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createJavaClassImplementsReference(uRealization, uClass);
    }
    
    public void callRoutine1(final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteJavaClassImplementsReference(uRealization, uClass);
    }
  }
  
  public ReplaceJavaClassImplementsReferenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization uRealization, final org.eclipse.uml2.uml.Class uClass) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaClassifier.ReplaceJavaClassImplementsReferenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.uRealization = uRealization;this.uClass = uClass;
  }
  
  private InterfaceRealization uRealization;
  
  private org.eclipse.uml2.uml.Class uClass;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceJavaClassImplementsReferenceRoutine with input:");
    getLogger().debug("   uRealization: " + this.uRealization);
    getLogger().debug("   uClass: " + this.uClass);
    
    userExecution.callRoutine1(uRealization, uClass, this.getRoutinesFacade());
    
    userExecution.callRoutine2(uRealization, uClass, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
