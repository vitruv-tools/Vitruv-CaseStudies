package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.uml2.uml.NamedElement;
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
    
    public void callRoutine1(final NamedElement umlElement, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.renameNamedElement(umlElement);
      _routinesFacade.renameCollectionType(umlElement);
    }
  }
  
  public RenameElementRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement umlElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.RenameElementRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlElement = umlElement;
  }
  
  private NamedElement umlElement;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameElementRoutine with input:");
    getLogger().debug("   umlElement: " + this.umlElement);
    
    userExecution.callRoutine1(umlElement, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
