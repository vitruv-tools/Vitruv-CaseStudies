package mir.routines.umlRequiredRolePropertyReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlRequiredRolePropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingRequiredRoleRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingRequiredRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      Type _type = umlProperty.getType();
      return _type;
    }
    
    public String getRetrieveTag2(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void callRoutine1(final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent, final Optional<OperationInterface> pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmComponent.isPresent() && (pcmInterface.isPresent() || (umlProperty.getType() == null)))) {
        _routinesFacade.detectOrCreateCorrespondingRequiredRole(umlProperty, umlComponent);
        _routinesFacade.moveCorrespondingRequiredRole(umlProperty, umlComponent);
      } else {
        _routinesFacade.deleteCorrespondingRequiredRole(umlProperty);
      }
    }
  }
  
  public InsertCorrespondingRequiredRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlRequiredRolePropertyReactions.InsertCorrespondingRequiredRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.umlComponent = umlComponent;
  }
  
  private Property umlProperty;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingRequiredRoleRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> pcmComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmComponent(umlProperty, umlComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlProperty, umlComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmComponent.isPresent() ? pcmComponent.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInterface(umlProperty, umlComponent, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlProperty, umlComponent, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInterface.isPresent() ? pcmInterface.get() : null);
    userExecution.callRoutine1(umlProperty, umlComponent, pcmComponent, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
