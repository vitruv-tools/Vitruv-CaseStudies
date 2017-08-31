package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nAdd correspondance between OperationInterface and JavaInterface and CompilationUnit. Also adds OperationInterface into Repository.
 *  
 */
@SuppressWarnings("all")
public class AddCorrespondanceToInterfaceAndUpdateRepositoryRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddCorrespondanceToInterfaceAndUpdateRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return pcmInterface;
    }
    
    public void update0Element(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
      EList<org.palladiosimulator.pcm.repository.Interface> _interfaces__Repository = pcmRepository.getInterfaces__Repository();
      _interfaces__Repository.add(pcmInterface);
    }
    
    public EObject getElement4(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return compilationUnit;
    }
    
    public EObject getElement5(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return pcmRepository;
    }
    
    public EObject getElement2(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return javaInterface;
    }
    
    public EObject getElement3(final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return pcmInterface;
    }
  }
  
  public AddCorrespondanceToInterfaceAndUpdateRepositoryRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final Repository pcmRepository, final Interface javaInterface, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.AddCorrespondanceToInterfaceAndUpdateRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.pcmRepository = pcmRepository;this.javaInterface = javaInterface;this.compilationUnit = compilationUnit;
  }
  
  private OperationInterface pcmInterface;
  
  private Repository pcmRepository;
  
  private Interface javaInterface;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondanceToInterfaceAndUpdateRepositoryRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   pcmRepository: " + this.pcmRepository);
    getLogger().debug("   javaInterface: " + this.javaInterface);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmInterface, pcmRepository, javaInterface, compilationUnit), userExecution.getElement2(pcmInterface, pcmRepository, javaInterface, compilationUnit), "");
    
    addCorrespondenceBetween(userExecution.getElement3(pcmInterface, pcmRepository, javaInterface, compilationUnit), userExecution.getElement4(pcmInterface, pcmRepository, javaInterface, compilationUnit), "");
    
    // val updatedElement userExecution.getElement5(pcmInterface, pcmRepository, javaInterface, compilationUnit);
    userExecution.update0Element(pcmInterface, pcmRepository, javaInterface, compilationUnit);
    
    postprocessElements();
    
    return true;
  }
}
