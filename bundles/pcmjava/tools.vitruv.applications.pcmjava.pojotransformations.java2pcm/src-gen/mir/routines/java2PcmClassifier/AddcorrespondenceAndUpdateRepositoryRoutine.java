package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.ContainersPackage;
import org.palladiosimulator.pcm.repository.ImplementationComponentType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nAdds correspondence between component and package and add component into repository.
 *  
 */
@SuppressWarnings("all")
public class AddcorrespondenceAndUpdateRepositoryRoutine extends AbstractRepairRoutineRealization {
  private AddcorrespondenceAndUpdateRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository) {
      return pcmComponent;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public void update0Element(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository) {
      EList<RepositoryComponent> _components__Repository = pcmRepository.getComponents__Repository();
      _components__Repository.add(pcmComponent);
    }
    
    public EObject getElement2(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository) {
      return javaPackage;
    }
    
    public EObject getElement3(final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage, final Repository pcmRepository) {
      return pcmRepository;
    }
  }
  
  public AddcorrespondenceAndUpdateRepositoryRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ImplementationComponentType pcmComponent, final org.emftext.language.java.containers.Package javaPackage) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.AddcorrespondenceAndUpdateRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.javaPackage = javaPackage;
  }
  
  private ImplementationComponentType pcmComponent;
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddcorrespondenceAndUpdateRepositoryRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   javaPackage: " + this.javaPackage);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(pcmComponent, javaPackage), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, javaPackage, pcmRepository), userExecution.getElement2(pcmComponent, javaPackage, pcmRepository), "");
    
    // val updatedElement userExecution.getElement3(pcmComponent, javaPackage, pcmRepository);
    userExecution.update0Element(pcmComponent, javaPackage, pcmRepository);
    
    postprocessElements();
    
    return true;
  }
}
