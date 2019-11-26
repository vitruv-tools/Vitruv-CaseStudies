package mir.routines.pcmDataTypePropagationReactions;

import java.io.IOException;
import mir.routines.pcmDataTypePropagationReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.TypedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddCollectionDataTypeCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private AddCollectionDataTypeCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CollectionDataType pcmType, final TypedElement umlElement, final String tag) {
      return pcmType;
    }
    
    public EObject getCorrepondenceSource1(final CollectionDataType pcmType, final TypedElement umlElement, final String tag) {
      return umlElement;
    }
    
    public String getRetrieveTag1(final CollectionDataType pcmType, final TypedElement umlElement, final String tag) {
      return tag;
    }
    
    public EObject getElement2(final CollectionDataType pcmType, final TypedElement umlElement, final String tag) {
      return umlElement;
    }
    
    public String getTag1(final CollectionDataType pcmType, final TypedElement umlElement, final String tag) {
      return tag;
    }
  }
  
  public AddCollectionDataTypeCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmType, final TypedElement umlElement, final String tag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.AddCollectionDataTypeCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.pcmType = pcmType;this.umlElement = umlElement;this.tag = tag;
  }
  
  private CollectionDataType pcmType;
  
  private TypedElement umlElement;
  
  private String tag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCollectionDataTypeCorrespondenceRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    getLogger().debug("   umlElement: " + this.umlElement);
    getLogger().debug("   tag: " + this.tag);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(pcmType, umlElement, tag), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CollectionDataType.class,
    	(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType, umlElement, tag)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(pcmType, umlElement, tag), userExecution.getElement2(pcmType, umlElement, tag), userExecution.getTag1(pcmType, umlElement, tag));
    
    postprocessElements();
    
    return true;
  }
}
