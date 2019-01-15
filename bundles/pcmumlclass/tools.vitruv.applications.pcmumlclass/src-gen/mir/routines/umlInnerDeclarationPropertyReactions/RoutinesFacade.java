package mir.routines.umlInnerDeclarationPropertyReactions;

import mir.routines.umlInnerDeclarationPropertyReactions.AddCorrespondenceForCollectionTypeRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.AddCorrespondenceForExistingInnerDeclarationRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.ChangeNameOfCorrespondingInnerDeclarationRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.CreateCorrespondingInnerDeclarationRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.DeleteCorrespondingInnerDeclarationRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.DetectOrCreateCorrespondingInnerDeclarationRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.InsertCorrespondingInnerDeclarationRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.MoveCorrespondingInnerDeclarationRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.PropagateTypeChangeRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.RemoveCorrespondenceForOldCollectionTypeRoutine;
import mir.routines.umlInnerDeclarationPropertyReactions.RemoveCorrespondingInnerDeclarationRoutine;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
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
  
  public boolean insertCorrespondingInnerDeclaration(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingInnerDeclarationRoutine routine = new InsertCorrespondingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlCompositeType);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingInnerDeclaration(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingInnerDeclarationRoutine routine = new DetectOrCreateCorrespondingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlCompositeType);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingInnerDeclaration(final Property umlProperty, final InnerDeclaration pcmInnerDeclaration) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingInnerDeclarationRoutine routine = new AddCorrespondenceForExistingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, pcmInnerDeclaration);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingInnerDeclaration(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingInnerDeclarationRoutine routine = new CreateCorrespondingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlCompositeType);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingInnerDeclaration(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingInnerDeclarationRoutine routine = new MoveCorrespondingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlCompositeType);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingInnerDeclaration(final Property umlProperty, final org.eclipse.uml2.uml.Class umlCompositeType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingInnerDeclarationRoutine routine = new RemoveCorrespondingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlCompositeType);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingInnerDeclaration(final Property umlProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingInnerDeclarationRoutine routine = new DeleteCorrespondingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingInnerDeclaration(final Property umlProperty, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingInnerDeclarationRoutine routine = new ChangeNameOfCorrespondingInnerDeclarationRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, newName);
    return routine.applyRoutine();
  }
  
  public boolean propagateTypeChange(final Property umlProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    PropagateTypeChangeRoutine routine = new PropagateTypeChangeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondenceForOldCollectionType(final Property umlProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondenceForOldCollectionTypeRoutine routine = new RemoveCorrespondenceForOldCollectionTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForCollectionType(final Property umlProperty, final CollectionDataType pcmCollectionType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForCollectionTypeRoutine routine = new AddCorrespondenceForCollectionTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, pcmCollectionType);
    return routine.applyRoutine();
  }
}
