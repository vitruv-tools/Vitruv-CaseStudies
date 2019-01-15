package mir.routines.umlProvidedRoleRealizationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlProvidedRoleRealizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class InsertCorrespondingProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private InsertCorrespondingProvidedRoleRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourcePcmComponent(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public String getRetrieveTag1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      Interface _contract = umlRealization.getContract();
      return _contract;
    }
    
    public String getRetrieveTag2(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void callRoutine1(final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent, final Optional<OperationInterface> pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmComponent.isPresent() && (pcmInterface.isPresent() || (umlRealization.getContract() == null)))) {
        _routinesFacade.detectOrCreateCorrespondingProvidedRole(umlRealization, umlComponent);
        _routinesFacade.moveCorrespondingProvidedRole(umlRealization, umlComponent);
      } else {
        _routinesFacade.deleteCorrespondingProvidedRole(umlRealization);
      }
    }
  }
  
  public InsertCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceRealization umlRealization, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleRealizationReactions.InsertCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlRealization = umlRealization;this.umlComponent = umlComponent;
  }
  
  private InterfaceRealization umlRealization;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlRealization: " + this.umlRealization);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> pcmComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmComponent(umlRealization, umlComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlRealization, umlComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmComponent.isPresent() ? pcmComponent.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInterface(umlRealization, umlComponent, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlRealization, umlComponent, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInterface.isPresent() ? pcmInterface.get() : null);
    userExecution.callRoutine1(umlRealization, umlComponent, pcmComponent, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
