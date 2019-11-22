package mir.routines.umlXpcmDatatypes_L2R;

import java.io.IOException;
import mir.routines.umlXpcmDatatypes_L2R.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.xtext.xbase.lib.Extension;
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
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Class)) {
        org.eclipse.uml2.uml.Class class_ = ((org.eclipse.uml2.uml.Class)affectedEObject);
        EList<Generalization> _generalizations = class_.getGeneralizations();
        for (final Generalization generalization__candidate : _generalizations) {
          if (((generalization__candidate != null) && (generalization__candidate instanceof Generalization))) {
            Generalization generalization_ = ((Generalization) generalization__candidate);
            {
              _routinesFacade.compositeDatatypeParent_CreateMapping(class_, generalization_);
              return;
            }
          }
        }
      }
      if ((affectedEObject instanceof Generalization)) {
        Generalization generalization__1 = ((Generalization)affectedEObject);
        {
          EObject class__candidate = generalization__1.eContainer();
          if (((class__candidate != null) && (class__candidate instanceof org.eclipse.uml2.uml.Class))) {
            org.eclipse.uml2.uml.Class class__1 = ((org.eclipse.uml2.uml.Class) class__candidate);
            {
              _routinesFacade.compositeDatatypeParent_CreateMapping(class__1, generalization__1);
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
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_ElementUpdatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
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
