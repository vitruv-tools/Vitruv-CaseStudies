package mir.routines.umlXpcmRepository_R2L;

import java.io.IOException;
import mir.routines.umlXpcmRepository_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Repository_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private Repository_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof Repository)) {
        Repository repository_ = ((Repository)affectedEObject);
        if ((routineName == "updateRepoPackageName")) {
          _routinesFacade.updateRepoPackageName(repository_);
        }
      }
    }
  }
  
  public Repository_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmRepository_R2L.Repository_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Repository_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
