package mir.routines.umlToJavaAttribute;

import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createJavaAttribute(final org.eclipse.uml2.uml.Class umlClass, final Property umlAttribute) {
    mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine effect = new mir.routines.umlToJavaAttribute.CreateJavaAttributeRoutine(this.executionState, calledBy,
    	umlClass, umlAttribute);
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
}
