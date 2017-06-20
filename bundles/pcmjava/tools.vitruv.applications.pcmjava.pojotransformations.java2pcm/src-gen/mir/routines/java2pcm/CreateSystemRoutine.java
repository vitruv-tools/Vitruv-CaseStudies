package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.impl.SystemFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateSystemRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateSystemRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return pcmSystem;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public void updatePcmSystemElement(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final org.palladiosimulator.pcm.system.System pcmSystem) {
      pcmSystem.setEntityName(javaPackage.getName());
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final org.palladiosimulator.pcm.system.System pcmSystem) {
      return javaPackage;
    }
  }
  
  public CreateSystemRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreateSystemRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateSystemRoutine with input:");
    getLogger().debug("   Package: " + this.javaPackage);
    
    Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(javaPackage), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	null);
    if (pcmRepository == null) {
    	return;
    }
    registerObjectUnderModification(pcmRepository);
    org.palladiosimulator.pcm.system.System pcmSystem = SystemFactoryImpl.eINSTANCE.createSystem();
    userExecution.updatePcmSystemElement(javaPackage, pcmRepository, pcmSystem);
    
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, pcmRepository, pcmSystem), userExecution.getElement2(javaPackage, pcmRepository, pcmSystem), "");
    
    postprocessElements();
  }
}
