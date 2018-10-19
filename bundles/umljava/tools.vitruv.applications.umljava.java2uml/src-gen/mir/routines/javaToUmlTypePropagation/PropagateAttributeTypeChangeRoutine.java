package mir.routines.javaToUmlTypePropagation;

import java.io.IOException;
import mir.routines.javaToUmlTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateAttributeTypeChangeRoutine extends AbstractRepairRoutineRealization {
  private PropagateAttributeTypeChangeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Field jAtt, final Property uAtt, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.propagateTypeChangeToTypedMultiplicityElement(uAtt, uAtt, jAtt);
    }
  }
  
  public PropagateAttributeTypeChangeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAtt, final Property uAtt) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUmlTypePropagation.PropagateAttributeTypeChangeRoutine.ActionUserExecution(getExecutionState(), this);
    this.jAtt = jAtt;this.uAtt = uAtt;
  }
  
  private Field jAtt;
  
  private Property uAtt;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateAttributeTypeChangeRoutine with input:");
    getLogger().debug("   jAtt: " + this.jAtt);
    getLogger().debug("   uAtt: " + this.uAtt);
    
    userExecution.callRoutine1(jAtt, uAtt, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
