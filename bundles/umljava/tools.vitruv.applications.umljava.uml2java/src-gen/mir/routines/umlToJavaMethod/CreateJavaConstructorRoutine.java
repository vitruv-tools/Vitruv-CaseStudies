package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Member;
import tools.vitruv.applications.umljava.util.java.JavaModifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaConstructorRoutine extends AbstractRepairRoutineRealization {
  private CreateJavaConstructorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final Constructor jConstructor) {
      return jClassifier;
    }
    
    public void update0Element(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final Constructor jConstructor) {
      EList<Member> _members = jClassifier.getMembers();
      _members.add(jConstructor);
    }
    
    public EObject getCorrepondenceSource1(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier) {
      return uOperation;
    }
    
    public EObject getCorrepondenceSourceJClassifier(final Classifier uClassifier, final Operation uOperation) {
      return uClassifier;
    }
    
    public EObject getElement2(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final Constructor jConstructor) {
      return uOperation;
    }
    
    public EObject getElement3(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final Constructor jConstructor) {
      return jConstructor;
    }
    
    public void updateJConstructorElement(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final Constructor jConstructor) {
      jConstructor.setName(uOperation.getName());
      JavaModifierUtil.setJavaVisibility(jConstructor, uOperation.getVisibility());
    }
  }
  
  public CreateJavaConstructorRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier uClassifier, final Operation uOperation) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.uClassifier = uClassifier;this.uOperation = uOperation;
  }
  
  private Classifier uClassifier;
  
  private Operation uOperation;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaConstructorRoutine with input:");
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
    	org.emftext.language.java.members.Constructor.class,
    	(org.emftext.language.java.members.Constructor _element) -> true, // correspondence precondition checker
    	null
    ).isEmpty()) {
    	return false;
    }
    org.emftext.language.java.members.Constructor jConstructor = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createConstructor();
    notifyObjectCreated(jConstructor);
    userExecution.updateJConstructorElement(uClassifier, uOperation, jClassifier, jConstructor);
    
    // val updatedElement userExecution.getElement1(uClassifier, uOperation, jClassifier, jConstructor);
    userExecution.update0Element(uClassifier, uOperation, jClassifier, jConstructor);
    
    addCorrespondenceBetween(userExecution.getElement2(uClassifier, uOperation, jClassifier, jConstructor), userExecution.getElement3(uClassifier, uOperation, jClassifier, jConstructor), "");
    
    postprocessElements();
    
    return true;
  }
}
