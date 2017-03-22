package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUmlType(final Parameter pcmParameter, final org.eclipse.uml2.uml.Parameter umlParameter) {
      DataType _dataType__Parameter = pcmParameter.getDataType__Parameter();
      return _dataType__Parameter;
    }
    
    public EObject getElement1(final Parameter pcmParameter, final org.eclipse.uml2.uml.Parameter umlParameter, final org.eclipse.uml2.uml.DataType umlType) {
      return umlParameter;
    }
    
    public void update0Element(final Parameter pcmParameter, final org.eclipse.uml2.uml.Parameter umlParameter, final org.eclipse.uml2.uml.DataType umlType) {
      umlParameter.setType(umlType);
    }
    
    public EObject getCorrepondenceSourceUmlParameter(final Parameter pcmParameter) {
      return pcmParameter;
    }
  }
  
  public ChangeParameterTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ChangeParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmParameter = pcmParameter;
  }
  
  private Parameter pcmParameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeRoutine with input:");
    getLogger().debug("   Parameter: " + this.pcmParameter);
    
    org.eclipse.uml2.uml.Parameter umlParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParameter(pcmParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (umlParameter == null) {
    	return;
    }
    initializeRetrieveElementState(umlParameter);
    org.eclipse.uml2.uml.DataType umlType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlType(pcmParameter, umlParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	null);
    if (umlType == null) {
    	return;
    }
    initializeRetrieveElementState(umlType);
    // val updatedElement userExecution.getElement1(pcmParameter, umlParameter, umlType);
    userExecution.update0Element(pcmParameter, umlParameter, umlType);
    
    postprocessElementStates();
  }
}
