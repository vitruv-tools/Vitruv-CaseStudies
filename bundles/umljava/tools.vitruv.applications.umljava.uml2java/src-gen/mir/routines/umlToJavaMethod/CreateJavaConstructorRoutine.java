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
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaConstructorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
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
      UmlToJavaHelper.setJavaVisibility(jConstructor, uOperation.getVisibility());
    }
  }
  
  public CreateJavaConstructorRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier uClassifier, final Operation uOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaConstructorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uClassifier = uClassifier;this.uOperation = uOperation;
  }
  
  private Classifier uClassifier;
  
  private Operation uOperation;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaConstructorRoutine with input:");
    getLogger().debug("   Classifier: " + this.uClassifier);
    getLogger().debug("   Operation: " + this.uOperation);
    
    ConcreteClassifier jClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClassifier(uClassifier, uOperation), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (jClassifier == null) {
    	return;
    }
    registerObjectUnderModification(jClassifier);
    Constructor jConstructor = MembersFactoryImpl.eINSTANCE.createConstructor();
    userExecution.updateJConstructorElement(uClassifier, uOperation, jClassifier, jConstructor);
    
    // val updatedElement userExecution.getElement1(uClassifier, uOperation, jClassifier, jConstructor);
    userExecution.update0Element(uClassifier, uOperation, jClassifier, jConstructor);
    
    addCorrespondenceBetween(userExecution.getElement2(uClassifier, uOperation, jClassifier, jConstructor), userExecution.getElement3(uClassifier, uOperation, jClassifier, jConstructor), "");
    
    postprocessElements();
  }
}
