package mir.routines.pcmAssemblyContextReactions;

import mir.routines.pcmAssemblyContextReactions.AddCorrespondenceForExistingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.ChangeNameOfCorrespondingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.CreateCorrespondingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.DeleteCorrespondingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.DetectOrCreateCorrespondingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.InsertCorrespondingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.MoveCorrespondingAssemblyContextPropertyRoutine;
import mir.routines.pcmAssemblyContextReactions.RemoveCorrespondingAssemblyContextPropertyRoutine;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
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
  
  public boolean insertCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingAssemblyContextPropertyRoutine routine = new InsertCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingAssemblyContextPropertyRoutine routine = new DetectOrCreateCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingAssemblyContextProperty(final AssemblyContext pcmAssembly, final Property umlProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingAssemblyContextPropertyRoutine routine = new AddCorrespondenceForExistingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingAssemblyContextPropertyRoutine routine = new CreateCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingAssemblyContextPropertyRoutine routine = new MoveCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingAssemblyContextPropertyRoutine routine = new RemoveCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingAssemblyContextPropertyRoutine routine = new DeleteCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingAssemblyContextPropertyRoutine routine = new ChangeNameOfCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingAssemblyContextProperty(final AssemblyContext pcmAssembly, final RepositoryComponent pcmComonent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine routine = new ChangeTypeOfCorrespondingAssemblyContextPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAssembly, pcmComonent);
    return routine.applyRoutine();
  }
}
