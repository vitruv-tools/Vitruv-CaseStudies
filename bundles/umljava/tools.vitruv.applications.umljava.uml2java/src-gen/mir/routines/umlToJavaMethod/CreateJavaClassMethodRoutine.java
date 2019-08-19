package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import tools.vitruv.applications.umljava.util.java.JavaModifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaClassMethodRoutine extends AbstractRepairRoutineRealization {
  private CreateJavaClassMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJavaMethodElement(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final ClassMethod javaMethod) {
      javaMethod.setName(uOperation.getName());
      JavaModifierUtil.setJavaVisibility(javaMethod, uOperation.getVisibility());
    }
    
    public EObject getElement1(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final ClassMethod javaMethod) {
      return jClassifier;
    }
    
    public void update0Element(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final ClassMethod javaMethod) {
      EList<Member> _members = jClassifier.getMembers();
      _members.add(javaMethod);
    }
    
    public EObject getCorrepondenceSource1(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier) {
      return uOperation;
    }
    
    public EObject getCorrepondenceSourceJClassifier(final Classifier uClassifier, final Operation uOperation) {
      return uClassifier;
    }
    
    public EObject getElement2(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final ClassMethod javaMethod) {
      return uOperation;
    }
    
    public EObject getElement3(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final ClassMethod javaMethod) {
      return javaMethod;
    }
    
    public void callRoutine1(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final ClassMethod javaMethod, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.setJavaMethodReturnType(uOperation, uOperation.getReturnResult());
    }
  }
  
  public CreateJavaClassMethodRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier uClassifier, final Operation uOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.uClassifier = uClassifier;this.uOperation = uOperation;
  }
  
  private Classifier uClassifier;
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaClassMethodRoutine with input:");
    getLogger().debug("   uClassifier: " + this.uClassifier);
    getLogger().debug("   uOperation: " + this.uOperation);
    
    org.emftext.language.java.classifiers.ConcreteClassifier jClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClassifier(uClassifier, uOperation), // correspondence source supplier
    	org.emftext.language.java.classifiers.ConcreteClassifier.class,
    	(org.emftext.language.java.classifiers.ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (jClassifier == null) {
    	return false;
    }
    registerObjectUnderModification(jClassifier);
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(uClassifier, uOperation, jClassifier), // correspondence source supplier
    	org.emftext.language.java.members.ClassMethod.class,
    	(org.emftext.language.java.members.ClassMethod _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    org.emftext.language.java.members.ClassMethod javaMethod = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createClassMethod();
    notifyObjectCreated(javaMethod);
    userExecution.updateJavaMethodElement(uClassifier, uOperation, jClassifier, javaMethod);
    
    // val updatedElement userExecution.getElement1(uClassifier, uOperation, jClassifier, javaMethod);
    userExecution.update0Element(uClassifier, uOperation, jClassifier, javaMethod);
    
    addCorrespondenceBetween(userExecution.getElement2(uClassifier, uOperation, jClassifier, javaMethod), userExecution.getElement3(uClassifier, uOperation, jClassifier, javaMethod), "");
    
    userExecution.callRoutine1(uClassifier, uOperation, jClassifier, javaMethod, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
