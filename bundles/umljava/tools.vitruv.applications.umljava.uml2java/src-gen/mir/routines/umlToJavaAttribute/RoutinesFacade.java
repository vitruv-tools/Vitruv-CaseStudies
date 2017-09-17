package mir.routines.umlToJavaAttribute;

import org.eclipse.uml2.uml.Classifier;
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
  
  public boolean createJavaAttribute(final Classifier uClassifier, final Property umlAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine(this.executionState, calledBy, uClassifier, umlAttribute);
    return effect.applyRoutine();
  }
  
  public boolean deleteJavaAttribute(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine effect = new mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine(this.executionState, calledBy, umlAttr);
    return effect.applyRoutine();
  }
  
  public boolean setJavaAttributeFinal(final Property umlAttr) {
    mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine effect = new mir.routines.umlToJavaAttribute.SetJavaAttributeFinalRoutine(this.executionState, calledBy, umlAttr);
    return effect.applyRoutine();
  }
  
  public boolean changeJavaAttributeType(final Property uAttr, final Type uType) {
    mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine effect = new mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine(this.executionState, calledBy, uAttr, uType);
    return effect.applyRoutine();
  }
  
  public boolean handleMultiplicityForJavaAttribute(final Property uAttribute) {
    mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine effect = new mir.routines.umlToJavaAttribute.HandleMultiplicityForJavaAttributeRoutine(this.executionState, calledBy, uAttribute);
    return effect.applyRoutine();
  }
  
  public boolean createJavaGetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaGetterRoutine(this.executionState, calledBy, jAttribute);
    return effect.applyRoutine();
  }
  
  public boolean createJavaSetter(final Field jAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine(this.executionState, calledBy, jAttribute);
    return effect.applyRoutine();
  }
  
  public boolean renameJavaAttribute(final String oldName, final String newName, final Property uAttribute) {
    mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine effect = new mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine(this.executionState, calledBy, oldName, newName, uAttribute);
    return effect.applyRoutine();
  }
}
