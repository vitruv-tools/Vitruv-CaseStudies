package mir.routines.umlProvidedRoleGeneralizationReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.umlProvidedRoleGeneralizationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
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
    
    public EObject getCorrepondenceSourcePcmComponent(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
      return umlComponent;
    }
    
    public String getRetrieveTag1(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
      return TagLiterals.IPRE__IMPLEMENTATION;
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      Classifier _general = umlGeneralization.getGeneral();
      return _general;
    }
    
    public String getRetrieveTag2(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public void callRoutine1(final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent, final Optional<InterfaceProvidingRequiringEntity> pcmComponent, final Optional<OperationInterface> pcmInterface, @Extension final RoutinesFacade _routinesFacade) {
      if ((pcmComponent.isPresent() && (pcmInterface.isPresent() || (umlGeneralization.getGeneral() == null)))) {
        _routinesFacade.debugging_detectOrCreateCorrespondingProvidedRole(umlGeneralization, umlComponent);
        _routinesFacade.moveCorrespondingProvidedRole(umlGeneralization, umlComponent);
      } else {
        _routinesFacade.deleteCorrespondingProvidedRole(umlGeneralization);
      }
    }
  }
  
  public InsertCorrespondingProvidedRoleRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Generalization umlGeneralization, final org.eclipse.uml2.uml.Class umlComponent) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlProvidedRoleGeneralizationReactions.InsertCorrespondingProvidedRoleRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlGeneralization = umlGeneralization;this.umlComponent = umlComponent;
  }
  
  private Generalization umlGeneralization;
  
  private org.eclipse.uml2.uml.Class umlComponent;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine InsertCorrespondingProvidedRoleRoutine with input:");
    getLogger().debug("   umlGeneralization: " + this.umlGeneralization);
    getLogger().debug("   umlComponent: " + this.umlComponent);
    
    	Optional<org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity> pcmComponent = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmComponent(umlGeneralization, umlComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity.class,
    		(org.palladiosimulator.pcm.core.entity.InterfaceProvidingRequiringEntity _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(umlGeneralization, umlComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmComponent.isPresent() ? pcmComponent.get() : null);
    	Optional<org.palladiosimulator.pcm.repository.OperationInterface> pcmInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourcePcmInterface(umlGeneralization, umlComponent, pcmComponent), // correspondence source supplier
    		org.palladiosimulator.pcm.repository.OperationInterface.class,
    		(org.palladiosimulator.pcm.repository.OperationInterface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(umlGeneralization, umlComponent, pcmComponent), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(pcmInterface.isPresent() ? pcmInterface.get() : null);
    userExecution.callRoutine1(umlGeneralization, umlComponent, pcmComponent, pcmInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
