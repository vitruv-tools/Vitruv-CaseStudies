package mir.routines.javaToUmlAttribute;

import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createUmlAttributeInEnum(final Enumeration jEnum, final Field jAttr) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine routine = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine(_routinesFacade, _reactionExecutionState, _caller, jEnum, jAttr);
    return routine.applyRoutine();
  }
  
  public boolean createUmlAttributeInClass(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine routine = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine(_routinesFacade, _reactionExecutionState, _caller, jClass, jAttr);
    return routine.applyRoutine();
  }
  
  public boolean changeUmlAttributeType(final Field jAttr, final TypeReference jType) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine routine = new mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttr, jType);
    return routine.applyRoutine();
  }
  
  public boolean setUmlAttributeFinal(final Field jAttr, final Boolean isFinal) {
    mir.routines.javaToUmlAttribute.RoutinesFacade _routinesFacade = this._getRoutinesFacadesProvider().getRoutinesFacade(tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath.fromPathString("JavaToUmlAttribute").prepend(this._getParentImportPath()));
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine routine = new mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttr, isFinal);
    return routine.applyRoutine();
  }
}
