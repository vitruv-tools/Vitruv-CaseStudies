package mir.routines.umlReturnAndRegularParameterTypeReactions;

import java.io.IOException;
import mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondenceForOldCollectionType_ParameterRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondenceForOldCollectionType_ParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return pcmCollectionType;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter) {
      return TagLiterals.COLLECTION_DATATYPE__PARAMETER;
    }
    
    public EObject getElement2(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return umlParameter;
    }
    
    public String getTag1(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return TagLiterals.COLLECTION_DATATYPE__PARAMETER;
    }
    
    public EObject getCorrepondenceSourcePcmCollectionType(final Parameter umlParameter) {
      return umlParameter;
    }
  }
  
  public RemoveCorrespondenceForOldCollectionType_ParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlReturnAndRegularParameterTypeReactions.RemoveCorrespondenceForOldCollectionType_ParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;
  }
  
  private Parameter umlParameter;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondenceForOldCollectionType_ParameterRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    
    org.palladiosimulator.pcm.repository.CollectionDataType pcmCollectionType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCollectionType(umlParameter), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CollectionDataType.class,
    	(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter), 
    	false // asserted
    	);
    if (pcmCollectionType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCollectionType);
    removeCorrespondenceBetween(userExecution.getElement1(umlParameter, pcmCollectionType), userExecution.getElement2(umlParameter, pcmCollectionType), userExecution.getTag1(umlParameter, pcmCollectionType));
    
    postprocessElements();
    
    return true;
  }
}
