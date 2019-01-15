package mir.routines.pcmInnerDeclarationReactions;

import mir.routines.pcmInnerDeclarationReactions.AddCorrespondenceForExistingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.ChangeNameOfCorrespondingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.ChangeTypeOfCorrespondingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.CreateCorrespondingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.DeleteCorrespondingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.DetectOrCreateCorrespondingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.InsertCorrespondingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.MoveCorrespondingAttributeRoutine;
import mir.routines.pcmInnerDeclarationReactions.RemoveCorrespondingAttributeRoutine;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.DataType;
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
    this.pcmDataTypePropagationReactions = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("pcmDataTypePropagationReactions")));
  }
  
  public final mir.routines.pcmDataTypePropagationReactions.RoutinesFacade pcmDataTypePropagationReactions;
  
  public boolean insertCorrespondingAttribute(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    InsertCorrespondingAttributeRoutine routine = new InsertCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean detectOrCreateCorrespondingAttribute(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DetectOrCreateCorrespondingAttributeRoutine routine = new DetectOrCreateCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean addCorrespondenceForExistingAttribute(final InnerDeclaration pcmAttribute, final Property umlAttribute) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    AddCorrespondenceForExistingAttributeRoutine routine = new AddCorrespondenceForExistingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute, umlAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createCorrespondingAttribute(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateCorrespondingAttributeRoutine routine = new CreateCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean moveCorrespondingAttribute(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    MoveCorrespondingAttributeRoutine routine = new MoveCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean removeCorrespondingAttribute(final InnerDeclaration pcmAttribute, final CompositeDataType pcmComposite) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RemoveCorrespondingAttributeRoutine routine = new RemoveCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute, pcmComposite);
    return routine.applyRoutine();
  }
  
  public boolean deleteCorrespondingAttribute(final InnerDeclaration pcmAttribute) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteCorrespondingAttributeRoutine routine = new DeleteCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute);
    return routine.applyRoutine();
  }
  
  public boolean changeNameOfCorrespondingAttribute(final InnerDeclaration pcmAttribute, final String newName) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeNameOfCorrespondingAttributeRoutine routine = new ChangeNameOfCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmAttribute, newName);
    return routine.applyRoutine();
  }
  
  public boolean changeTypeOfCorrespondingAttribute(final InnerDeclaration pcmInnerDeclaration, final DataType pcmDataType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeTypeOfCorrespondingAttributeRoutine routine = new ChangeTypeOfCorrespondingAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, pcmInnerDeclaration, pcmDataType);
    return routine.applyRoutine();
  }
}
