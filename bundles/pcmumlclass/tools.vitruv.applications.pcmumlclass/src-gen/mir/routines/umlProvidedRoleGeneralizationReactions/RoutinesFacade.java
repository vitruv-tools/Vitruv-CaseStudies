package mir.routines.umlProvidedRoleGeneralizationReactions;

import mir.routines.umlProvidedRoleGeneralizationReactions.AddCorrespondenceForExistingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.ChangeTypeOfCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.CreateCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.Debugging_detectOrCreateCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.DeleteCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.DetectOrCreateCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.InsertCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.MoveCorrespondingProvidedRoleRoutine;
import mir.routines.umlProvidedRoleGeneralizationReactions.RemoveCorrespondingProvidedRoleRoutine;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
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
  
  public boolean insertCorrespondingProvidedRole(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingProvidedRoleRoutine routine = new InsertCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean debugging_detectOrCreateCorrespondingProvidedRole(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    Debugging_detectOrCreateCorrespondingProvidedRoleRoutine routine = new Debugging_detectOrCreateCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingProvidedRole(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingProvidedRoleRoutine routine = new DetectOrCreateCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingProvidedRole(final Generalization umlGeneralization, final OperationProvidedRole pcmProvided) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingProvidedRoleRoutine routine = new AddCorrespondenceForExistingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, pcmProvided);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingProvidedRole(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingProvidedRoleRoutine routine = new CreateCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingProvidedRole(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingProvidedRoleRoutine routine = new MoveCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingProvidedRole(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingProvidedRoleRoutine routine = new RemoveCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingProvidedRole(final Generalization umlGeneralization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingProvidedRoleRoutine routine = new DeleteCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingProvidedRole(final Generalization umlGeneralization, final Interface umlNewInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingProvidedRoleRoutine routine = new ChangeTypeOfCorrespondingProvidedRoleRoutine(_routinesFacade, _reactionExecutionState, _caller, umlGeneralization, umlNewInterface);
    return routine.applyRoutine();
  }
}
