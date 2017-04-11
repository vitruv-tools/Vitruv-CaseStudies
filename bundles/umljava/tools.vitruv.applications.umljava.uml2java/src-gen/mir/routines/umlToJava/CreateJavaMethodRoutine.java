package mir.routines.umlToJava;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJava.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.VisibilityKind;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.emftext.language.java.modifiers.ModifiersFactory;
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
    
    public void updateJavaMethodElement(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      String _name = umlOp.getName();
      boolean _equals = Objects.equal(_name, null);
      if (_equals) {
        javaMethod.setName("DefaultMethodName");
      } else {
        javaMethod.setName(umlOp.getName());
      }
      if (((!Objects.equal(umlOp.getVisibility(), null)) && (!Objects.equal(umlOp.getVisibility(), VisibilityKind.PACKAGE_LITERAL)))) {
        UmlToJavaHelper.setJavaVisibility(javaMethod, umlOp.getVisibility());
      }
      boolean _isStatic = umlOp.isStatic();
      if (_isStatic) {
        javaMethod.addModifier(ModifiersFactory.eINSTANCE.createStatic());
      }
      boolean _isAbstract = umlOp.isAbstract();
      if (_isAbstract) {
        javaMethod.addModifier(ModifiersFactory.eINSTANCE.createAbstract());
      }
      javaMethod.setTypeReference(UmlToJavaHelper.createTypeReference(umlOp.getType(), customTypeClass));
    }
    
    public EObject getElement1(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return javaClass;
    }
    
    public void update0Element(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      EList<Member> _members = javaClass.getMembers();
      _members.add(javaMethod);
      this.getLogger().info(((("Correspondence Added: " + umlOp) + ", ") + javaMethod));
    }
    
    public EObject getCorrepondenceSourceJavaClass(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp) {
      return uClass;
    }
    
    public EObject getCorrepondenceSourceCustomTypeClass(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass) {
      Type _type = umlOp.getType();
      return _type;
    }
    
    public EObject getElement2(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return umlOp;
    }
    
    public EObject getElement3(final org.eclipse.uml2.uml.Class uClass, final Operation umlOp, final org.emftext.language.java.classifiers.Class javaClass, final org.emftext.language.java.classifiers.Class customTypeClass, final ClassMethod javaMethod) {
      return javaMethod;
    }
  }
  
  public CreateJavaMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.eclipse.uml2.uml.Class uClass, final Operation umlOp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJava.CreateJavaMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJava.RoutinesFacade(getExecutionState(), this);
    this.uClass = uClass;this.umlOp = umlOp;
  }
  
  private org.eclipse.uml2.uml.Class uClass;
  
  private Operation umlOp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaMethodRoutine with input:");
    getLogger().debug("   Class: " + this.uClass);
    getLogger().debug("   Operation: " + this.umlOp);
    
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(uClass, umlOp), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    registerObjectUnderModification(javaClass);
    org.emftext.language.java.classifiers.Class customTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomTypeClass(uClass, umlOp, javaClass), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customTypeClass);
    ClassMethod javaMethod = MembersFactoryImpl.eINSTANCE.createClassMethod();
    userExecution.updateJavaMethodElement(uClass, umlOp, javaClass, customTypeClass, javaMethod);
    
    // val updatedElement userExecution.getElement1(uClass, umlOp, javaClass, customTypeClass, javaMethod);
    userExecution.update0Element(uClass, umlOp, javaClass, customTypeClass, javaMethod);
    
    addCorrespondenceBetween(userExecution.getElement2(uClass, umlOp, javaClass, customTypeClass, javaMethod), userExecution.getElement3(uClass, umlOp, javaClass, customTypeClass, javaMethod), "");
    
    postprocessElements();
  }
}
