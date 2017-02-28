package mir.responses.responses5_1ToJava.pcm2javaEjb;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedProvidedRoleResponse extends AbstractResponseRealization {
  public CreatedProvidedRoleResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<InterfaceProvidingEntity, ProvidedRole> change) {
    boolean _and = false;
    ProvidedRole _newValue = change.getNewValue();
    if (!(_newValue instanceof OperationProvidedRole)) {
      _and = false;
    } else {
      InterfaceProvidingEntity _affectedEObject = change.getAffectedEObject();
      boolean _not = (!(_affectedEObject instanceof org.palladiosimulator.pcm.system.System));
      _and = _not;
    }
    return _and;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<InterfaceProvidingEntity, ProvidedRole> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof InterfaceProvidingEntity)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("providedRoles_InterfaceProvidingEntity")) {
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
    InsertEReference<InterfaceProvidingEntity, ProvidedRole> typedChange = (InsertEReference<InterfaceProvidingEntity, ProvidedRole>)change;
    mir.routines.pcm2javaEjb.CreatedProvidedRoleEffect effect = new mir.routines.pcm2javaEjb.CreatedProvidedRoleEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
