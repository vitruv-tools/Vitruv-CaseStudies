package mir.routines.comp2class;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collections;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeCorrespondingVisibilityRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeCorrespondingVisibilityRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final NamedElement compElement, @Extension final RoutinesFacade _routinesFacade) {
      final Iterable<EObject> correspondingElements = Iterables.<EObject>concat(this.correspondenceModel.getCorrespondingEObjects(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(compElement))));
      for (final EObject classElement : correspondingElements) {
        ((NamedElement) classElement).setVisibility(compElement.getVisibility());
      }
    }
  }
  
  public ChangeCorrespondingVisibilityRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement compElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.ChangeCorrespondingVisibilityRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.compElement = compElement;
  }
  
  private NamedElement compElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeCorrespondingVisibilityRoutine with input:");
    getLogger().debug("   compElement: " + this.compElement);
    
    userExecution.callRoutine1(compElement, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
