package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJavaMethodElement(final Classifier uClassifier, final Operation umlOp, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      javaMethod.setName(umlOp.getName());
      UmlToJavaHelper.setJavaVisibility(javaMethod, umlOp.getVisibility());
      javaMethod.setTypeReference(UmlToJavaHelper.createTypeReference(umlOp.getType(), customTypeClass));
    }
    
    public EObject getElement1(final Classifier uClassifier, final Operation umlOp, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return jClassifier;
    }
    
    public void update0Element(final Classifier uClassifier, final Operation umlOp, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      EList<Member> _members = jClassifier.getMembers();
      _members.add(javaMethod);
    }
    
    public EObject getCorrepondenceSourceCustomTypeClass(final Classifier uClassifier, final Operation umlOp, final ConcreteClassifier jClassifier) {
      Type _type = umlOp.getType();
      return _type;
    }
    
    public EObject getCorrepondenceSourceJClassifier(final Classifier uClassifier, final Operation umlOp) {
      return uClassifier;
    }
    
    public EObject getElement2(final Classifier uClassifier, final Operation umlOp, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return umlOp;
    }
    
    public EObject getElement3(final Classifier uClassifier, final Operation umlOp, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return javaMethod;
    }
  }
  
  public CreateJavaMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier uClassifier, final Operation umlOp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uClassifier = uClassifier;this.umlOp = umlOp;
  }
  
  private Classifier uClassifier;
  
  private Operation umlOp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaMethodRoutine with input:");
    getLogger().debug("   Classifier: " + this.uClassifier);
    getLogger().debug("   Operation: " + this.umlOp);
    
    ConcreteClassifier jClassifier = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJClassifier(uClassifier, umlOp), // correspondence source supplier
    	ConcreteClassifier.class,
    	(ConcreteClassifier _element) -> true, // correspondence precondition checker
    	null);
    if (jClassifier == null) {
    	return;
    }
    registerObjectUnderModification(jClassifier);
    org.emftext.language.java.classifiers.Class customTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomTypeClass(uClassifier, umlOp, jClassifier), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customTypeClass);
    ClassMethod javaMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    userExecution.updateJavaMethodElement(uClassifier, umlOp, jClassifier, customTypeClass, javaMethod);
    
    // val updatedElement userExecution.getElement1(uClassifier, umlOp, jClassifier, customTypeClass, javaMethod);
    userExecution.update0Element(uClassifier, umlOp, jClassifier, customTypeClass, javaMethod);
    
    addCorrespondenceBetween(userExecution.getElement2(uClassifier, umlOp, jClassifier, customTypeClass, javaMethod), userExecution.getElement3(uClassifier, umlOp, jClassifier, customTypeClass, javaMethod), "");
    
    postprocessElements();
  }
}
