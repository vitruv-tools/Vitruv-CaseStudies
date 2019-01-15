package mir.routines.umlAssemblyContextPropertyReactions;

import mir.routines.umlAssemblyContextPropertyReactions.AddCorrespondenceForExistingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.ChangeNameOfCorrespondingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.ChangeTypeOfCorrespondingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.CreateCorrespondingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.DeleteCorrespondingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.DetectOrCreateCorrespondingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.InsertCorrespondingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.MoveCorrespondingAssemblyContextRoutine;
import mir.routines.umlAssemblyContextPropertyReactions.RemoveCorrespondingAssemblyContextRoutine;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
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
  
  public boolean insertCorrespondingAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingAssemblyContextRoutine routine = new InsertCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingAssemblyContextRoutine routine = new DetectOrCreateCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingAssemblyContext(final Property umlProperty, final AssemblyContext pcmAssemblyContext) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingAssemblyContextRoutine routine = new AddCorrespondenceForExistingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, pcmAssemblyContext);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingAssemblyContextRoutine routine = new CreateCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingAssemblyContextRoutine routine = new MoveCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingAssemblyContextRoutine routine = new RemoveCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlComponent);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingAssemblyContext(final Property umlProperty) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingAssemblyContextRoutine routine = new DeleteCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingAssemblyContext(final Property umlProperty, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingAssemblyContextRoutine routine = new ChangeNameOfCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingAssemblyContext(final Property umlProperty, final org.eclipse.uml2.uml.Class umlNewInnerComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingAssemblyContextRoutine routine = new ChangeTypeOfCorrespondingAssemblyContextRoutine(_routinesFacade, _reactionExecutionState, _caller, umlProperty, umlNewInnerComponent);
    return routine.applyRoutine();
  }
}
