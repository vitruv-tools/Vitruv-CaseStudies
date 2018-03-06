package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
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
    
    public EObject getCorrepondenceSourceBasicComponent(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod, final OperationSignature operationSignature) {
      return javaClass;
    }
    
    public EObject getElement1(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod, final OperationSignature operationSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return classMethod;
    }
    
    public void update0Element(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod, final OperationSignature operationSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      EList<ServiceEffectSpecification> _serviceEffectSpecifications__BasicComponent = basicComponent.getServiceEffectSpecifications__BasicComponent();
      _serviceEffectSpecifications__BasicComponent.add(rdseff);
    }
    
    public EObject getElement2(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod, final OperationSignature operationSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return rdseff;
    }
    
    public void updateRdseffElement(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod, final OperationSignature operationSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      rdseff.setDescribedService__SEFF(operationSignature);
      rdseff.setBasicComponent_ServiceEffectSpecification(basicComponent);
    }
    
    public EObject getElement3(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod, final OperationSignature operationSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return basicComponent;
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod) {
      return javaMethod;
    }
  }
  
  public CreateSEFFRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method javaMethod, final org.emftext.language.java.classifiers.Class javaClass, final ClassMethod classMethod) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateSEFFRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaMethod = javaMethod;this.javaClass = javaClass;this.classMethod = classMethod;
  }
  
  private Method javaMethod;
  
  private org.emftext.language.java.classifiers.Class javaClass;
  
  private ClassMethod classMethod;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFRoutine with input:");
    getLogger().debug("   javaMethod: " + this.javaMethod);
    getLogger().debug("   javaClass: " + this.javaClass);
    getLogger().debug("   classMethod: " + this.classMethod);
    
    org.palladiosimulator.pcm.repository.OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(javaMethod, javaClass, classMethod), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (operationSignature == null) {
    	return false;
    }
    registerObjectUnderModification(operationSignature);
    org.palladiosimulator.pcm.repository.BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(javaMethod, javaClass, classMethod, operationSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.BasicComponent.class,
    	(org.palladiosimulator.pcm.repository.BasicComponent _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (basicComponent == null) {
    	return false;
    }
    registerObjectUnderModification(basicComponent);
    org.palladiosimulator.pcm.seff.ResourceDemandingSEFF rdseff = org.palladiosimulator.pcm.seff.impl.SeffFactoryImpl.eINSTANCE.createResourceDemandingSEFF();
    notifyObjectCreated(rdseff);
    userExecution.updateRdseffElement(javaMethod, javaClass, classMethod, operationSignature, basicComponent, rdseff);
    
    addCorrespondenceBetween(userExecution.getElement1(javaMethod, javaClass, classMethod, operationSignature, basicComponent, rdseff), userExecution.getElement2(javaMethod, javaClass, classMethod, operationSignature, basicComponent, rdseff), "");
    
    // val updatedElement userExecution.getElement3(javaMethod, javaClass, classMethod, operationSignature, basicComponent, rdseff);
    userExecution.update0Element(javaMethod, javaClass, classMethod, operationSignature, basicComponent, rdseff);
    
    postprocessElements();
    
    return true;
  }
}
