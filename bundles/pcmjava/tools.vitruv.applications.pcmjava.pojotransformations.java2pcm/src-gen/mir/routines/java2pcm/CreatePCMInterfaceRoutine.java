package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMInterfaceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePCMInterfaceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface javaIface, final org.emftext.language.java.containers.Package ifacePackage, final Repository pcmRepository, final OperationInterface pcmIface) {
      return pcmIface;
    }
    
    public EObject getCorrepondenceSourcePcmRepository(final Interface javaIface, final org.emftext.language.java.containers.Package ifacePackage) {
      return ifacePackage;
    }
    
    public EObject getCorrepondenceSourceIfacePackage(final Interface javaIface) {
      return javaIface;
    }
    
    public void updatePcmIfaceElement(final Interface javaIface, final org.emftext.language.java.containers.Package ifacePackage, final Repository pcmRepository, final OperationInterface pcmIface) {
      pcmIface.setEntityName(javaIface.getName());
    }
    
    public String getRetrieveTag1(final Interface javaIface) {
      return "contracts";
    }
    
    public EObject getElement2(final Interface javaIface, final org.emftext.language.java.containers.Package ifacePackage, final Repository pcmRepository, final OperationInterface pcmIface) {
      return javaIface;
    }
  }
  
  public CreatePCMInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface javaIface) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreatePCMInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.javaIface = javaIface;
  }
  
  private Interface javaIface;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.javaIface);
    
    org.emftext.language.java.containers.Package ifacePackage = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceIfacePackage(javaIface), // correspondence source supplier
    	org.emftext.language.java.containers.Package.class,
    	(org.emftext.language.java.containers.Package _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(javaIface));
    if (ifacePackage == null) {
    	return;
    }
    registerObjectUnderModification(ifacePackage);
    Repository pcmRepository = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmRepository(javaIface, ifacePackage), // correspondence source supplier
    	Repository.class,
    	(Repository _element) -> true, // correspondence precondition checker
    	null);
    if (pcmRepository == null) {
    	return;
    }
    registerObjectUnderModification(pcmRepository);
    OperationInterface pcmIface = RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    userExecution.updatePcmIfaceElement(javaIface, ifacePackage, pcmRepository, pcmIface);
    
    addCorrespondenceBetween(userExecution.getElement1(javaIface, ifacePackage, pcmRepository, pcmIface), userExecution.getElement2(javaIface, ifacePackage, pcmRepository, pcmIface), "");
    
    postprocessElements();
  }
}
