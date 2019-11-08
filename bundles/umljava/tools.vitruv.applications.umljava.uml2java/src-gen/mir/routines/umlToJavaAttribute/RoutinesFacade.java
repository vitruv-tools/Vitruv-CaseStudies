package mir.routines.umlToJavaAttribute;

import mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine;
import mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine;
import mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine;
import mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine;
import mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine;
import mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine;
import mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.emftext.language.java.members.Field;
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
    this.umlToJavaTypePropagation = this._getRoutinesFacadesProvider().getRoutinesFacade(this._getReactionsImportPath().append(ReactionsImportPath.fromPathString("umlToJavaTypePropagation")));
  }
  
  public final mir.routines.umlToJavaTypePropagation.RoutinesFacade umlToJavaTypePropagation;
  
  public boolean createJavaAttribute(final Classifier uClassifier, final Property umlAttribute) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaAttributeRoutine routine = new CreateJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, umlAttribute);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaAttribute(final Property umlAttr) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    DeleteJavaAttributeRoutine routine = new DeleteJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlAttr);
    return routine.applyRoutine();
  }
  
  public boolean setJavaAttributeFinal(final Property umlAttr) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetJavaAttributeFinalRoutine routine = new SetJavaAttributeFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, umlAttr);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaAttributeType(final Property uAttribute) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeJavaAttributeTypeRoutine routine = new ChangeJavaAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createJavaGetter(final Field jAttribute) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaGetterRoutine routine = new CreateJavaGetterRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createJavaSetter(final Field jAttribute) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateJavaSetterRoutine routine = new CreateJavaSetterRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttribute);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaAttribute(final String newName, final String oldName, final Property uAttribute) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    RenameJavaAttributeRoutine routine = new RenameJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, newName, oldName, uAttribute);
    return routine.applyRoutine();
  }
}
