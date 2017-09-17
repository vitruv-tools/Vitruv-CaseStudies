package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.applications.umljava.util.java.JavaModifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaMethodAbstractRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaMethodAbstractRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation uOperation, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod javaMethod) {
      return javaMethod;
    }
    
    public void update0Element(final Operation uOperation, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod javaMethod) {
      JavaModifierUtil.setAbstract(javaMethod, uOperation.isAbstract());
    }
    
    public EObject getCorrepondenceSourceJavaClass(final Operation uOperation) {
      org.eclipse.uml2.uml.Class _class_ = uOperation.getClass_();
      return _class_;
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Operation uOperation, final org.emftext.language.java.classifiers.Class javaClass) {
      return uOperation;
    }
  }
  
  public SetJavaMethodAbstractRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.SetJavaMethodAbstractRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uOperation = uOperation;
  }
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaMethodAbstractRoutine with input:");
    getLogger().debug("   uOperation: " + this.uOperation);
    
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(uOperation), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaClass == null) {
    	return false;
    }
    registerObjectUnderModification(javaClass);
    org.emftext.language.java.members.ClassMethod javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(uOperation, javaClass), // correspondence source supplier
    	org.emftext.language.java.members.ClassMethod.class,
    	(org.emftext.language.java.members.ClassMethod _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaMethod == null) {
    	return false;
    }
    registerObjectUnderModification(javaMethod);
    // val updatedElement userExecution.getElement1(uOperation, javaClass, javaMethod);
    userExecution.update0Element(uOperation, javaClass, javaMethod);
    
    postprocessElements();
    
    return true;
  }
}
