package mir.routines.javaToUmlAttribute;

import org.emftext.language.java.classifiers.Enumeration;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean createUmlAttributeInEnum(final Enumeration jEnum, final Field jAttr) {
    mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine effect = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInEnumRoutine(this.executionState, calledBy, jEnum, jAttr);
    return effect.applyRoutine();
  }
  
  public boolean createUmlAttributeInClass(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine effect = new mir.routines.javaToUmlAttribute.CreateUmlAttributeInClassRoutine(this.executionState, calledBy, jClass, jAttr);
    return effect.applyRoutine();
  }
  
  public boolean changeUmlAttributeType(final Field jAttr, final TypeReference jType) {
    mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine effect = new mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine(this.executionState, calledBy, jAttr, jType);
    return effect.applyRoutine();
  }
  
  public boolean setUmlAttributeFinal(final Field jAttr, final Boolean isFinal) {
    mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine effect = new mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine(this.executionState, calledBy, jAttr, isFinal);
    return effect.applyRoutine();
  }
}
