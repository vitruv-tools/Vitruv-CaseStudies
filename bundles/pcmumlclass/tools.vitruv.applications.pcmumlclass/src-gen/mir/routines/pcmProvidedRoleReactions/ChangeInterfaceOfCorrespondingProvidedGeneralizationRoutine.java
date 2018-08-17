package mir.routines.pcmProvidedRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmProvidedRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine extends AbstractRepairRoutineRealization {
  private ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final Generalization umlGeneralization, final Optional<Interface> umlInterface) {
      return umlGeneralization;
    }
    
    public void update0Element(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final Generalization umlGeneralization, final Optional<Interface> umlInterface) {
      umlGeneralization.setGeneral(umlInterface.orElse(null));
    }
    
    public EObject getCorrepondenceSourceUmlGeneralization(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
      return pcmProvided;
    }
    
    public String getRetrieveTag1(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
      return TagLiterals.PROVIDED_ROLE__GENERALIZATION;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final Generalization umlGeneralization) {
      return pcmInterface;
    }
    
    public String getRetrieveTag2(final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface, final Generalization umlGeneralization) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
  }
  
  public ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole pcmProvided, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmProvidedRoleReactions.ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmProvided = pcmProvided;this.pcmInterface = pcmInterface;
  }
  
  private OperationProvidedRole pcmProvided;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeInterfaceOfCorrespondingProvidedGeneralizationRoutine with input:");
    getLogger().debug("   pcmProvided: " + this.pcmProvided);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Generalization umlGeneralization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlGeneralization(pcmProvided, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Generalization.class,
    	(org.eclipse.uml2.uml.Generalization _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmProvided, pcmInterface), 
    	false // asserted
    	);
    if (umlGeneralization == null) {
    	return false;
    }
    registerObjectUnderModification(umlGeneralization);
    	Optional<org.eclipse.uml2.uml.Interface> umlInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInterface(pcmProvided, pcmInterface, umlGeneralization), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag2(pcmProvided, pcmInterface, umlGeneralization), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInterface.isPresent() ? umlInterface.get() : null);
    // val updatedElement userExecution.getElement1(pcmProvided, pcmInterface, umlGeneralization, umlInterface);
    userExecution.update0Element(pcmProvided, pcmInterface, umlGeneralization, umlInterface);
    
    postprocessElements();
    
    return true;
  }
}
