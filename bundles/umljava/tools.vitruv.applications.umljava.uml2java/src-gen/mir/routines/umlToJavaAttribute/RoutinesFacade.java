package mir.routines.umlToJavaAttribute;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Field;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createJavaAttributeInClass(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaAttributeInClassRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaAttributeInClassRoutine(this.executionState, calledBy,
    	umlClass, umlAttribute);
    effect.applyRoutine();
  }
  
  public void createJavaAttributeInEnum(final Enumeration uEnum, final Property umlAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaAttributeInEnumRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaAttributeInEnumRoutine(this.executionState, calledBy,
    	uEnum, umlAttribute);
    effect.applyRoutine();
  }
  
  public void deleteJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine effect = new mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void setJavaAttributeFinal(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine effect = new mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine(this.executionState, calledBy,
    	umlAttr);
    effect.applyRoutine();
  }
  
  public void changeJavaAttributeType(final Property uAttr, final Type uType) {
    mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine effect = new mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine(this.executionState, calledBy,
    	uAttr, uType);
    effect.applyRoutine();
  }
  
  public void handleMultiplicityForJavaAttribute(final Property uAttribute) {
    mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine effect = new mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine(this.executionState, calledBy,
    	uAttribute);
    effect.applyRoutine();
  }
  
  public void createJavaConstructor(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaConstructorRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaConstructorRoutine(this.executionState, calledBy,
    	jAttribute);
    effect.applyRoutine();
  }
  
  public void createJavaGetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine(this.executionState, calledBy,
    	jAttribute);
    effect.applyRoutine();
  }
  
  public void createJavaSetterWithNullCheck(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaSetterWithNullCheckRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaSetterWithNullCheckRoutine(this.executionState, calledBy,
    	jAttribute);
    effect.applyRoutine();
  }
}
