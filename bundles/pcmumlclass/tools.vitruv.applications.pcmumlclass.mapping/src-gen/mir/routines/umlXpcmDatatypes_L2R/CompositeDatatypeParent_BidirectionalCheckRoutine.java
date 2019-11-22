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
public class CompositeDatatypeParent_BidirectionalCheckRoutine extends AbstractRepairRoutineRealization {
  private CompositeDatatypeParent_BidirectionalCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, final String routineName, @Extension final RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof org.eclipse.uml2.uml.Class)) {
        org.eclipse.uml2.uml.Class class_ = ((org.eclipse.uml2.uml.Class)affectedEObject);
        EList<Generalization> _generalizations = class_.getGeneralizations();
        for (final Generalization generalization__candidate : _generalizations) {
          if (((generalization__candidate != null) && (generalization__candidate instanceof Generalization))) {
            Generalization generalization_ = ((Generalization) generalization__candidate);
            {
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
              return;
            }
          }
        }
      }
    }
  }
  
  public CompositeDatatypeParent_BidirectionalCheckRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject, final String routineName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlXpcmDatatypes_L2R.CompositeDatatypeParent_BidirectionalCheckRoutine.ActionUserExecution(getExecutionState(), this);
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
