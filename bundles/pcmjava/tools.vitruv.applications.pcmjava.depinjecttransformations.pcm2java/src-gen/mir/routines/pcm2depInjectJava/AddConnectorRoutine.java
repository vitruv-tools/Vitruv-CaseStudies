package mir.routines.pcm2depInjectJava;

import java.io.IOException;
import mir.routines.pcm2depInjectJava.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmjava.depinjecttransformations.pcm2java.PcmJamoppUtilsGuice;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddConnectorRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddConnectorRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final AssemblyConnector assemblyConnector, @Extension final RoutinesFacade _routinesFacade) {
      final AssemblyContext assemblyContext = assemblyConnector.getProvidingAssemblyContext_AssemblyConnector();
      RepositoryComponent _encapsulatedComponent__AssemblyContext = assemblyContext.getEncapsulatedComponent__AssemblyContext();
      boolean _tripleNotEquals = (_encapsulatedComponent__AssemblyContext != null);
      if (_tripleNotEquals) {
        PcmJamoppUtilsGuice.createBindCallForConnector(assemblyContext, assemblyConnector, this.correspondenceModel, this.userInteracting);
      } else {
      }
    }
  }
  
  public AddConnectorRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final AssemblyConnector assemblyConnector) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2depInjectJava.AddConnectorRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2depInjectJava.RoutinesFacade(getExecutionState(), this);
    this.assemblyConnector = assemblyConnector;
  }
  
  private AssemblyConnector assemblyConnector;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddConnectorRoutine with input:");
    getLogger().debug("   assemblyConnector: " + this.assemblyConnector);
    
    userExecution.callRoutine1(assemblyConnector, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}
