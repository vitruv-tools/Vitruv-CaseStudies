package mir.routines.pcmRepositoryReactions;

import mir.routines.pcmRepositoryReactions.AddPrimitiveDatatypeCorrespondenceRoutine;
import mir.routines.pcmRepositoryReactions.AddUmlModelCorrespondenceRoutine;
import mir.routines.pcmRepositoryReactions.BootstrapPrimitiveDatatypesRoutine;
import mir.routines.pcmRepositoryReactions.ChangeNameOfCorrespondingRepositoryPackageRoutine;
import mir.routines.pcmRepositoryReactions.CreateUmlContractsPackageRoutine;
import mir.routines.pcmRepositoryReactions.CreateUmlDatatypesPackageRoutine;
import mir.routines.pcmRepositoryReactions.CreateUmlRepositoryPackageRoutine;
import mir.routines.pcmRepositoryReactions.DeleteCorrespondingRepositoryPackagesRoutine;
import mir.routines.pcmRepositoryReactions.EnsureUmlModelCorrespondenceExistsRoutine;
import mir.routines.pcmRepositoryReactions.InitializePcmRepositoryUmlPackagesCorrespondenceRoutine;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
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
  
  public boolean initializePcmRepositoryUmlPackagesCorrespondence(final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InitializePcmRepositoryUmlPackagesCorrespondenceRoutine routine = new InitializePcmRepositoryUmlPackagesCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean createUmlRepositoryPackage(final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlRepositoryPackageRoutine routine = new CreateUmlRepositoryPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean ensureUmlModelCorrespondenceExists(final Model newModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    EnsureUmlModelCorrespondenceExistsRoutine routine = new EnsureUmlModelCorrespondenceExistsRoutine(_routinesFacade, _reactionExecutionState, _caller, newModel);
    return routine.applyRoutine();
  }
  
  public boolean addUmlModelCorrespondence(final Model newModel) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddUmlModelCorrespondenceRoutine routine = new AddUmlModelCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, newModel);
    return routine.applyRoutine();
  }
  
  public boolean createUmlContractsPackage(final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlContractsPackageRoutine routine = new CreateUmlContractsPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean createUmlDatatypesPackage(final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlDatatypesPackageRoutine routine = new CreateUmlDatatypesPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingRepositoryPackage(final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingRepositoryPackageRoutine routine = new ChangeNameOfCorrespondingRepositoryPackageRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingRepositoryPackages(final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingRepositoryPackagesRoutine routine = new DeleteCorrespondingRepositoryPackagesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean bootstrapPrimitiveDatatypes(final Repository pcmRepo) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    BootstrapPrimitiveDatatypesRoutine routine = new BootstrapPrimitiveDatatypesRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmRepo);
    return routine.applyRoutine();
  }
  
  public boolean addPrimitiveDatatypeCorrespondence(final PrimitiveDataType pcmPrimitiveType, final PrimitiveType umlPrimitiveType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddPrimitiveDatatypeCorrespondenceRoutine routine = new AddPrimitiveDatatypeCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmPrimitiveType, umlPrimitiveType);
    return routine.applyRoutine();
  }
}
