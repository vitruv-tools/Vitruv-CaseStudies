package mir.routines.umlToJavaTypePropagation;

import java.io.IOException;
import mir.routines.umlToJavaTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagatePropertyTypeChangedRoutine extends AbstractRepairRoutineRealization {
  private PropagatePropertyTypeChangedRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Property uProperty, final Field jElement, final ConcreteClassifier jType, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.propagateTypedMultiplicityElementTypeChanged_defaultObject(uProperty, uProperty, jElement, jType);
    }
  }
  
  public PropagatePropertyTypeChangedRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property uProperty, final Field jElement, final ConcreteClassifier jType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaTypePropagation.PropagatePropertyTypeChangedRoutine.ActionUserExecution(getExecutionState(), this);
    this.uProperty = uProperty;this.jElement = jElement;this.jType = jType;
  }
  
  private Property uProperty;
  
  private Field jElement;
  
  private ConcreteClassifier jType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagatePropertyTypeChangedRoutine with input:");
    getLogger().debug("   uProperty: " + this.uProperty);
    getLogger().debug("   jElement: " + this.jElement);
    getLogger().debug("   jType: " + this.jType);
    
    userExecution.callRoutine1(uProperty, jElement, jType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
