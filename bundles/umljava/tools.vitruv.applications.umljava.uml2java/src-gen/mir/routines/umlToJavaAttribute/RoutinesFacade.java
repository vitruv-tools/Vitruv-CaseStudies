package mir.routines.umlToJavaAttribute;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadeExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.RoutinesFacadesProvider;
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final RoutinesFacadesProvider routinesFacadesProvider, final ReactionsImportPath reactionsImportPath, final RoutinesFacadeExecutionState executionState) {
    super(routinesFacadesProvider, reactionsImportPath, executionState);
  }
  
  public boolean createJavaAttribute(final Classifier uClassifier, final Property umlAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, uClassifier, umlAttribute);
    return routine.applyRoutine();
  }
  
  public boolean deleteJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, umlAttr);
    return routine.applyRoutine();
  }
  
  public boolean setJavaAttributeFinal(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine routine = new mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine(_routinesFacade, _reactionExecutionState, _caller, umlAttr);
    return routine.applyRoutine();
  }
  
  public boolean changeJavaAttributeType(final Property uAttr, final Type uType) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine routine = new mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine(_routinesFacade, _reactionExecutionState, _caller, uAttr, uType);
    return routine.applyRoutine();
  }
  
  public boolean handleMultiplicityForJavaAttribute(final Property uAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, uAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createJavaGetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine routine = new mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttribute);
    return routine.applyRoutine();
  }
  
  public boolean createJavaSetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine routine = new mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine(_routinesFacade, _reactionExecutionState, _caller, jAttribute);
    return routine.applyRoutine();
  }
  
  public boolean renameJavaAttribute(final String oldName, final String newName, final Property uAttribute) {
    mir.routines.umlToJavaAttribute.RoutinesFacade _routinesFacade = this;
    tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState _reactionExecutionState = this._getExecutionState().getReactionExecutionState();
    tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving _caller = this._getExecutionState().getCaller();
    mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine routine = new mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine(_routinesFacade, _reactionExecutionState, _caller, oldName, newName, uAttribute);
    return routine.applyRoutine();
  }
}
