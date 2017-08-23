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
  private RoutinesFacade actionsFacade;
  
  private CreateSEFFRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod, final OperationSignature opSignature) {
      return cls;
    }
    
    public EObject getElement1(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return classMethod;
    }
    
    public void update0Element(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      EList<ServiceEffectSpecification> _serviceEffectSpecifications__BasicComponent = basicComponent.getServiceEffectSpecifications__BasicComponent();
      _serviceEffectSpecifications__BasicComponent.add(rdseff);
    }
    
    public EObject getElement2(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return rdseff;
    }
    
    public void updateRdseffElement(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      rdseff.setDescribedService__SEFF(opSignature);
      rdseff.setBasicComponent_ServiceEffectSpecification(basicComponent);
    }
    
    public EObject getElement3(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return basicComponent;
    }
    
    public EObject getCorrepondenceSourceOpSignature(final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod) {
      return method;
    }
  }
  
  public CreateSEFFRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method method, final org.emftext.language.java.classifiers.Class cls, final ClassMethod classMethod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateSEFFRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.cls = cls;this.classMethod = classMethod;
  }
  
  private Method method;
  
  private org.emftext.language.java.classifiers.Class cls;
  
  private ClassMethod classMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFRoutine with input:");
    getLogger().debug("   method: " + this.method);
    getLogger().debug("   cls: " + this.cls);
    getLogger().debug("   classMethod: " + this.classMethod);
    
    org.palladiosimulator.pcm.repository.OperationSignature opSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpSignature(method, cls, classMethod), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationSignature.class,
    	(org.palladiosimulator.pcm.repository.OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    registerObjectUnderModification(opSignature);
    org.palladiosimulator.pcm.repository.BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(method, cls, classMethod, opSignature), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.BasicComponent.class,
    	(org.palladiosimulator.pcm.repository.BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    registerObjectUnderModification(basicComponent);
    org.palladiosimulator.pcm.seff.ResourceDemandingSEFF rdseff = org.palladiosimulator.pcm.seff.impl.SeffFactoryImpl.eINSTANCE.createResourceDemandingSEFF();
    notifyObjectCreated(rdseff);
    userExecution.updateRdseffElement(method, cls, classMethod, opSignature, basicComponent, rdseff);
    
    addCorrespondenceBetween(userExecution.getElement1(method, cls, classMethod, opSignature, basicComponent, rdseff), userExecution.getElement2(method, cls, classMethod, opSignature, basicComponent, rdseff), "");
    
    // val updatedElement userExecution.getElement3(method, cls, classMethod, opSignature, basicComponent, rdseff);
    userExecution.update0Element(method, cls, classMethod, opSignature, basicComponent, rdseff);
    
    postprocessElements();
  }
}
