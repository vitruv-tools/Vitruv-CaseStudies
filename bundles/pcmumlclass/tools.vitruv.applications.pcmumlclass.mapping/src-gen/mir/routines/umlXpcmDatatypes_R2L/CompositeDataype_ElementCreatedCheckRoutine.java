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
public class CompositeDataype_ElementCreatedCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDataype_ElementCreatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof CompositeDataType)) {
        CompositeDataType type_ = ((CompositeDataType)affectedEObject);
        {
          EObject repository__candidate = type_.eContainer();
          if (((repository__candidate != null) && (repository__candidate instanceof Repository))) {
            Repository repository_ = ((Repository) repository__candidate);
            {
              _routinesFacade.compositeDataype_CreateMapping(type_, repository_);
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
              _routinesFacade.compositeDataype_CreateMapping(type__1, repository_);
              return;
            }
          }
        }
      }
    }
  }
  
  public CompositeDataype_ElementCreatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDataype_ElementCreatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDataype_ElementCreatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
