package mir.routines.umlToJavaMethod;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.members.Method;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaMethodReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaMethodReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Operation uOperation, final Method javaMethod, final Optional<org.emftext.language.java.classifiers.Class> returnType) {
      return javaMethod;
    }
    
    public void update0Element(final Operation uOperation, final Method javaMethod, final Optional<org.emftext.language.java.classifiers.Class> returnType) {
      javaMethod.setTypeReference(UmlToJavaHelper.createTypeReferenceAndUpdateImport(uOperation.getType(), returnType, javaMethod.getContainingCompilationUnit(), this.userInteracting));
    }
    
    public EObject getCorrepondenceSourceJavaMethod(final Operation uOperation) {
      return uOperation;
    }
    
    public EObject getCorrepondenceSourceReturnType(final Operation uOperation, final Method javaMethod) {
      Type _type = uOperation.getType();
      return _type;
    }
  }
  
  public SetJavaMethodReturnTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.SetJavaMethodReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uOperation = uOperation;
  }
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaMethodReturnTypeRoutine with input:");
    getLogger().debug("   uOperation: " + this.uOperation);
    
    org.emftext.language.java.members.Method javaMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaMethod(uOperation), // correspondence source supplier
    	org.emftext.language.java.members.Method.class,
    	(org.emftext.language.java.members.Method _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaMethod == null) {
    	return false;
    }
    registerObjectUnderModification(javaMethod);
    	Optional<org.emftext.language.java.classifiers.Class> returnType = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceReturnType(uOperation, javaMethod), // correspondence source supplier
    		org.emftext.language.java.classifiers.Class.class,
    		(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    		null, 
    		false // asserted
    		)
    );
    registerObjectUnderModification(returnType.isPresent() ? returnType.get() : null);
    // val updatedElement userExecution.getElement1(uOperation, javaMethod, returnType);
    userExecution.update0Element(uOperation, javaMethod, returnType);
    
    postprocessElements();
    
    return true;
  }
}
