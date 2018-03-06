package mir.routines.javaToUmlAttribute;

import mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine;
import mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine;
import mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine;
import mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine;
import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
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
  
  public boolean createUmlAttributeInEnum(final Enumeration jEnum, final Field jAttr) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlAttributeInEnumRoutine routine = new CreateUmlAttributeInEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jAttr);
    return routine.applyRoutine();
  }
  
  public boolean createUmlAttributeInClass(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    CreateUmlAttributeInClassRoutine routine = new CreateUmlAttributeInClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jAttr);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlAttributeType(final Field jAttr, final TypeReference jType) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    ChangeUmlAttributeTypeRoutine routine = new ChangeUmlAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttr, jType);
    return routine.applyRoutine();
  }
  
  public boolean setUmlAttributeFinal(final Field jAttr, final Boolean isFinal) {
    RoutinesFacade _routinesFacade = this;
    ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    SetUmlAttributeFinalRoutine routine = new SetUmlAttributeFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttr, isFinal);
    return routine.applyRoutine();
  }
}
