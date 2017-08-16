package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class FooRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private FooRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Field field, final EObject eObject) {
      return field;
    }
    
    public EObject getElement2(final Field field, final EObject eObject) {
      return eObject;
    }
  }
  
  public FooRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field field, final EObject eObject) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.FooRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.field = field;this.eObject = eObject;
  }
  
  private Field field;
  
  private EObject eObject;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine FooRoutine with input:");
    getLogger().debug("   Field: " + this.field);
    getLogger().debug("   EObject: " + this.eObject);
    
    addCorrespondenceBetween(userExecution.getElement1(field, eObject), userExecution.getElement2(field, eObject), "");
    
    postprocessElements();
  }
}
