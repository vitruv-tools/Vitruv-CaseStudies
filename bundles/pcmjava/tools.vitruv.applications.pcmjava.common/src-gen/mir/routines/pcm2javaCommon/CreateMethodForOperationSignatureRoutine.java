package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateMethodForOperationSignatureRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateMethodForOperationSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationSignature operationSignature, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
      return interfaceMethod;
    }
    
    public EObject getCorrepondenceSourceJavaInterface(final OperationSignature operationSignature) {
      OperationInterface _interface__OperationSignature = operationSignature.getInterface__OperationSignature();
      return _interface__OperationSignature;
    }
    
    public EObject getElement2(final OperationSignature operationSignature, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
      return operationSignature;
    }
    
    public void callRoutine1(final OperationSignature operationSignature, final Interface javaInterface, final InterfaceMethod interfaceMethod, @Extension final RoutinesFacade _routinesFacade) {
      interfaceMethod.setName(operationSignature.getEntityName());
      _routinesFacade.changeInterfaceMethodReturnType(interfaceMethod, operationSignature.getReturnType__OperationSignature());
      EList<Member> _members = javaInterface.getMembers();
      _members.add(interfaceMethod);
    }
  }
  
  public CreateMethodForOperationSignatureRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateMethodForOperationSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2javaCommon.RoutinesFacade(getExecutionState(), this);
    this.operationSignature = operationSignature;
  }
  
  private OperationSignature operationSignature;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateMethodForOperationSignatureRoutine with input:");
    getLogger().debug("   operationSignature: " + this.operationSignature);
    
    org.emftext.language.java.classifiers.Interface javaInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaInterface(operationSignature), // correspondence source supplier
    	org.emftext.language.java.classifiers.Interface.class,
    	(org.emftext.language.java.classifiers.Interface _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (javaInterface == null) {
    	return false;
    }
    registerObjectUnderModification(javaInterface);
    org.emftext.language.java.members.InterfaceMethod interfaceMethod = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createInterfaceMethod();
    notifyObjectCreated(interfaceMethod);
    
    addCorrespondenceBetween(userExecution.getElement1(operationSignature, javaInterface, interfaceMethod), userExecution.getElement2(operationSignature, javaInterface, interfaceMethod), "");
    
    userExecution.callRoutine1(operationSignature, javaInterface, interfaceMethod, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
