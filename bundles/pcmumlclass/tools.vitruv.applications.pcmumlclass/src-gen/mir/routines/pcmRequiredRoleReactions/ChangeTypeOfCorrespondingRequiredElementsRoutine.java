package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import java.util.Optional;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeTypeOfCorrespondingRequiredElementsRoutine extends AbstractRepairRoutineRealization {
  private ChangeTypeOfCorrespondingRequiredElementsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface, final Parameter umlConstructorParameter, final Property umlRequiredField, final Optional<Interface> umlInterface, @Extension final RoutinesFacade _routinesFacade) {
      umlConstructorParameter.setType(umlInterface.orElse(null));
      umlRequiredField.setType(umlInterface.orElse(null));
    }
    
    public String getRetrieveTag1(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public EObject getCorrepondenceSourceUmlInterface(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface, final Parameter umlConstructorParameter, final Property umlRequiredField) {
      return pcmInterface;
    }
    
    public String getRetrieveTag2(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface, final Parameter umlConstructorParameter) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredField(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface, final Parameter umlConstructorParameter) {
      return pcmRequired;
    }
    
    public String getRetrieveTag3(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface, final Parameter umlConstructorParameter, final Property umlRequiredField) {
      return TagLiterals.INTERFACE_TO_INTERFACE;
    }
    
    public EObject getCorrepondenceSourceUmlConstructorParameter(final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface) {
      return pcmRequired;
    }
  }
  
  public ChangeTypeOfCorrespondingRequiredElementsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final OperationInterface pcmInterface) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.ChangeTypeOfCorrespondingRequiredElementsRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.pcmInterface = pcmInterface;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private OperationInterface pcmInterface;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeTypeOfCorrespondingRequiredElementsRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    getLogger().debug("   pcmInterface: " + this.pcmInterface);
    
    org.eclipse.uml2.uml.Parameter umlConstructorParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlConstructorParameter(pcmRequired, pcmInterface), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRequired, pcmInterface), 
    	false // asserted
    	);
    if (umlConstructorParameter == null) {
    	return false;
    }
    registerObjectUnderModification(umlConstructorParameter);
    org.eclipse.uml2.uml.Property umlRequiredField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRequiredField(pcmRequired, pcmInterface, umlConstructorParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmRequired, pcmInterface, umlConstructorParameter), 
    	false // asserted
    	);
    if (umlRequiredField == null) {
    	return false;
    }
    registerObjectUnderModification(umlRequiredField);
    	Optional<org.eclipse.uml2.uml.Interface> umlInterface = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceUmlInterface(pcmRequired, pcmInterface, umlConstructorParameter, umlRequiredField), // correspondence source supplier
    		org.eclipse.uml2.uml.Interface.class,
    		(org.eclipse.uml2.uml.Interface _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag3(pcmRequired, pcmInterface, umlConstructorParameter, umlRequiredField), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(umlInterface.isPresent() ? umlInterface.get() : null);
    userExecution.executeAction1(pcmRequired, pcmInterface, umlConstructorParameter, umlRequiredField, umlInterface, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
