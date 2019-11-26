package mir.routines.umlReturnAndRegularParameterTypeReactions;

import mir.routines.umlReturnAndRegularParameterTypeReactions.AddCorrespondenceForCollectionType_ParameterRoutine;
import mir.routines.umlReturnAndRegularParameterTypeReactions.PropagateTypeChangeRoutine;
import mir.routines.umlReturnAndRegularParameterTypeReactions.RemoveCorrespondenceForOldCollectionType_ParameterRoutine;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.CollectionDataType;
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
  
  public boolean propagateTypeChange(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateTypeChangeRoutine routine = new PropagateTypeChangeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondenceForOldCollectionType_Parameter(final Parameter umlParameter) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondenceForOldCollectionType_ParameterRoutine routine = new RemoveCorrespondenceForOldCollectionType_ParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForCollectionType_Parameter(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForCollectionType_ParameterRoutine routine = new AddCorrespondenceForCollectionType_ParameterRoutine(_routinesFacade, _reactionExecutionState, _caller, umlParameter, pcmCollectionType);
    return routine.applyRoutine();
  }
}
