package mir.routines.umlToJava;

import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
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
    
    public EObject getElement1(final Property uAttr, final Type uType, final Field jAttr, final org.emftext.language.java.classifiers.Class customType) {
      return jAttr;
    }
    
    public void update0Element(final Property uAttr, final Type uType, final Field jAttr, final org.emftext.language.java.classifiers.Class customType) {
      TypeReference _createTypeReference = UmlToJavaHelper.createTypeReference(uType, customType);
      jAttr.setTypeReference(_createTypeReference);
    }
    
    public EObject getCorrepondenceSourceJAttr(final Property uAttr, final Type uType) {
      return uAttr;
    }
  }
  
  public ChangeJavaAttributeTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property uAttr, final Type uType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.ChangeJavaAttributeTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
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
    initializeRetrieveElementState(jAttr);
    org.emftext.language.java.classifiers.Class customType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomType(uAttr, uType, jAttr), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(customType);
    // val updatedElement userExecution.getElement1(uAttr, uType, jAttr, customType);
    userExecution.update0Element(uAttr, uType, jAttr, customType);
    
    postprocessElementStates();
  }
}
