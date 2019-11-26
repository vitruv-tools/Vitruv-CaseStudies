package mir.routines.umlXpcmRepository_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRepository_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateRepoNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateRepoNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateRepoNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package repositoryPkg, final org.eclipse.uml2.uml.Package contractsPkg, final org.eclipse.uml2.uml.Package datatypesPkg) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_L2R.UpdateRepoNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.repositoryPkg = repositoryPkg;this.contractsPkg = contractsPkg;this.datatypesPkg = datatypesPkg;
  }
  
  private org.eclipse.uml2.uml.Package repositoryPkg;
  
  private org.eclipse.uml2.uml.Package contractsPkg;
  
  private org.eclipse.uml2.uml.Package datatypesPkg;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateRepoNameRoutine with input:");
    getLogger().debug("   repositoryPkg: " + this.repositoryPkg);
    getLogger().debug("   contractsPkg: " + this.contractsPkg);
    getLogger().debug("   datatypesPkg: " + this.datatypesPkg);
    
    userExecution.executeAction1(repositoryPkg, contractsPkg, datatypesPkg, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
