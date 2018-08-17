package mir.routines.umlCompositeDataTypeGeneralizationReactions;

import mir.routines.umlCompositeDataTypeGeneralizationReactions.AddCompositeDatatypeParentRoutine;
import mir.routines.umlCompositeDataTypeGeneralizationReactions.RemoveCompositeDatatypeParentRoutine;
import mir.routines.umlCompositeDataTypeGeneralizationReactions.ReplaceCollectionDatatypeParentRoutine;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean addCompositeDatatypeParent(final org.eclipse.uml2.uml.Class umlClass, final Classifier umlNewParent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCompositeDatatypeParentRoutine routine = new AddCompositeDatatypeParentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlNewParent);
    return routine.applyRoutine();
  }
  
  public boolean removeCompositeDatatypeParent(final org.eclipse.uml2.uml.Class umlClass, final Classifier umlOldParent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCompositeDatatypeParentRoutine routine = new RemoveCompositeDatatypeParentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlOldParent);
    return routine.applyRoutine();
  }
  
  public boolean replaceCollectionDatatypeParent(final Generalization gen, final Classifier umlNewParent, final Classifier umlOldParent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ReplaceCollectionDatatypeParentRoutine routine = new ReplaceCollectionDatatypeParentRoutine(_routinesFacade, _reactionExecutionState, _caller, gen, umlNewParent, umlOldParent);
    return routine.applyRoutine();
  }
}
