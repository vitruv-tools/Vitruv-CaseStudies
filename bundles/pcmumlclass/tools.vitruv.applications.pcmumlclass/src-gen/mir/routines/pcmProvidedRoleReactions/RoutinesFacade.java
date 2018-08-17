package mir.routines.pcmProvidedRoleReactions;

import mir.routines.pcmProvidedRoleReactions.AddCorrespondenceForExistingGeneralizationRoutine;
import mir.routines.pcmProvidedRoleReactions.ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine;
import mir.routines.pcmProvidedRoleReactions.CreateCorrespondingProvidedGeneralizationRoutine;
import mir.routines.pcmProvidedRoleReactions.DeleteCorrespondingProvidedGeneralizationRoutine;
import mir.routines.pcmProvidedRoleReactions.DetectOrCreateCorrespondingProvidedGeneralizationRoutine;
import mir.routines.pcmProvidedRoleReactions.InsertCorrespondingProvidedGeneralizationRoutine;
import mir.routines.pcmProvidedRoleReactions.MoveCorrespondingProvidedGeneralizationRoutine;
import mir.routines.pcmProvidedRoleReactions.RemoveCorrespondingProvidedGeneralizationRoutine;
import org.eclipse.uml2.uml.Generalization;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
  
  public boolean insertCorrespondingProvidedGeneralization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingProvidedGeneralizationRoutine routine = new InsertCorrespondingProvidedGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingProvidedGeneralization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingProvidedGeneralizationRoutine routine = new DetectOrCreateCorrespondingProvidedGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingGeneralization(final OperationProvidedRole pcmProvided, final Generalization umlGeneralization) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingGeneralizationRoutine routine = new AddCorrespondenceForExistingGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, umlGeneralization);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingProvidedGeneralization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingProvidedGeneralizationRoutine routine = new CreateCorrespondingProvidedGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingProvidedGeneralization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingProvidedGeneralizationRoutine routine = new MoveCorrespondingProvidedGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingProvidedGeneralization(final OperationProvidedRole pcmProvided, final InterfaceProvidingRequiringEntity pcmIPRE) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingProvidedGeneralizationRoutine routine = new RemoveCorrespondingProvidedGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmIPRE);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingProvidedGeneralization(final OperationProvidedRole pcmProvided) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingProvidedGeneralizationRoutine routine = new DeleteCorrespondingProvidedGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided);
    return routine.applyRoutine();
  }
  
  public boolean changeInterfaceOfCorrespondingProvidedGeneralization(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine routine = new ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmProvided, pcmInterface);
    return routine.applyRoutine();
  }
}
