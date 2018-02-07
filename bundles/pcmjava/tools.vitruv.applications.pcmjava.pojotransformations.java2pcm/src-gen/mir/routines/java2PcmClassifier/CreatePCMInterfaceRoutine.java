package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersPackage;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.pojotransformations.java2pcm.Java2PcmHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMInterfaceRoutine extends AbstractRepairRoutineRealization {
  private CreatePCMInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final Interface javaInterface, final CompilationUnit compilationUnit) {
      return ContainersPackage.Literals.PACKAGE;
    }
    
    public void updatePcmIfaceElement(final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository, final org.emftext.language.java.containers.Package contractsPackage, final OperationInterface pcmIface) {
      pcmIface.setEntityName(javaInterface.getName());
    }
    
    public String getRetrieveTag1(final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return "contracts";
    }
    
    public EObject getCorrepondenceSourceContractsPackage(final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository) {
      return pcmRepository;
    }
    
    public void callRoutine1(final Interface javaInterface, final CompilationUnit compilationUnit, final Repository pcmRepository, final org.emftext.language.java.containers.Package contractsPackage, final OperationInterface pcmIface, @Extension final RoutinesFacade _routinesFacade) {
      final org.emftext.language.java.containers.Package javaPackage = Java2PcmHelper.getContainingPackageFromCorrespondenceModel(javaInterface, 
        this.correspondenceModel);
      boolean _equals = javaPackage.getName().equals(contractsPackage.getName());
      if (_equals) {
        _routinesFacade.addcorrespondenceToInterfaceAndUpdateRepository(pcmIface, javaInterface, compilationUnit);
      } else {
        _routinesFacade.createdInterfaceNotInContracts(javaInterface, pcmIface, compilationUnit);
      }
    }
  }
  
  public CreatePCMInterfaceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface javaInterface, final CompilationUnit compilationUnit) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.CreatePCMInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.javaInterface = javaInterface;this.compilationUnit = compilationUnit;
  }
  
  private Interface javaInterface;
  
  private CompilationUnit compilationUnit;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMInterfaceRoutine with input:");
    getLogger().debug("   javaInterface: " + this.javaInterface);
    getLogger().debug("   compilationUnit: " + this.compilationUnit);
    
    org.palladiosimulator.pcm.repository.Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(javaInterface, compilationUnit), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.Repository.class,
    	(org.palladiosimulator.pcm.repository.Repository _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (pcmRepository == null) {
    	return false;
    }
    registerObjectUnderModification(pcmRepository);
    org.emftext.language.java.containers.Package contractsPackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceContractsPackage(javaInterface, compilationUnit, pcmRepository), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(javaInterface, compilationUnit, pcmRepository), 
    	false // asserted
    	);
    if (contractsPackage == null) {
    	return false;
    }
    registerObjectUnderModification(contractsPackage);
    org.palladiosimulator.pcm.repository.OperationInterface pcmIface = org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    notifyObjectCreated(pcmIface);
    userExecution.updatePcmIfaceElement(javaInterface, compilationUnit, pcmRepository, contractsPackage, pcmIface);
    
    userExecution.callRoutine1(javaInterface, compilationUnit, pcmRepository, contractsPackage, pcmIface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
