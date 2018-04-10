package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersPackage;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * *nAdd correspondence between OperationInterface and JavaInterface and CompilationUnit. Also adds OperationInterface into Repository.
 *  
 */
@SuppressWarnings("all")
public class AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine extends AbstractRepairRoutineRealization {
  private AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return pcmInterface;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public void update0Element(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      EList<org.palladiosimulator.pcm.repository.Interface> _interfaces__Repository = pcmRepository.getInterfaces__Repository();
      _interfaces__Repository.add(pcmInterface);
    }
    
    public EObject getElement4(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return compilationUnit;
    }
    
    public EObject getElement5(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public EObject getElement2(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return javaInterface;
    }
    
    public EObject getElement3(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return pcmInterface;
    }
  }
  
  public AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.javaInterface = javaInterface;this.compilationUnit = compilationUnit;
  }
  
  private OperationInterface pcmInterface;
  
  private Interface javaInterface;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddcorrespondenceToInterfaceAndUpdateRepositoryRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   javaInterface: " + this.javaInterface);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(pcmInterface, javaInterface, compilationUnit), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    addCorrespondenceBetween(userExecution.getElement1(pcmInterface, javaInterface, compilationUnit, pcmRepository), userExecution.getElement2(pcmInterface, javaInterface, compilationUnit, pcmRepository), "");
    
    addCorrespondenceBetween(userExecution.getElement3(pcmInterface, javaInterface, compilationUnit, pcmRepository), userExecution.getElement4(pcmInterface, javaInterface, compilationUnit, pcmRepository), "");
    
    // val updatedElement userExecution.getElement5(pcmInterface, javaInterface, compilationUnit, pcmRepository);
    userExecution.update0Element(pcmInterface, javaInterface, compilationUnit, pcmRepository);
    
    postprocessElements();
    
    return true;
  }
}
