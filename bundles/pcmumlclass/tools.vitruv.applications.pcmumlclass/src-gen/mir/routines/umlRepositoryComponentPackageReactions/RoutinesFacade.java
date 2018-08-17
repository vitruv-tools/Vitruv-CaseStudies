package mir.routines.umlRepositoryComponentPackageReactions;

import mir.routines.umlRepositoryComponentPackageReactions.AddCorrespondenceForExistingRepositoryComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.ChangeNameOfCorrespondingRepositoryComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.CreateCorrespondingBasicComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.CreateCorrespondingCompositeComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.CreateCorrespondingSubSystemRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.DeleteCorrespondingRepositoryComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.DetectOrCreateCorrespondingRepositoryComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.InsertCorrespondingRepositoryComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.MoveCorrespondingRepositoryComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.RemoveCorrespondingRepositoryComponentRoutine;
import mir.routines.umlRepositoryComponentPackageReactions.UserDisambiguateCorrespondingRepositoryComponentTypeRoutine;
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
  
  public boolean insertCorrespondingRepositoryComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingRepositoryComponentRoutine routine = new InsertCorrespondingRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingRepositoryComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingRepositoryComponentRoutine routine = new DetectOrCreateCorrespondingRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingRepositoryComponent(final org.eclipse.uml2.uml.Package umlPkg, final RepositoryComponent pcmComponent) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingRepositoryComponentRoutine routine = new AddCorrespondenceForExistingRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, pcmComponent);
    return routine.applyRoutine();
  }
  
  public boolean userDisambiguateCorrespondingRepositoryComponentType(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    UserDisambiguateCorrespondingRepositoryComponentTypeRoutine routine = new UserDisambiguateCorrespondingRepositoryComponentTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingBasicComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingBasicComponentRoutine routine = new CreateCorrespondingBasicComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingCompositeComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingCompositeComponentRoutine routine = new CreateCorrespondingCompositeComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingSubSystem(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingSubSystemRoutine routine = new CreateCorrespondingSubSystemRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingRepositoryComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingRepositoryComponentRoutine routine = new MoveCorrespondingRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingRepositoryComponent(final org.eclipse.uml2.uml.Package umlPkg, final org.eclipse.uml2.uml.Package umlParentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingRepositoryComponentRoutine routine = new RemoveCorrespondingRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, umlParentPkg);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRepositoryComponent(final org.eclipse.uml2.uml.Package umlComponentPkg) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRepositoryComponentRoutine routine = new DeleteCorrespondingRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlComponentPkg);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingRepositoryComponent(final org.eclipse.uml2.uml.Package umlPkg, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingRepositoryComponentRoutine routine = new ChangeNameOfCorrespondingRepositoryComponentRoutine(_routinesFacade, _reactionExecutionState, _caller, umlPkg, newName);
    return routine.applyRoutine();
  }
}
