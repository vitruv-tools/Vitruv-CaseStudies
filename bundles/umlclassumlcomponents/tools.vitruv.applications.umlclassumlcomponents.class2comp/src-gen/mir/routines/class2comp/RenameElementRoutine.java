package mir.routines.class2comp;

import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Collections;
import mir.routines.class2comp.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameElementRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameElementRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final NamedElement classElement, @Extension final RoutinesFacade _routinesFacade) {
      final Iterable<EObject> correspondingElements = Iterables.<EObject>concat(this.correspondenceModel.getCorrespondingEObjects(Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(classElement))));
      for (final EObject compElement : correspondingElements) {
        ((NamedElement) compElement).setName(classElement.getName());
      }
    }
  }
  
  public RenameElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement classElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.class2comp.RenameElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.class2comp.RoutinesFacade(getExecutionState(), this);
    this.classElement = classElement;
  }
  
  private NamedElement classElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameElementRoutine with input:");
    getLogger().debug("   classElement: " + this.classElement);
    
    userExecution.callRoutine1(classElement, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
