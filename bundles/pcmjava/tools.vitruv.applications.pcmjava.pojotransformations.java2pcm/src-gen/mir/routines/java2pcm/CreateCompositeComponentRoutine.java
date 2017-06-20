package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeComponent;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateCompositeComponentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateCompositeComponentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void updatePcmCompositeComponentElement(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final CompositeComponent pcmCompositeComponent) {
      pcmCompositeComponent.setEntityName(javaPackage.getName());
    }
    
    public EObject getElement1(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final CompositeComponent pcmCompositeComponent) {
      return pcmCompositeComponent;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public EObject getElement2(final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository, final CompositeComponent pcmCompositeComponent) {
      return javaPackage;
    }
  }
  
  public CreateCompositeComponentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final org.emftext.language.java.containers.Package javaPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreateCompositeComponentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.javaPackage = javaPackage;
  }
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateCompositeComponentRoutine with input:");
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
    CompositeComponent pcmCompositeComponent = RepositoryFactoryImpl.eINSTANCE.createCompositeComponent();
    userExecution.updatePcmCompositeComponentElement(javaPackage, pcmRepository, pcmCompositeComponent);
    
    addCorrespondenceBetween(userExecution.getElement1(javaPackage, pcmRepository, pcmCompositeComponent), userExecution.getElement2(javaPackage, pcmRepository, pcmCompositeComponent), "");
    
    postprocessElements();
  }
}
