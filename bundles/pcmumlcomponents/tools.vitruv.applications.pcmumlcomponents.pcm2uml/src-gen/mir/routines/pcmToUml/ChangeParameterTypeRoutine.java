package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmumlcomp.pcm2uml.PcmToUmlUtil;
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
    
    public EObject getElement1(final Parameter pcmParameter, final DataType pcmDataType, final org.eclipse.uml2.uml.Parameter umlParameter) {
      return umlParameter;
    }
    
    public void update0Element(final Parameter pcmParameter, final DataType pcmDataType, final org.eclipse.uml2.uml.Parameter umlParameter) {
      Logger _logger = this.getLogger();
      String _parameterName = pcmParameter.getParameterName();
      String _plus = ("Change Parameter type at " + _parameterName);
      String _plus_1 = (_plus + ", ");
      String _entityName = pcmParameter.getOperationSignature__Parameter().getEntityName();
      String _plus_2 = (_plus_1 + _entityName);
      String _plus_3 = (_plus_2 + " to ");
      String _plus_4 = (_plus_3 + pcmDataType);
      _logger.info(_plus_4);
      umlParameter.setType(PcmToUmlUtil.retrieveUmlType(this.correspondenceModel, pcmDataType));
      PcmToUmlUtil.updateMultiplicity(umlParameter, Boolean.valueOf((pcmDataType instanceof CollectionDataType)));
    }
    
    public EObject getCorrepondenceSourceUmlParameter(final Parameter pcmParameter, final DataType pcmDataType) {
      return pcmParameter;
    }
  }
  
  public ChangeParameterTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter pcmParameter, final DataType pcmDataType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ChangeParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmParameter = pcmParameter;this.pcmDataType = pcmDataType;
  }
  
  private Parameter pcmParameter;
  
  private DataType pcmDataType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeRoutine with input:");
    getLogger().debug("   Parameter: " + this.pcmParameter);
    getLogger().debug("   DataType: " + this.pcmDataType);
    
    org.eclipse.uml2.uml.Parameter umlParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParameter(pcmParameter, pcmDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (umlParameter == null) {
    	return;
    }
    registerObjectUnderModification(umlParameter);
    // val updatedElement userExecution.getElement1(pcmParameter, pcmDataType, umlParameter);
    userExecution.update0Element(pcmParameter, pcmDataType, umlParameter);
    
    postprocessElements();
  }
}
