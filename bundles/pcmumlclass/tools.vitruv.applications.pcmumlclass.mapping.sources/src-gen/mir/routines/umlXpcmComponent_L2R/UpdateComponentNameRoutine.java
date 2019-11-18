package mir.routines.umlXpcmComponent_L2R;

import java.io.IOException;
import mir.routines.umlXpcmComponent_L2R.RoutinesFacade;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateComponentNameRoutine extends AbstractRepairRoutineRealization {
  private UpdateComponentNameRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
  
  public UpdateComponentNameRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Package componentPackage, final org.eclipse.uml2.uml.Package repositoryPackage, final org.eclipse.uml2.uml.Class implementation, final Operation constructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_L2R.UpdateComponentNameRoutine.ActionUserExecution(getExecutionState(), this);
    this.componentPackage = componentPackage;this.repositoryPackage = repositoryPackage;this.implementation = implementation;this.constructor = constructor;
  }
  
  private org.eclipse.uml2.uml.Package componentPackage;
  
  private org.eclipse.uml2.uml.Package repositoryPackage;
  
  private org.eclipse.uml2.uml.Class implementation;
  
  private Operation constructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateComponentNameRoutine with input:");
    getLogger().debug("   componentPackage: " + this.componentPackage);
    getLogger().debug("   repositoryPackage: " + this.repositoryPackage);
    getLogger().debug("   implementation: " + this.implementation);
    getLogger().debug("   constructor: " + this.constructor);
    
    userExecution.executeAction1(componentPackage, repositoryPackage, implementation, constructor, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
