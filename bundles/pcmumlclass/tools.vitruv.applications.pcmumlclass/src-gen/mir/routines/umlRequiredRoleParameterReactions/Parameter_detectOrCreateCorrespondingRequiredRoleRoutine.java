package mir.routines.umlRequiredRoleParameterReactions;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
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
public class Parameter_detectOrCreateCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_detectOrCreateCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Parameter umlParameter, final Operation umlConstructor) {
      return umlConstructor;
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final Operation umlConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent) {
      Type _type = umlParameter.getType();
      return _type;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag3(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public void callRoutine1(final Parameter umlParameter, final Operation umlConstructor, final InterfaceProvidingRequiringEntity pcmComponent, final OperationInterface pcmInterface, final Optional<OperationRequiredRole> pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      boolean _isPresent = pcmRequired.isPresent();
      boolean _not = (!_isPresent);
      if (_not) {
        final Function1<OperationRequiredRole, Boolean> _function = (OperationRequiredRole it) -> {
          return Boolean.valueOf(((it.getRequiredInterface__OperationRequiredRole() == pcmInterface) && Objects.equal(it.getEntityName(), umlParameter.getName())));
        };
        final OperationRequiredRole pcmRequiredCandidate = IterableExtensions.<OperationRequiredRole>findFirst(Iterables.<OperationRequiredRole>filter(pcmComponent.getRequiredRoles_InterfaceRequiringEntity(), OperationRequiredRole.class), _function);
        if ((pcmRequiredCandidate != null)) {
          _routinesFacade.parameter_addCorrespondenceForExistingRequiredRole(umlParameter, pcmRequiredCandidate);
        } else {
          _routinesFacade.parameter_createCorrespondingRequiredRole(umlParameter, umlConstructor);
        }
      }
    }
  }
  
  public Parameter_detectOrCreateCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final Operation umlConstructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_detectOrCreateCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.umlConstructor = umlConstructor;
  }
  
  private Parameter umlParameter;
  
  private Operation umlConstructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_detectOrCreateCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   umlConstructor: " + this.umlConstructor);
    
    org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity pcmComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmComponent(umlParameter, umlConstructor), // correspondence source supplier
    	org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    	(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, umlConstructor), 
    	false // asserted
    	);
    if (pcmComponent == null) {
    	return false;
    }
    registerObjectUnderModification(pcmComponent);
    org.palladiosimulator.pcm.repository.OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(umlParameter, umlConstructor, pcmComponent), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.OperationInterface.class,
    	(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(umlParameter, umlConstructor, pcmComponent), 
    	false // asserted
    	);
    if (pcmInterface == null) {
    	return false;
    }
    registerObjectUnderModification(pcmInterface);
    	Optional<org.palladiosimulator.pcm.repository.OperationRequiredRole> pcmRequired = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmRequired(umlParameter, umlConstructor, pcmComponent, pcmInterface), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationRequiredRole.class,
    		(org.palladiosimulator.pcm.repository.OperationRequiredRole _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(umlParameter, umlConstructor, pcmComponent, pcmInterface), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmRequired.isPresent() ? pcmRequired.get() : null);
    userExecution.callRoutine1(umlParameter, umlConstructor, pcmComponent, pcmInterface, pcmRequired, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
