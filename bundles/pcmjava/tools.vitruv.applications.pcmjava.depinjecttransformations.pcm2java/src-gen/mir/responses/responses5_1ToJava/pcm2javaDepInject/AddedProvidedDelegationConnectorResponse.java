package mir.responses.responses5_1ToJava.pcm2javaDepInject;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.composition.ComposedStructure;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.ProvidedDelegationConnector;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class AddedProvidedDelegationConnectorResponse extends AbstractResponseRealization {
  public AddedProvidedDelegationConnectorResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<ComposedStructure, Connector> change) {
    Connector _newValue = change.getNewValue();
    return (_newValue instanceof ProvidedDelegationConnector);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<ComposedStructure, Connector> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof ComposedStructure)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("connectors__ComposedStructure")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference<?, ?>)) {
    	return false;
    }
    InsertEReference typedChange = (InsertEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<ComposedStructure, Connector> typedChange = (InsertEReference<ComposedStructure, Connector>)change;
    mir.routines.pcm2javaDepInject.AddedProvidedDelegationConnectorEffect effect = new mir.routines.pcm2javaDepInject.AddedProvidedDelegationConnectorEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
