package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateBasicComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateBasicComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final BasicComponent pcmBasicComponent) {
      return pcmBasicComponent;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final BasicComponent pcmBasicComponent) {
      return javaPackage;
    }
    
    public void updatePcmBasicComponentElement(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final BasicComponent pcmBasicComponent) {
      pcmBasicComponent.setEntityName(javaPackage.getName());
    }
  }
  
  public CreateBasicComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreateBasicComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateBasicComponentRoutine with input:");
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
    BasicComponent pcmBasicComponent = RepositoryFactoryImpl.eINSTANCE.createBasicComponent();
    userExecution.updatePcmBasicComponentElement(javaPackage, pcmRepository, pcmBasicComponent);
    
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, pcmRepository, pcmBasicComponent), userExecution.getElement2(javaPackage, pcmRepository, pcmBasicComponent), "");
    
    postprocessElements();
  }
}
