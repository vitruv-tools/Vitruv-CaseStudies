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
public class CompositeDatatypeParent_ElementUpdatedCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDatatypeParent_ElementUpdatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof CompositeDataType)) {
        CompositeDataType type_ = ((CompositeDataType)affectedEObject);
        EList<CompositeDataType> _parentType_CompositeDataType = type_.getParentType_CompositeDataType();
        for (final CompositeDataType parentType__candidate : _parentType_CompositeDataType) {
          if (((parentType__candidate != null) && (parentType__candidate instanceof CompositeDataType))) {
            CompositeDataType parentType_ = ((CompositeDataType) parentType__candidate);
            {
              _routinesFacade.compositeDatatypeParent_CreateMapping(type_, parentType_);
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
              _routinesFacade.compositeDatatypeParent_CreateMapping(type__1, parentType__1);
              return;
            }
          }
        }
      }
      _routinesFacade.compositeDatatypeParent_ElementDeletedCheck(affectedEObject);
    }
  }
  
  public CompositeDatatypeParent_ElementUpdatedCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_R2L.CompositeDatatypeParent_ElementUpdatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CompositeDatatypeParent_ElementUpdatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
