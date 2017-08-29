package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSEFFForClassMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateSEFFForClassMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      return seff;
    }
    
    public void update0Element(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      basicComponent.getServiceEffectSpecifications__BasicComponent().add(seff);
    }
    
    public EObject getElement2(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      return classMethod;
    }
    
    public EObject getElement3(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      return basicComponent;
    }
    
    public void updateSeffElement(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod, final ResourceDemandingSEFF seff) {
      seff.setDescribedService__SEFF(opSignature);
    }
  }
  
  public CreateSEFFForClassMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreateSEFFForClassMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.basicComponent = basicComponent;this.opSignature = opSignature;this.classMethod = classMethod;
  }
  
  private BasicComponent basicComponent;
  
  private OperationSignature opSignature;
  
  private ClassMethod classMethod;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSEFFForClassMethodRoutine with input:");
    getLogger().debug("   basicComponent: " + this.basicComponent);
    getLogger().debug("   opSignature: " + this.opSignature);
    getLogger().debug("   classMethod: " + this.classMethod);
    
    org.palladiosimulator.pcm.seff.ResourceDemandingSEFF seff = org.palladiosimulator.pcm.seff.impl.SeffFactoryImpl.eINSTANCE.createResourceDemandingSEFF();
    notifyObjectCreated(seff);
    userExecution.updateSeffElement(basicComponent, opSignature, classMethod, seff);
    
    addCorrespondenceBetween(userExecution.getElement1(basicComponent, opSignature, classMethod, seff), userExecution.getElement2(basicComponent, opSignature, classMethod, seff), "");
    
    // val updatedElement userExecution.getElement3(basicComponent, opSignature, classMethod, seff);
    userExecution.update0Element(basicComponent, opSignature, classMethod, seff);
    
    postprocessElements();
    
    return true;
  }
}
