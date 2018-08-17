package mir.routines.pcmAssemblyContextReactions;

import java.io.IOException;
import mir.routines.pcmAssemblyContextReactions.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.entity.ComposedProvidingRequiringEntity;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingAssemblyContextPropertyRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine2(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.moveCorrespondingAssemblyContextProperty(pcmAssembly, pcmComposite);
    }
    
    public void callRoutine1(final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.detectOrCreateCorrespondingAssemblyContextProperty(pcmAssembly, pcmComposite);
    }
  }
  
  public InsertCorrespondingAssemblyContextPropertyRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyContext pcmAssembly, final ComposedProvidingRequiringEntity pcmComposite) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmAssemblyContextReactions.InsertCorrespondingAssemblyContextPropertyRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmAssembly = pcmAssembly;this.pcmComposite = pcmComposite;
  }
  
  private AssemblyContext pcmAssembly;
  
  private ComposedProvidingRequiringEntity pcmComposite;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingAssemblyContextPropertyRoutine with input:");
    getLogger().debug("   pcmAssembly: " + this.pcmAssembly);
    getLogger().debug("   pcmComposite: " + this.pcmComposite);
    
    userExecution.callRoutine1(pcmAssembly, pcmComposite, this.getRoutinesFacade());
    
    userExecution.callRoutine2(pcmAssembly, pcmComposite, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
