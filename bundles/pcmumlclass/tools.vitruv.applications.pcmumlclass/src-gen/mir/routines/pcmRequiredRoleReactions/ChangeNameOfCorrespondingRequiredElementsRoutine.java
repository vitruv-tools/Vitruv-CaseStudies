package mir.routines.pcmRequiredRoleReactions;

import java.io.IOException;
import mir.routines.pcmRequiredRoleReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeNameOfCorrespondingRequiredElementsRoutine extends AbstractRepairRoutineRealization {
  private ChangeNameOfCorrespondingRequiredElementsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final OperationRequiredRole pcmRequired, final String newName, final Parameter umlConstructorParameter, final Property umlRequiredField, @Extension final RoutinesFacade _routinesFacade) {
      umlConstructorParameter.setName(newName);
      umlRequiredField.setName(newName);
    }
    
    public String getRetrieveTag1(final OperationRequiredRole pcmRequired, final String newName) {
      return TagLiterals.REQUIRED_ROLE__PARAMETER;
    }
    
    public String getRetrieveTag2(final OperationRequiredRole pcmRequired, final String newName, final Parameter umlConstructorParameter) {
      return TagLiterals.REQUIRED_ROLE__PROPERTY;
    }
    
    public EObject getCorrepondenceSourceUmlRequiredField(final OperationRequiredRole pcmRequired, final String newName, final Parameter umlConstructorParameter) {
      return pcmRequired;
    }
    
    public EObject getCorrepondenceSourceUmlConstructorParameter(final OperationRequiredRole pcmRequired, final String newName) {
      return pcmRequired;
    }
  }
  
  public ChangeNameOfCorrespondingRequiredElementsRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole pcmRequired, final String newName) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmRequiredRoleReactions.ChangeNameOfCorrespondingRequiredElementsRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmRequired = pcmRequired;this.newName = newName;
  }
  
  private OperationRequiredRole pcmRequired;
  
  private String newName;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeNameOfCorrespondingRequiredElementsRoutine with input:");
    getLogger().debug("   pcmRequired: " + this.pcmRequired);
    getLogger().debug("   newName: " + this.newName);
    
    org.eclipse.uml2.uml.Parameter umlConstructorParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlConstructorParameter(pcmRequired, newName), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmRequired, newName), 
    	false // asserted
    	);
    if (umlConstructorParameter == null) {
    	return false;
    }
    registerObjectUnderModification(umlConstructorParameter);
    org.eclipse.uml2.uml.Property umlRequiredField = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlRequiredField(pcmRequired, newName, umlConstructorParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Property.class,
    	(org.eclipse.uml2.uml.Property _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag2(pcmRequired, newName, umlConstructorParameter), 
    	false // asserted
    	);
    if (umlRequiredField == null) {
    	return false;
    }
    registerObjectUnderModification(umlRequiredField);
    userExecution.executeAction1(pcmRequired, newName, umlConstructorParameter, umlRequiredField, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}
