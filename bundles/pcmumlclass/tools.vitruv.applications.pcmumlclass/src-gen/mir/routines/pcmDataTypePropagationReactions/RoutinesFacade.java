package mir.routines.pcmDataTypePropagationReactions;

import mir.routines.pcmDataTypePropagationReactions.AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine;
import mir.routines.pcmDataTypePropagationReactions.RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine;
import mir.routines.pcmDataTypePropagationReactions.ReplaceTypeOfCorrespondingParameterOrPropertyRoutine;
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
  
  public boolean removeCollectionTypeCorrespondenceFromParameterOrProperty(final CollectionDataType oldCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine routine = new RemoveCollectionTypeCorrespondenceFromParameterOrPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, oldCollectionDataType, umlElement, umlMultiplicity, tag);
    return routine.applyRoutine();
  }
  
  public boolean addCollectionTypeCorrespondenceToParameterOrProperty(final CollectionDataType newCollectionDataType, final TypedElement umlElement, final MultiplicityElement umlMultiplicity, final String tag) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine routine = new AddCollectionTypeCorrespondenceToParameterOrPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, newCollectionDataType, umlElement, umlMultiplicity, tag);
    return routine.applyRoutine();
  }
  
  public boolean replaceTypeOfCorrespondingParameterOrProperty(final DataType pcmSimpleType, final TypedElement umlElement) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ReplaceTypeOfCorrespondingParameterOrPropertyRoutine routine = new ReplaceTypeOfCorrespondingParameterOrPropertyRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmSimpleType, umlElement);
    return routine.applyRoutine();
  }
}
