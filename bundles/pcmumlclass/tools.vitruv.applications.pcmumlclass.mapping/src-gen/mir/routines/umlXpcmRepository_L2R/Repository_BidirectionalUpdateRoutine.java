package mir.routines.umlXpcmRepository_L2R;

import java.io.IOException;
import mir.routines.umlXpcmRepository_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private Repository_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateRepoName(repositoryPkg_, contractsPkg_, datatypesPkg_);
    }
  }
  
  public Repository_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package repositoryPkg_, final org.eclipse.uml2.uml.Package contractsPkg_, final org.eclipse.uml2.uml.Package datatypesPkg_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_L2R.Repository_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.repositoryPkg_ = repositoryPkg_;this.contractsPkg_ = contractsPkg_;this.datatypesPkg_ = datatypesPkg_;
  }
  
  private org.eclipse.uml2.uml.Package repositoryPkg_;
  
  private org.eclipse.uml2.uml.Package contractsPkg_;
  
  private org.eclipse.uml2.uml.Package datatypesPkg_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   repositoryPkg_: " + this.repositoryPkg_);
    getLogger().debug("   contractsPkg_: " + this.contractsPkg_);
    getLogger().debug("   datatypesPkg_: " + this.datatypesPkg_);
    
    userExecution.callRoutine1(repositoryPkg_, contractsPkg_, datatypesPkg_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
