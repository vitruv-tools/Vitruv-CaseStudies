package mir.routines.umlToJavaMethod;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Type;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaInterfaceMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaInterfaceMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updateJavaMethodElement(final Operation umlOp, final Interface jInterface, final org.emftext.language.java.classifiers.Class customClassType, final InterfaceMethod javaMethod) {
      String _name = umlOp.getName();
      boolean _equals = Objects.equal(_name, null);
      if (_equals) {
        javaMethod.setName("DefaultInterfaceMethodName");
      } else {
        javaMethod.setName(umlOp.getName());
      }
      javaMethod.setTypeReference(UmlToJavaHelper.createTypeReference(umlOp.getType(), customClassType));
    }
    
    public EObject getElement1(final Operation umlOp, final Interface jInterface, final org.emftext.language.java.classifiers.Class customClassType, final InterfaceMethod javaMethod) {
      return umlOp;
    }
    
    public EObject getCorrepondenceSourceCustomClassType(final Operation umlOp, final Interface jInterface) {
      Type _type = umlOp.getType();
      return _type;
    }
    
    public void update0Element(final Operation umlOp, final Interface jInterface, final org.emftext.language.java.classifiers.Class customClassType, final InterfaceMethod javaMethod) {
      EList<Member> _members = jInterface.getMembers();
      _members.add(javaMethod);
    }
    
    public EObject getElement2(final Operation umlOp, final Interface jInterface, final org.emftext.language.java.classifiers.Class customClassType, final InterfaceMethod javaMethod) {
      return javaMethod;
    }
    
    public EObject getElement3(final Operation umlOp, final Interface jInterface, final org.emftext.language.java.classifiers.Class customClassType, final InterfaceMethod javaMethod) {
      return jInterface;
    }
    
    public EObject getCorrepondenceSourceJInterface(final Operation umlOp) {
      org.eclipse.uml2.uml.Interface _interface = umlOp.getInterface();
      return _interface;
    }
  }
  
  public CreateJavaInterfaceMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation umlOp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.CreateJavaInterfaceMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.umlOp = umlOp;
  }
  
  private Operation umlOp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaInterfaceMethodRoutine with input:");
    getLogger().debug("   Operation: " + this.umlOp);
    
    Interface jInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJInterface(umlOp), // correspondence source supplier
    	Interface.class,
    	(Interface _element) -> true, // correspondence precondition checker
    	null);
    if (jInterface == null) {
    	return;
    }
    registerObjectUnderModification(jInterface);
    org.emftext.language.java.classifiers.Class customClassType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCustomClassType(umlOp, jInterface), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    registerObjectUnderModification(customClassType);
    InterfaceMethod javaMethod = MembersFactoryImpl.eINSTANCE.createInterfaceMethod();
    userExecution.updateJavaMethodElement(umlOp, jInterface, customClassType, javaMethod);
    
    addCorrespondenceBetween(userExecution.getElement1(umlOp, jInterface, customClassType, javaMethod), userExecution.getElement2(umlOp, jInterface, customClassType, javaMethod), "");
    
    // val updatedElement userExecution.getElement3(umlOp, jInterface, customClassType, javaMethod);
    userExecution.update0Element(umlOp, jInterface, customClassType, javaMethod);
    
    postprocessElements();
  }
}
