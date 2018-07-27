package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import tools.vitruv.applications.pcmjava.util.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSEFFRoutine extends AbstractRepairRoutineRealization {
  private CreateSEFFRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      return classMethod;
    }
    
    public void update0Element(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      ClassMethod correspondingClassMethod = Pcm2JavaHelper.findMethodInClass(componentClass, classMethod);
      if ((null == correspondingClassMethod)) {
        componentClass.getMembers().add(classMethod);
        correspondingClassMethod = classMethod;
      } else {
        correspondingClassMethod.setName(interfaceMethod.getName());
      }
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass) {
      Signature _describedService__SEFF = seff.getDescribedService__SEFF();
      return _describedService__SEFF;
    }
    
    public EObject getCorrepondenceSourceComponentClass(final ServiceEffectSpecification seff) {
      BasicComponent _basicComponent_ServiceEffectSpecification = seff.getBasicComponent_ServiceEffectSpecification();
      return _basicComponent_ServiceEffectSpecification;
    }
    
    public EObject getElement2(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      return seff;
    }
    
    public void updateClassMethodElement(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      Pcm2JavaHelper.initializeClassMethod(classMethod, interfaceMethod, true);
    }
    
    public EObject getElement3(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod, final ClassMethod classMethod) {
      return componentClass;
    }
    
    public boolean checkMatcherPrecondition1(final ServiceEffectSpecification seff, final org.emftext.language.java.classifiers.Class componentClass, final InterfaceMethod interfaceMethod) {
      Signature _describedService__SEFF = seff.getDescribedService__SEFF();
      return (_describedService__SEFF instanceof OperationSignature);
    }
  }
  
  public CreateSEFFRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ServiceEffectSpecification seff) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.CreateSEFFRoutine.ActionUserExecution(getExecutionState(), this);
    this.seff = seff;
  }
  
  private ServiceEffectSpecification seff;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFRoutine with input:");
    getLogger().debug("   seff: " + this.seff);
    
    org.emftext.language.java.classifiers.Class componentClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceComponentClass(seff), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (componentClass == null) {
    	return false;
    }
    registerObjectUnderModification(componentClass);
    org.emftext.language.java.members.InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(seff, componentClass), // correspondence source supplier
    	org.emftext.language.java.members.InterfaceMethod.class,
    	(org.emftext.language.java.members.InterfaceMethod _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (interfaceMethod == null) {
    	return false;
    }
    registerObjectUnderModification(interfaceMethod);
    if (!userExecution.checkMatcherPrecondition1(seff, componentClass, interfaceMethod)) {
    	return false;
    }
    org.emftext.language.java.members.ClassMethod classMethod = org.emftext.language.java.members.impl.MembersFactoryImpl.eINSTANCE.createClassMethod();
    notifyObjectCreated(classMethod);
    userExecution.updateClassMethodElement(seff, componentClass, interfaceMethod, classMethod);
    
    addCorrespondenceBetween(userExecution.getElement1(seff, componentClass, interfaceMethod, classMethod), userExecution.getElement2(seff, componentClass, interfaceMethod, classMethod), "");
    
    // val updatedElement userExecution.getElement3(seff, componentClass, interfaceMethod, classMethod);
    userExecution.update0Element(seff, componentClass, interfaceMethod, classMethod);
    
    postprocessElements();
    
    return true;
  }
}
