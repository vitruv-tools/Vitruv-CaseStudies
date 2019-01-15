package mir.routines.umlInterfaceGeneralizationReactions;

import mir.routines.umlInterfaceGeneralizationReactions.AddParentInterfaceRoutine;
import mir.routines.umlInterfaceGeneralizationReactions.RemoveParentInterfaceRoutine;
import mir.routines.umlInterfaceGeneralizationReactions.ReplaceParentInterfaceRoutine;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
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
  
  public boolean addParentInterface(final Interface umlInterface, final Classifier umlNewParent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddParentInterfaceRoutine routine = new AddParentInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, umlNewParent);
    return routine.applyRoutine();
  }
  
  public boolean removeParentInterface(final Interface umlInterface, final Classifier umlOldParent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveParentInterfaceRoutine routine = new RemoveParentInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlInterface, umlOldParent);
    return routine.applyRoutine();
  }
  
  public boolean replaceParentInterface(final Generalization gen, final Classifier newParent, final Classifier oldParent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ReplaceParentInterfaceRoutine routine = new ReplaceParentInterfaceRoutine(_routinesFacade, _reactionExecutionState, _caller, gen, newParent, oldParent);
    return routine.applyRoutine();
  }
}
