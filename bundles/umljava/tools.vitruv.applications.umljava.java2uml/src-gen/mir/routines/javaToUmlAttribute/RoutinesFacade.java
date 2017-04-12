package mir.routines.javaToUmlAttribute;

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
  
  public void createUmlAttribute(final org.emftext.language.java.classifiers.Class jClass, final Field jAttr) {
    mir.routines.javaToUmlAttribute.CreateUmlAttributeRoutine effect = new mir.routines.javaToUmlAttribute.CreateUmlAttributeRoutine(this.executionState, calledBy,
    	jClass, jAttr);
    effect.applyRoutine();
  }
  
  public void changeUmlAttributeType(final Field jAttr, final TypeReference jType) {
    mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine effect = new mir.routines.javaToUmlAttribute.ChangeUmlAttributeTypeRoutine(this.executionState, calledBy,
    	jAttr, jType);
    effect.applyRoutine();
  }
  
  public void setUmlAttributeFinal(final Field jAttr, final Integer isFinal) {
    mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine effect = new mir.routines.javaToUmlAttribute.SetUmlAttributeFinalRoutine(this.executionState, calledBy,
    	jAttr, isFinal);
    effect.applyRoutine();
  }
}
