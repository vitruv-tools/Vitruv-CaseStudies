package mir.routines.umlRequiredRolePropertyReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRequiredRolePropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DetectOrCreateCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private DetectOrCreateCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      Type _type = umlProperty.getType();
      return _type;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag3(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final Optional<OperationRequiredRole> pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmRequired.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<OperationRequiredRole, Boolean> _function = (OperationRequiredRole it) -> {
          return Boolean.valueOf(((it.getRequiredInterface__OperationRequiredRole() == pcmInterface) && Objects.equal(it.getEntityName(), umlProperty.getName())));
        };
        final OperationRequiredRole pcmRequiredCandidate = IterableExtensions.<OperationRequiredRole>findFirst(Iterables.<OperationRequiredRole>filter(pcmComponent.getRequiredRoles_InterfaceRequiringEntity(), OperationRequiredRole.class), _function);
        if ((pcmRequiredCandidate != null)) {
          _routinesFacade.addCorrespondenceForExistingRequiredRole(umlProperty, pcmRequiredCandidate);
        } else {
          _routinesFacade.createCorrespondingRequiredRole(umlProperty, umlComponent);
        }
      }
    }
  }
  
  public DetectOrCreateCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRolePropertyReactions.DetectOrCreateCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlComponent = umlComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DetectOrCreateCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlProperty, umlComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, umlComponent), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlProperty, umlComponent, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlProperty, umlComponent, pcmComponent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> pcmRequired = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRequired(umlProperty, umlComponent, pcmComponent, pcmInterface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlProperty, umlComponent, pcmComponent, pcmInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRequired.isPresent() ? pcmRequired.get() : null);
    userExecution.callRoutine1(umlProperty, umlComponent, pcmComponent, pcmInterface, pcmRequired, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
