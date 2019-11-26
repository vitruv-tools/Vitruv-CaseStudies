package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDataype_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof CompositeDataType)) {
        CompositeDataType type_ = ((CompositeDataType)affectedEObject);
        {
          EObject repository__candidate = type_.eContainer();
          if (((repository__candidate != null) && (repository__candidate instanceof Repository))) {
            Repository repository_ = ((Repository) repository__candidate);
            {
              if ((routineName == "updateClassName")) {
                _routinesFacade.updateClassName(type_, repository_);
              }
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof Repository)) {
        Repository repository_ = ((Repository)affectedEObject);
        EList<DataType> _dataTypes__Repository = repository_.getDataTypes__Repository();
        for (final DataType type__candidate : _dataTypes__Repository) {
          if (((type__candidate != null) && (type__candidate instanceof CompositeDataType))) {
            CompositeDataType type__1 = ((CompositeDataType) type__candidate);
            {
              if ((routineName == "updateClassName")) {
                _routinesFacade.updateClassName(type__1, repository_);
              }
              return;
            }
          }
        }
      }
    }
  }
  
  public CompositeDataype_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
