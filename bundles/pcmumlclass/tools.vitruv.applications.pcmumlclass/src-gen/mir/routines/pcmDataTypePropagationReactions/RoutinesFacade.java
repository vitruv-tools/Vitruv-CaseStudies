package mir.routines.pcmDataTypePropagationReactions;

import mir.routines.pcmDataTypePropagationReactions.AddCollectionDataTypeCorrespondenceRoutine;
import mir.routines.pcmDataTypePropagationReactions.RemoveOldCollectionDataTypeCorrespondenceRoutine;
import mir.routines.pcmDataTypePropagationReactions.SetTypeOfUmlParameterOrPropertyRoutine;
import mir.routines.pcmDataTypePropagationReactions.SetTypeOfUmlParameterOrProperty_CollectionRoutine;
import mir.routines.pcmDataTypePropagationReactions.SetTypeOfUmlParameterOrProperty_NonCollectionRoutine;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.TypedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
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
  
  public boolean setTypeOfUmlParameterOrProperty(final DataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetTypeOfUmlParameterOrPropertyRoutine routine = new SetTypeOfUmlParameterOrPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, umlElement, umlMultiplicity, tag);
    return routine.applyRoutine();
  }
  
  public boolean setTypeOfUmlParameterOrProperty_NonCollection(final DataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetTypeOfUmlParameterOrProperty_NonCollectionRoutine routine = new SetTypeOfUmlParameterOrProperty_NonCollectionRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, umlElement, umlMultiplicity, tag);
    return routine.applyRoutine();
  }
  
  public boolean setTypeOfUmlParameterOrProperty_Collection(final CollectionDataType pcmType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetTypeOfUmlParameterOrProperty_CollectionRoutine routine = new SetTypeOfUmlParameterOrProperty_CollectionRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, umlElement, umlMultiplicity, tag);
    return routine.applyRoutine();
  }
  
  public boolean removeOldCollectionDataTypeCorrespondence(final TypedElement umlElement, final String tag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveOldCollectionDataTypeCorrespondenceRoutine routine = new RemoveOldCollectionDataTypeCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, umlElement, tag);
    return routine.applyRoutine();
  }
  
  public boolean addCollectionDataTypeCorrespondence(final CollectionDataType pcmType, final TypedElement umlElement, final String tag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCollectionDataTypeCorrespondenceRoutine routine = new AddCollectionDataTypeCorrespondenceRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmType, umlElement, tag);
    return routine.applyRoutine();
  }
}
