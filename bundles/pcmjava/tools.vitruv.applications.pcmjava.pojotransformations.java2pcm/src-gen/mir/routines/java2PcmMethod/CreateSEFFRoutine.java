package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.impl.SeffFactoryImpl;
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
    
    public EObject getCorrepondenceSourceBasicComponent(final Method method, final org.emftext.language.java.classifiers.Class cls, final OperationSignature opSignature) {
      return cls;
    }
    
    public EObject getElement1(final Method method, final org.emftext.language.java.classifiers.Class cls, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return cls;
    }
    
    public EObject getElement2(final Method method, final org.emftext.language.java.classifiers.Class cls, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      return rdseff;
    }
    
    public void updateRdseffElement(final Method method, final org.emftext.language.java.classifiers.Class cls, final OperationSignature opSignature, final BasicComponent basicComponent, final ResourceDemandingSEFF rdseff) {
      rdseff.setDescribedService__SEFF(opSignature);
      rdseff.setBasicComponent_ServiceEffectSpecification(basicComponent);
    }
    
    public EObject getCorrepondenceSourceOpSignature(final Method method, final org.emftext.language.java.classifiers.Class cls) {
      return method;
    }
  }
  
  public CreateSEFFRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method method, final org.emftext.language.java.classifiers.Class cls) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreateSEFFRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.cls = cls;
  }
  
  private Method method;
  
  private org.emftext.language.java.classifiers.Class cls;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFRoutine with input:");
    getLogger().debug("   Method: " + this.method);
    getLogger().debug("   Class: " + this.cls);
    
    OperationSignature opSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpSignature(method, cls), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    registerObjectUnderModification(opSignature);
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(method, cls, opSignature), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    registerObjectUnderModification(basicComponent);
    ResourceDemandingSEFF rdseff = SeffFactoryImpl.eINSTANCE.createResourceDemandingSEFF();
    notifyObjectCreated(rdseff);
    userExecution.updateRdseffElement(method, cls, opSignature, basicComponent, rdseff);
    
    addCorrespondenceBetween(userExecution.getElement1(method, cls, opSignature, basicComponent, rdseff), userExecution.getElement2(method, cls, opSignature, basicComponent, rdseff), "");
    
    postprocessElements();
  }
}
