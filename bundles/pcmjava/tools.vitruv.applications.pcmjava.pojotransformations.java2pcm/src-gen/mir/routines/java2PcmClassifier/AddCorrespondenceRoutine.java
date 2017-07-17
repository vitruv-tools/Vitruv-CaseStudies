package mir.routines.java2PcmClassifier;

import java.io.IOException;
import mir.routines.java2PcmClassifier.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return pcmInterface;
    }
    
    public EObject getElement4(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return compilationUnit;
    }
    
    public EObject getElement2(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return javaInterface;
    }
    
    public EObject getElement3(final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
      return pcmInterface;
    }
  }
  
  public AddCorrespondenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationInterface pcmInterface, final Interface javaInterface, final CompilationUnit compilationUnit) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmClassifier.AddCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmClassifier.RoutinesFacade(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.javaInterface = javaInterface;this.compilationUnit = compilationUnit;
  }
  
  private OperationInterface pcmInterface;
  
  private Interface javaInterface;
  
  private CompilationUnit compilationUnit;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceRoutine with input:");
    getLogger().debug("   OperationInterface: " + this.pcmInterface);
    getLogger().debug("   Interface: " + this.javaInterface);
    getLogger().debug("   CompilationUnit: " + this.compilationUnit);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmInterface, javaInterface, compilationUnit), userExecution.getElement2(pcmInterface, javaInterface, compilationUnit), "");
    
    addCorrespondenceBetween(userExecution.getElement3(pcmInterface, javaInterface, compilationUnit), userExecution.getElement4(pcmInterface, javaInterface, compilationUnit), "");
    
    postprocessElements();
  }
}
