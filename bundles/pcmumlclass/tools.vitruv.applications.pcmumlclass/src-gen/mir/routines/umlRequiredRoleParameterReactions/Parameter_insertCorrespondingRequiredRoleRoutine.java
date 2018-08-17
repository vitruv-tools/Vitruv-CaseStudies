package mir.routines.umlRequiredRoleParameterReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRequiredRoleParameterReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class Parameter_insertCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private Parameter_insertCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Parameter umlParameter, final Operation umlConstructor) {
      return umlConstructor;
    }
    
    public EObject getCorrepondenceSourcePcmRequired(final Parameter umlParameter, final Operation umlConstructor, final Optional<InterfaceProvidingRequiringEntity> pcmComponent, final Optional<OperationInterface> pcmInterface) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final Operation umlConstructor) {
      return TagLiterals.IPRE__CONSTRUCTOR;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Parameter umlParameter, final Operation umlConstructor, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      Type _type = umlParameter.getType();
      return _type;
    }
    
    public String getRetrieveTag2(final Parameter umlParameter, final Operation umlConstructor, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public String getRetrieveTag3(final Parameter umlParameter, final Operation umlConstructor, final Optional<InterfaceProvidingRequiringEntity> pcmComponent, final Optional<OperationInterface> pcmInterface) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public void callRoutine1(final Parameter umlParameter, final Operation umlConstructor, final Optional<InterfaceProvidingRequiringEntity> pcmComponent, final Optional<OperationInterface> pcmInterface, final Optional<OperationRequiredRole> pcmRequired, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmComponent.isPresent() && (pcmInterface.isPresent() || (umlParameter.getType() == null)))) {
        _routinesFacade.parameter_detectOrCreateCorrespondingRequiredRole(umlParameter, umlConstructor);
        _routinesFacade.parameter_moveCorrespondingRequiredRole(umlParameter, umlConstructor);
      } else {
        _routinesFacade.parameter_deleteCorrespondingRequiredRole(umlParameter);
      }
    }
  }
  
  public Parameter_insertCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final Operation umlConstructor) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRoleParameterReactions.Parameter_insertCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.umlConstructor = umlConstructor;
  }
  
  private Parameter umlParameter;
  
  private Operation umlConstructor;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine Parameter_insertCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   umlConstructor: " + this.umlConstructor);
    
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> pcmComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmComponent(umlParameter, umlConstructor), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlParameter, umlConstructor), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmComponent.isPresent() ? pcmComponent.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInterface(umlParameter, umlConstructor, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlParameter, umlConstructor, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInterface.isPresent() ? pcmInterface.get() : null);
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
