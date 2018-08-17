package mir.routines.pcmCompositeDataTypeReactions;

import mir.routines.pcmCompositeDataTypeReactions.AddCorrespondenceForExistingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.AddParentTypeToCorrespondingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.ChangeNameOfCorrespondingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.CreateCorrespondingCompositetypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.DeleteCorrespondingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.DetectOrCreateCorrespondingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.InsertCorrespondingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.MoveCorrespondingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.RemoveCorrespondingCompositeTypeClassRoutine;
import mir.routines.pcmCompositeDataTypeReactions.RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.Repository;
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
  
  public boolean insertCorrespondingCompositeTypeClass(final CompositeDataType pcmType, final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingCompositeTypeClassRoutine routine = new InsertCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingCompositeTypeClass(final CompositeDataType pcmType, final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingCompositeTypeClassRoutine routine = new DetectOrCreateCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingCompositeTypeClass(final CompositeDataType pcmType, final org.eclipse.uml2.uml.Class umlCompositeClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingCompositeTypeClassRoutine routine = new AddCorrespondenceForExistingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, umlCompositeClass);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingCompositetypeClass(final CompositeDataType pcmType, final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingCompositetypeClassRoutine routine = new CreateCorrespondingCompositetypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingCompositeTypeClass(final CompositeDataType pcmType, final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingCompositeTypeClassRoutine routine = new MoveCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingCompositeTypeClass(final CompositeDataType pcmType, final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingCompositeTypeClassRoutine routine = new RemoveCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingCompositeTypeClass(final CompositeDataType pcmType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingCompositeTypeClassRoutine routine = new DeleteCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingCompositeTypeClass(final CompositeDataType pcmType, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingCompositeTypeClassRoutine routine = new ChangeNameOfCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, newName);
    return routine.applyRoutine();
  }
  
  public boolean addParentTypeToCorrespondingCompositeTypeClass(final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddParentTypeToCorrespondingCompositeTypeClassRoutine routine = new AddParentTypeToCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, pcmParentType);
    return routine.applyRoutine();
  }
  
  public boolean removeParentTypeFromCorrespondingCompositeTypeClass(final CompositeDataType pcmType, final CompositeDataType pcmParentType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine routine = new RemoveParentTypeFromCorrespondingCompositeTypeClassRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, pcmParentType);
    return routine.applyRoutine();
  }
}
