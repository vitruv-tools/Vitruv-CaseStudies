package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeJavaAttributeTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeJavaAttributeTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceCustomType(final Property uAttr, final Type uType, final Field jAttr) {
      return uType;
    }
    
    public EObject getCorrepondenceSourceJAttr(final Property uAttr, final Type uType) {
      return uAttr;
    }
    
    public void callRoutine1(final Property uAttr, final Type uType, final Field jAttr, final org.emftext.language.java.classifiers.Class customType, @Extension final RoutinesFacade _routinesFacade) {
      jAttr.setTypeReference(UmlToJavaHelper.createTypeReferenceAndUpdateImport(uType, customType, jAttr.getContainingCompilationUnit(), this.userInteracting));
      JavaMemberAndParameterUtil.updateAttributeTypeInSetters(jAttr);
      JavaMemberAndParameterUtil.updateAttributeTypeInGetters(jAttr);
    }
  }
  
  public ChangeJavaAttributeTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property uAttr, final Type uType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.ChangeJavaAttributeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(getExecutionState(), this);
    this.uAttr = uAttr;this.uType = uType;
  }
  
  private Property uAttr;
  
  private Type uType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeJavaAttributeTypeRoutine with input:");
    getLogger().debug("   Property: " + this.uAttr);
    getLogger().debug("   Type: " + this.uType);
    
    Field jAttr = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJAttr(uAttr, uType), // correspondence source supplier
    	Field.class,
    	(Field _element) -> true, // correspondence precondition checker
    	null);
    if (jAttr == null) {
    	return;
    }
    registerObjectUnderModification(jAttr);
    org.emftext.language.java.classifiers.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(uAttr, uType, jAttr), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customType);
    userExecution.callRoutine1(uAttr, uType, jAttr, customType, actionsFacade);
    
    postprocessElements();
  }
}