package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaSetterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaSetterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Field jAttribute, @Extension final RoutinesFacade _routinesFacade) {
      boolean _javaSetterForAttributeExists = JavaMemberAndParameterUtil.javaSetterForAttributeExists(jAttribute);
      boolean _not = (!_javaSetterForAttributeExists);
      if (_not) {
        UmlToJavaHelper.createSetterForAttribute(jAttribute);
      }
    }
  }
  
  public CreateJavaSetterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.CreateJavaSetterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(getExecutionState(), this);
    this.jAttribute = jAttribute;
  }
  
  private Field jAttribute;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaSetterRoutine with input:");
    getLogger().debug("   jAttribute: " + this.jAttribute);
    
    userExecution.callRoutine1(jAttribute, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
