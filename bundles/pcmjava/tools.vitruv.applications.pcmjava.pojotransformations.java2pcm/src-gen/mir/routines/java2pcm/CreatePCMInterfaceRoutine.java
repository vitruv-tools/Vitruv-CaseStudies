package mir.routines.java2pcm;

import java.io.IOException;
import mir.routines.java2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.OperationInterface;
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
    
    public EObject getElement1(final Interface javaInterface, final CompilationUnit javaPackage, final OperationInterface pcmIface) {
      return pcmIface;
    }
    
    public void updatePcmIfaceElement(final Interface javaInterface, final CompilationUnit javaPackage, final OperationInterface pcmIface) {
      pcmIface.setEntityName(javaInterface.getName());
    }
    
    public EObject getElement2(final Interface javaInterface, final CompilationUnit javaPackage, final OperationInterface pcmIface) {
      return javaInterface;
    }
  }
  
  public CreatePCMInterfaceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface javaInterface, final CompilationUnit javaPackage) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2pcm.CreatePCMInterfaceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2pcm.RoutinesFacade(getExecutionState(), this);
    this.javaInterface = javaInterface;this.javaPackage = javaPackage;
  }
  
  private Interface javaInterface;
  
  private CompilationUnit javaPackage;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMInterfaceRoutine with input:");
    getLogger().debug("   Interface: " + this.javaInterface);
    getLogger().debug("   CompilationUnit: " + this.javaPackage);
    
    OperationInterface pcmIface = RepositoryFactoryImpl.eINSTANCE.createOperationInterface();
    userExecution.updatePcmIfaceElement(javaInterface, javaPackage, pcmIface);
    
    addCorrespondenceBetween(userExecution.getElement1(javaInterface, javaPackage, pcmIface), userExecution.getElement2(javaInterface, javaPackage, pcmIface), "");
    
    postprocessElements();
  }
}
