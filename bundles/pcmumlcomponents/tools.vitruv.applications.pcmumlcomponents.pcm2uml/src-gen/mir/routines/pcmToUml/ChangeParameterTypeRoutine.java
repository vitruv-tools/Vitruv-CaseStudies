package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil;
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
    
    public EObject getElement1(final Parameter pcmParameter, final DataType pcmDataType, final org.eclipse.uml2.uml.Parameter umlParameter, final Model umlModel) {
      return umlParameter;
    }
    
    public void update0Element(final Parameter pcmParameter, final DataType pcmDataType, final org.eclipse.uml2.uml.Parameter umlParameter, final Model umlModel) {
      if (((pcmDataType == null) || ((pcmDataType instanceof CollectionDataType) && (((CollectionDataType) pcmDataType).getInnerType_CollectionDataType() == null)))) {
        umlParameter.setType(null);
      } else {
        umlParameter.setType(PcmToUmlUtil.retrieveUmlType(this.correspondenceModel, pcmParameter.getDataType__Parameter(), umlModel));
      }
      PcmToUmlUtil.updateMultiplicity(umlParameter, Boolean.valueOf(((umlParameter.getType() != null) && (pcmDataType instanceof CollectionDataType))));
    }
    
    public EObject getCorrepondenceSourceUmlParameter(final Parameter pcmParameter, final DataType pcmDataType) {
      return pcmParameter;
    }
    
    public EObject getCorrepondenceSourceUmlModel(final Parameter pcmParameter, final DataType pcmDataType, final org.eclipse.uml2.uml.Parameter umlParameter) {
      Repository _repository__Interface = pcmParameter.getOperationSignature__Parameter().getInterface__OperationSignature().getRepository__Interface();
      return _repository__Interface;
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
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeRoutine with input:");
    getLogger().debug("   pcmParameter: " + this.pcmParameter);
    getLogger().debug("   pcmDataType: " + this.pcmDataType);
    
    org.eclipse.uml2.uml.Parameter umlParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlParameter(pcmParameter, pcmDataType), // correspondence source supplier
    	org.eclipse.uml2.uml.Parameter.class,
    	(org.eclipse.uml2.uml.Parameter _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlParameter == null) {
    	return false;
    }
    registerObjectUnderModification(umlParameter);
    org.eclipse.uml2.uml.Model umlModel = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUmlModel(pcmParameter, pcmDataType, umlParameter), // correspondence source supplier
    	org.eclipse.uml2.uml.Model.class,
    	(org.eclipse.uml2.uml.Model _element) -> true, // correspondence precondition checker
    	null, 
    	false // asserted
    	);
    if (umlModel == null) {
    	return false;
    }
    registerObjectUnderModification(umlModel);
    // val updatedElement userExecution.getElement1(pcmParameter, pcmDataType, umlParameter, umlModel);
    userExecution.update0Element(pcmParameter, pcmDataType, umlParameter, umlModel);
    
    postprocessElements();
    
    return true;
  }
}
