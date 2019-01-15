package mir.routines.umlCompositeDataTypeClassReactions;

import mir.routines.umlCompositeDataTypeClassReactions.AddCorrespondenceForExistingCompositeDataTypeRoutine;
import mir.routines.umlCompositeDataTypeClassReactions.ChangeNameOfCorrespondingCompositeDataTypeRoutine;
import mir.routines.umlCompositeDataTypeClassReactions.CreateCorrespondingCompositeDataTypeRoutine;
import mir.routines.umlCompositeDataTypeClassReactions.DeleteCorrespondingCompositeDataTypeRoutine;
import mir.routines.umlCompositeDataTypeClassReactions.DetectOrCreateCorrespondingCompositeDataTypeRoutine;
import mir.routines.umlCompositeDataTypeClassReactions.InsertCorrespondingCompositeDataTypeRoutine;
import mir.routines.umlCompositeDataTypeClassReactions.MoveCorrespondingCompositeDataTypeRoutine;
import mir.routines.umlCompositeDataTypeClassReactions.RemoveCorrespondingCompositeTypeRoutine;
import org.palladiosimulator.pcm.repository.CompositeDataType;
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
  
  public boolean insertCorrespondingCompositeDataType(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingCompositeDataTypeRoutine routine = new InsertCorrespondingCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingCompositeDataType(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingCompositeDataTypeRoutine routine = new DetectOrCreateCorrespondingCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingCompositeDataType(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingCompositeDataTypeRoutine routine = new CreateCorrespondingCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingCompositeDataType(final CompositeDataType pcmCompositeType, final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingCompositeDataTypeRoutine routine = new AddCorrespondenceForExistingCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmCompositeType, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingCompositeDataType(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingCompositeDataTypeRoutine routine = new MoveCorrespondingCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingCompositeType(final org.eclipse.uml2.uml.Class umlClass, final org.eclipse.uml2.uml.Package umlPackage) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingCompositeTypeRoutine routine = new RemoveCorrespondingCompositeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, umlPackage);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingCompositeDataType(final org.eclipse.uml2.uml.Class umlClass) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingCompositeDataTypeRoutine routine = new DeleteCorrespondingCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingCompositeDataType(final org.eclipse.uml2.uml.Class umlClass, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingCompositeDataTypeRoutine routine = new ChangeNameOfCorrespondingCompositeDataTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlClass, newName);
    return routine.applyRoutine();
  }
}
