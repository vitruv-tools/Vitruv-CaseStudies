package mir.routines.pcm2javaDepInject;

import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.pcm2javaDepInject.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.composition.AssemblyConnector;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import tools.vitruv.applications.pcmjava.depinjecttransformations.pcm2java.PCMJaMoPPUtilsGuice;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
public class AddedConnectorEffect extends AbstractEffectRealization {
  public AddedConnectorEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<ComposedStructure, Connector> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<ComposedStructure, Connector> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddedConnectorEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2javaDepInject.AddedConnectorEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2javaDepInject.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<ComposedStructure, Connector> change) {
      Connector _newValue = change.getNewValue();
      final AssemblyConnector assemblyConnector = ((AssemblyConnector) _newValue);
      final AssemblyContext assemblyContext = assemblyConnector.getProvidingAssemblyContext_AssemblyConnector();
      RepositoryComponent _encapsulatedComponent__AssemblyContext = assemblyContext.getEncapsulatedComponent__AssemblyContext();
      boolean _notEquals = (!Objects.equal(_encapsulatedComponent__AssemblyContext, null));
      if (_notEquals) {
        PCMJaMoPPUtilsGuice.createBindCallForConnector(assemblyContext, assemblyConnector, this.correspondenceModel, this.userInteracting);
      } else {
      }
    }
  }
}
