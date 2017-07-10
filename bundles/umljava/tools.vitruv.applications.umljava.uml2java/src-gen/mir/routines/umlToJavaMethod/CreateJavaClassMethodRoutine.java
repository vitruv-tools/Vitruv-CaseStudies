package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.applications.umljava.util.java.JavaModifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaClassMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaClassMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJavaMethodElement(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      String _name = uOperation.getName();
      javaMethod.setName(_name);
      VisibilityKind _visibility = uOperation.getVisibility();
      JavaModifierUtil.setJavaVisibility(javaMethod, _visibility);
      Type _type = uOperation.getType();
      CompilationUnit _containingCompilationUnit = jClassifier.getContainingCompilationUnit();
      TypeReference _createTypeReferenceAndUpdateImport = UmlToJavaHelper.createTypeReferenceAndUpdateImport(_type, customTypeClass, _containingCompilationUnit, this.userInteracting);
      javaMethod.setTypeReference(_createTypeReferenceAndUpdateImport);
    }
    
    public EObject getElement1(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return jClassifier;
    }
    
    public void update0Element(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      EList<Member> _members = jClassifier.getMembers();
      _members.add(javaMethod);
    }
    
    public EObject getCorrepondenceSourceCustomTypeClass(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier) {
      Type _type = uOperation.getType();
      return _type;
    }
    
    public EObject getCorrepondenceSourceJClassifier(final Classifier uClassifier, final Operation uOperation) {
      return uClassifier;
    }
    
    public EObject getElement2(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return uOperation;
    }
    
    public EObject getElement3(final Classifier uClassifier, final Operation uOperation, final ConcreteClassifier jClassifier, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return javaMethod;
    }
  }
  
  public CreateJavaClassMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Classifier uClassifier, final Operation uOperation) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaClassMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uClassifier = uClassifier;this.uOperation = uOperation;
  }
  
  private Classifier uClassifier;
  
  private Operation uOperation;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaClassMethodRoutine with input:");
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
    org.emftext.language.java.classifiers.Class customTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomTypeClass(uClassifier, uOperation, jClassifier), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customTypeClass);
    ClassMethod javaMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    notifyObjectCreated(javaMethod);
    userExecution.updateJavaMethodElement(uClassifier, uOperation, jClassifier, customTypeClass, javaMethod);
    
    // val updatedElement userExecution.getElement1(uClassifier, uOperation, jClassifier, customTypeClass, javaMethod);
    userExecution.update0Element(uClassifier, uOperation, jClassifier, customTypeClass, javaMethod);
    
    addCorrespondenceBetween(userExecution.getElement2(uClassifier, uOperation, jClassifier, customTypeClass, javaMethod), userExecution.getElement3(uClassifier, uOperation, jClassifier, customTypeClass, javaMethod), "");
    
    postprocessElements();
  }
}
