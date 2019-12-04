package mir.routines.pcm2javaCommon;

import java.io.IOException;
import mir.routines.pcm2javaCommon.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.containers.CompilationUnit;
import org.palladiosimulator.pcm.repository.Interface;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddMissingInterfaceCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddMissingInterfaceCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Interface pcmInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return javaInterface;
    }
    
    public EObject getElement4(final Interface pcmInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return pcmInterface;
    }
    
    public EObject getElement2(final Interface pcmInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      return pcmInterface;
    }
    
    public EObject getElement3(final Interface pcmInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
      CompilationUnit _containingCompilationUnit = javaInterface.getContainingCompilationUnit();
      return _containingCompilationUnit;
    }
  }
  
  public AddMissingInterfaceCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Interface pcmInterface, final org.emftext.language.java.classifiers.Interface javaInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2javaCommon.AddMissingInterfaceCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmInterface = pcmInterface;this.javaInterface = javaInterface;
  }
  
  private Interface pcmInterface;
  
  private org.emftext.language.java.classifiers.Interface javaInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddMissingInterfaceCorrespondenceRoutine with input:");
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    getLogger().debug("   javaInterface: " + this.javaInterface);
    
    addCorrespondenceBetween(userExecution.getElement1(pcmInterface, javaInterface), userExecution.getElement2(pcmInterface, javaInterface), "");
    
    addCorrespondenceBetween(userExecution.getElement3(pcmInterface, javaInterface), userExecution.getElement4(pcmInterface, javaInterface), "");
    
    postprocessElements();
    
    return true;
  }
}
