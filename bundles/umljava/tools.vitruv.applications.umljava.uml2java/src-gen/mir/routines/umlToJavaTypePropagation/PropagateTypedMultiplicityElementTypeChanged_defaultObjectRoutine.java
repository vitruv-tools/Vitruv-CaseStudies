package mir.routines.umlToJavaTypePropagation;

import java.io.IOException;
import mir.routines.umlToJavaTypePropagation.RoutinesFacade;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.types.NamespaceClassifierReference;
import tools.vitruv.domains.java.util.JavaModificationUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine extends AbstractRepairRoutineRealization {
  private PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType, @Extension final RoutinesFacade _routinesFacade) {
      final NamespaceClassifierReference objectNsRef = JavaModificationUtil.createNamespaceClassifierReference(jElement.getObjectClass());
      _routinesFacade.propagateTypedMultiplicityElementTypeChanged(uElement, uMultiplicity, jElement, jType, objectNsRef);
    }
  }
  
  public PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypedElement uElement, final MultiplicityElement uMultiplicity, final org.emftext.language.java.types.TypedElement jElement, final ConcreteClassifier jType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaTypePropagation.PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine.ActionUserExecution(getExecutionState(), this);
    this.uElement = uElement;this.uMultiplicity = uMultiplicity;this.jElement = jElement;this.jType = jType;
  }
  
  private TypedElement uElement;
  
  private MultiplicityElement uMultiplicity;
  
  private org.emftext.language.java.types.TypedElement jElement;
  
  private ConcreteClassifier jType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine PropagateTypedMultiplicityElementTypeChanged_defaultObjectRoutine with input:");
    getLogger().debug("   uElement: " + this.uElement);
    getLogger().debug("   uMultiplicity: " + this.uMultiplicity);
    getLogger().debug("   jElement: " + this.jElement);
    getLogger().debug("   jType: " + this.jType);
    
    userExecution.callRoutine1(uElement, uMultiplicity, jElement, jType, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
