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
public class DeleteJavaAttributeRoutine extends AbstractRepairRoutineRealization {
  private DeleteJavaAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlAttr, final Field jAttr) {
      return jAttr;
    }
    
    public EObject getCorrepondenceSourceJAttr(final Property umlAttr) {
      return umlAttr;
    }
    
    public void callRoutine1(final Property umlAttr, final Field jAttr, @Extension final RoutinesFacade _routinesFacade) {
      final EObject uClassifier = umlAttr.eContainer();
      if (((uClassifier != null) && (uClassifier instanceof org.eclipse.uml2.uml.Class))) {
        JavaMemberAndParameterUtil.removeJavaGettersOfAttribute(jAttr);
        JavaMemberAndParameterUtil.removeJavaSettersOfAttribute(jAttr);
      }
    }
  }
  
  public DeleteJavaAttributeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlAttr) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.DeleteJavaAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlAttr = umlAttr;
  }
  
  private Property umlAttr;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteJavaAttributeRoutine with input:");
    getLogger().debug("   umlAttr: " + this.umlAttr);
    
    org.emftext.language.java.members.Field jAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJAttr(umlAttr), // correspondence source supplier
    	org.emftext.language.java.members.Field.class,
    	(org.emftext.language.java.members.Field _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jAttr == null) {
    	return false;
    }
    registerObjectUnderModification(jAttr);
    userExecution.callRoutine1(umlAttr, jAttr, this.getRoutinesFacade());
    
    deleteObject(userExecution.getElement1(umlAttr, jAttr));
    
    postprocessElements();
    
    return true;
  }
}
