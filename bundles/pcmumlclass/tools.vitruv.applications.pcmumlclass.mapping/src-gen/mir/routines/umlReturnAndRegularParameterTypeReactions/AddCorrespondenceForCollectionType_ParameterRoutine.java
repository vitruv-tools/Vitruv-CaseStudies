package mir.routines.umlReturnAndRegularParameterTypeReactions;

import java.io.IOException;
import mir.routines.umlReturnAndRegularParameterTypeReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.applications.pcmumlclass.mapping.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCorrespondenceForCollectionType_ParameterRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForCollectionType_ParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return pcmCollectionType;
    }
    
    public EObject getCorrepondenceSource1(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return umlParameter;
    }
    
    public String getRetrieveTag1(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return TagLiterals.COLLECTION_DATATYPE__PARAMETER;
    }
    
    public EObject getElement2(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return umlParameter;
    }
    
    public String getTag1(final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
      return TagLiterals.COLLECTION_DATATYPE__PARAMETER;
    }
  }
  
  public AddCorrespondenceForCollectionType_ParameterRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter umlParameter, final CollectionDataType pcmCollectionType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlReturnAndRegularParameterTypeReactions.AddCorrespondenceForCollectionType_ParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlParameter = umlParameter;this.pcmCollectionType = pcmCollectionType;
  }
  
  private Parameter umlParameter;
  
  private CollectionDataType pcmCollectionType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForCollectionType_ParameterRoutine with input:");
    getLogger().debug("   umlParameter: " + this.umlParameter);
    getLogger().debug("   pcmCollectionType: " + this.pcmCollectionType);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlParameter, pcmCollectionType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CollectionDataType.class,
    	(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlParameter, pcmCollectionType)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlParameter, pcmCollectionType), userExecution.getElement2(umlParameter, pcmCollectionType), userExecution.getTag1(umlParameter, pcmCollectionType));
    
    postprocessElements();
    
    return true;
  }
}
