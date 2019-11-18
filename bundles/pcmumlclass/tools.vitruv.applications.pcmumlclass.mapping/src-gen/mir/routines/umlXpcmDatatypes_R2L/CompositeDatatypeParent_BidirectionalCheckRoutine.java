package mir.routines.umlXpcmDatatypes_R2L;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_R2L.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CompositeDatatypeParent_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDatatypeParent_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof CompositeDataType)) {
        CompositeDataType type_ = ((CompositeDataType)affectedEObject);
        EList<CompositeDataType> _parentType_CompositeDataType = type_.getParentType_CompositeDataType();
        for (final CompositeDataType parentType__candidate : _parentType_CompositeDataType) {
          if (((parentType__candidate != null) && (parentType__candidate instanceof CompositeDataType))) {
            CompositeDataType parentType_ = ((CompositeDataType) parentType__candidate);
            {
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof CompositeDataType)) {
        CompositeDataType parentType__1 = ((CompositeDataType)affectedEObject);
        EList<EObject> _eCrossReferences = parentType__1.eCrossReferences();
        for (final EObject type__candidate : _eCrossReferences) {
          if (((type__candidate != null) && (type__candidate instanceof CompositeDataType))) {
            CompositeDataType type__1 = ((CompositeDataType) type__candidate);
            boolean _contains = type__1.getParentType_CompositeDataType().contains(parentType__1);
            if (_contains) {
              return;
            }
          }
        }
      }
    }
  }
  
  public CompositeDatatypeParent_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;this.routineName = routineName;
  }
  
  private EObject affectedEObject;
  
  private String routineName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_BidirectionalCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    getLogger().debug("   routineName: " + this.routineName);
    
    userExecution.callRoutine1(affectedEObject, routineName, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
