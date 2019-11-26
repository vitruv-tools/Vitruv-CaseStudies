package mir.routines.umlXpcmComponent_L2R;

import java.io.IOException;
import mir.routines.umlXpcmComponent_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.updateComponentName(componentPackage_, repositoryPackage_, implementation_, constructor_);
    }
  }
  
  public RepositoryComponent_BidirectionalUpdateRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package componentPackage_, final org.eclipse.uml2.uml.Package repositoryPackage_, final org.eclipse.uml2.uml.Class implementation_, final Operation constructor_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_L2R.RepositoryComponent_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.componentPackage_ = componentPackage_;this.repositoryPackage_ = repositoryPackage_;this.implementation_ = implementation_;this.constructor_ = constructor_;
  }
  
  private org.eclipse.uml2.uml.Package componentPackage_;
  
  private org.eclipse.uml2.uml.Package repositoryPackage_;
  
  private org.eclipse.uml2.uml.Class implementation_;
  
  private Operation constructor_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   componentPackage_: " + this.componentPackage_);
    getLogger().debug("   repositoryPackage_: " + this.repositoryPackage_);
    getLogger().debug("   implementation_: " + this.implementation_);
    getLogger().debug("   constructor_: " + this.constructor_);
    
    userExecution.callRoutine1(componentPackage_, repositoryPackage_, implementation_, constructor_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
