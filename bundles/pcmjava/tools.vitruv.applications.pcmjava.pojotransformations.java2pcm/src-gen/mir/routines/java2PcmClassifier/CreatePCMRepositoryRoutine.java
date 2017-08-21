package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePCMRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final EObject sourceElementMappedToRepository, final String packageName, final String newTag, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public String getRetrieveTag1(final EObject sourceElementMappedToRepository, final String packageName, final String newTag) {
      return newTag;
    }
    
    public void updatePcmRepositoryElement(final EObject sourceElementMappedToRepository, final String packageName, final String newTag, final Repository pcmRepository) {
      pcmRepository.setEntityName(packageName);
      String _entityName = pcmRepository.getEntityName();
      String _plus = ("model/" + _entityName);
      String _plus_1 = (_plus + ".repository");
      this.persistProjectRelative(sourceElementMappedToRepository, pcmRepository, _plus_1);
    }
    
    public EObject getElement2(final EObject sourceElementMappedToRepository, final String packageName, final String newTag, final Repository pcmRepository) {
      return sourceElementMappedToRepository;
    }
    
    public String getTag1(final EObject sourceElementMappedToRepository, final String packageName, final String newTag, final Repository pcmRepository) {
      return newTag;
    }
    
    public EObject getCorrepondenceSourcenull(final EObject sourceElementMappedToRepository, final String packageName, final String newTag) {
      return sourceElementMappedToRepository;
    }
  }
  
  public CreatePCMRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject sourceElementMappedToRepository, final String packageName, final String newTag) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreatePCMRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.sourceElementMappedToRepository = sourceElementMappedToRepository;this.packageName = packageName;this.newTag = newTag;
  }
  
  private EObject sourceElementMappedToRepository;
  
  private String packageName;
  
  private String newTag;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMRepositoryRoutine with input:");
    getLogger().debug("   EObject: " + this.sourceElementMappedToRepository);
    getLogger().debug("   String: " + this.packageName);
    getLogger().debug("   String: " + this.newTag);
    
    if (getCorrespondingElement(
    	userExecution.getCorrepondenceSourcenull(sourceElementMappedToRepository, packageName, newTag), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(sourceElementMappedToRepository, packageName, newTag)) != null) {
    	return;
    }
    Repository pcmRepository = RepositoryFactoryImpl.eINSTANCE.createRepository();
    notifyObjectCreated(pcmRepository);
    userExecution.updatePcmRepositoryElement(sourceElementMappedToRepository, packageName, newTag, pcmRepository);
    
    addCorrespondenceBetween(userExecution.getElement1(sourceElementMappedToRepository, packageName, newTag, pcmRepository), userExecution.getElement2(sourceElementMappedToRepository, packageName, newTag, pcmRepository), userExecution.getTag1(sourceElementMappedToRepository, packageName, newTag, pcmRepository));
    
    postprocessElements();
  }
}
