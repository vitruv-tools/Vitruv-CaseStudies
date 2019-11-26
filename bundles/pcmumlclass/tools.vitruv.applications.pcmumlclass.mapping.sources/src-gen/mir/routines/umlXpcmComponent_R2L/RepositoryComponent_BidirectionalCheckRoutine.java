package mir.routines.umlXpcmComponent_R2L;

import java.io.IOException;
import mir.routines.umlXpcmComponent_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RepositoryComponent_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private RepositoryComponent_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof RepositoryComponent)) {
        RepositoryComponent component_ = ((RepositoryComponent)affectedEObject);
        {
          EObject repository__candidate = component_.eContainer();
          if (((repository__candidate != null) && (repository__candidate instanceof Repository))) {
            Repository repository_ = ((Repository) repository__candidate);
            {
              if ((routineName == "updateComponentCorrespondingUmlNames")) {
                _routinesFacade.updateComponentCorrespondingUmlNames(component_, repository_);
              }
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof Repository)) {
        Repository repository_ = ((Repository)affectedEObject);
        EList<RepositoryComponent> _components__Repository = repository_.getComponents__Repository();
        for (final RepositoryComponent component__candidate : _components__Repository) {
          if (((component__candidate != null) && (component__candidate instanceof RepositoryComponent))) {
            RepositoryComponent component__1 = ((RepositoryComponent) component__candidate);
            {
              if ((routineName == "updateComponentCorrespondingUmlNames")) {
                _routinesFacade.updateComponentCorrespondingUmlNames(component__1, repository_);
              }
              return;
            }
          }
        }
      }
    }
  }
  
  public RepositoryComponent_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmComponent_R2L.RepositoryComponent_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RepositoryComponent_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
