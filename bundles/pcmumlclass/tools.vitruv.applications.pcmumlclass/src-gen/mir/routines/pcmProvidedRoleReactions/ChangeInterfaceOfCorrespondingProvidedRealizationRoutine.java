package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInterfaceOfCorrespondingProvidedRealizationRoutine extends AbstractRepairRoutineRealization {
  private ChangeInterfaceOfCorrespondingProvidedRealizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final InterfaceRealization umlRealization, final Optional<Interface> umlInterface) {
      return umlRealization;
    }
    
    public void update0Element(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final InterfaceRealization umlRealization, final Optional<Interface> umlInterface) {
      umlRealization.setContract(umlInterface.orElse(null));
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
      return TagLiterals.PROVIDED_ROLE__INTERFACE_REALIZATION;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final InterfaceRealization umlRealization) {
      return pcmInterface;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final InterfaceRealization umlRealization) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourceUmlRealization(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
      return pcmProvided;
    }
  }
  
  public ChangeInterfaceOfCorrespondingProvidedRealizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.ChangeInterfaceOfCorrespondingProvidedRealizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmInterface = pcmInterface;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInterfaceOfCorrespondingProvidedRealizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.InterfaceRealization umlRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRealization(pcmProvided, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.InterfaceRealization.class,
    	(org.eclipse.uml2.uml.InterfaceRealization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided, pcmInterface), 
    	false // asserted
    	);
    if (umlRealization == null) {
    	return false;
    }
    registerObjectUnderModification(umlRealization);
    	Optional<org.eclipse.uml2.uml.Interface> umlInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInterface(pcmProvided, pcmInterface, umlRealization), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmProvided, pcmInterface, umlRealization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInterface.isPresent() ? umlInterface.get() : null);
    // val updatedElement userExecution.getElement1(pcmProvided, pcmInterface, umlRealization, umlInterface);
    userExecution.update0Element(pcmProvided, pcmInterface, umlRealization, umlInterface);
    
    postprocessElements();
    
    return true;
  }
}
