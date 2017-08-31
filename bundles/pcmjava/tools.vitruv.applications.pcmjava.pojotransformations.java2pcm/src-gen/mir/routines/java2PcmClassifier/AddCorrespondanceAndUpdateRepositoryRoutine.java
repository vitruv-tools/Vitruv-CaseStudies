package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
public class AddCorrespondanceAndUpdateRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddCorrespondanceAndUpdateRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final ImplementationComponentType pcmComponent, final Repository pcmRepository, final org.emftext.language.java.containers.Package javaPackage) {
      return pcmComponent;
    }
    
    public void update0Element(final ImplementationComponentType pcmComponent, final Repository pcmRepository, final org.emftext.language.java.containers.Package javaPackage) {
      EList<RepositoryComponent> _components__Repository = pcmRepository.getComponents__Repository();
      _components__Repository.add(pcmComponent);
    }
    
    public EObject getElement2(final ImplementationComponentType pcmComponent, final Repository pcmRepository, final org.emftext.language.java.containers.Package javaPackage) {
      return javaPackage;
    }
    
    public EObject getElement3(final ImplementationComponentType pcmComponent, final Repository pcmRepository, final org.emftext.language.java.containers.Package javaPackage) {
      return pcmRepository;
    }
  }
  
  public AddCorrespondanceAndUpdateRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final ImplementationComponentType pcmComponent, final Repository pcmRepository, final org.emftext.language.java.containers.Package javaPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.AddCorrespondanceAndUpdateRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.pcmComponent = pcmComponent;this.pcmRepository = pcmRepository;this.javaPackage = javaPackage;
  }
  
  private ImplementationComponentType pcmComponent;
  
  private Repository pcmRepository;
  
  private org.emftext.language.java.containers.Package javaPackage;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondanceAndUpdateRepositoryRoutine with input:");
    getLogger().debug("   pcmComponent: " + this.pcmComponent);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    getLogger().debug("   javaPackage: " + this.javaPackage);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmComponent, pcmRepository, javaPackage), userExecution.getElement2(pcmComponent, pcmRepository, javaPackage), "");
    
    // val updatedElement userExecution.getElement3(pcmComponent, pcmRepository, javaPackage);
    userExecution.update0Element(pcmComponent, pcmRepository, javaPackage);
    
    postprocessElements();
    
    return true;
  }
}
