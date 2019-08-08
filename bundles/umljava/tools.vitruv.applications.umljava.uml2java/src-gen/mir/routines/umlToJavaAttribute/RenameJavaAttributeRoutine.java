package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaAttributeRoutine extends AbstractRepairRoutineRealization {
  private RenameJavaAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJAttribute(final String newName, final String oldName, final Property uAttribute) {
      return uAttribute;
    }
    
    public void callRoutine1(final String newName, final String oldName, final Property uAttribute, final Field jAttribute, @Extension final RoutinesFacade _routinesFacade) {
      jAttribute.setName(uAttribute.getName());
      JavaMemberAndParameterUtil.renameGettersOfAttribute(jAttribute, oldName);
      JavaMemberAndParameterUtil.renameSettersOfAttribute(jAttribute, oldName);
    }
  }
  
  public RenameJavaAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final String newName, final String oldName, final Property uAttribute) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.RenameJavaAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.newName = newName;this.oldName = oldName;this.uAttribute = uAttribute;
  }
  
  private String newName;
  
  private String oldName;
  
  private Property uAttribute;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaAttributeRoutine with input:");
    getLogger().debug("   newName: " + this.newName);
    getLogger().debug("   oldName: " + this.oldName);
    getLogger().debug("   uAttribute: " + this.uAttribute);
    
    org.emftext.language.java.members.Field jAttribute = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJAttribute(newName, oldName, uAttribute), // correspondence source supplier
    	org.emftext.language.java.members.Field.class,
    	(org.emftext.language.java.members.Field _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jAttribute == null) {
    	return false;
    }
    registerObjectUnderModification(jAttribute);
    userExecution.callRoutine1(newName, oldName, uAttribute, jAttribute, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
