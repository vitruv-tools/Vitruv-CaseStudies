package mir.responses.responses5_1ToJava.pcm2javaEjb;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreatedRequiredRoleResponse extends AbstractResponseRealization {
  public CreatedRequiredRoleResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<InterfaceRequiringEntity, RequiredRole> change) {
    boolean _and = false;
    RequiredRole _newValue = change.getNewValue();
    if (!(_newValue instanceof OperationRequiredRole)) {
      _and = false;
    } else {
      InterfaceRequiringEntity _affectedEObject = change.getAffectedEObject();
      boolean _not = (!(_affectedEObject instanceof org.palladiosimulator.pcm.system.System));
      _and = _not;
    }
    return _and;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<InterfaceRequiringEntity, RequiredRole> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof InterfaceRequiringEntity)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("requiredRoles_InterfaceRequiringEntity")) {
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
    InsertEReference<InterfaceRequiringEntity, RequiredRole> typedChange = (InsertEReference<InterfaceRequiringEntity, RequiredRole>)change;
    mir.routines.pcm2javaEjb.CreatedRequiredRoleEffect effect = new mir.routines.pcm2javaEjb.CreatedRequiredRoleEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}
