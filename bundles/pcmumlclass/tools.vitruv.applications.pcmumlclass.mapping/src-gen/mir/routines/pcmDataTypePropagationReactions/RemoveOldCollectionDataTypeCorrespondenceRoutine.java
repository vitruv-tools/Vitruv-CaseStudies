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
public class RemoveOldCollectionDataTypeCorrespondenceRoutine extends AbstractRepairRoutineRealization {
  private RemoveOldCollectionDataTypeCorrespondenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final TypedElement umlElement, final String tag, final CollectionDataType oldCollectionType) {
      return oldCollectionType;
    }
    
    public String getRetrieveTag1(final TypedElement umlElement, final String tag) {
      return tag;
    }
    
    public EObject getElement2(final TypedElement umlElement, final String tag, final CollectionDataType oldCollectionType) {
      return umlElement;
    }
    
    public String getTag1(final TypedElement umlElement, final String tag, final CollectionDataType oldCollectionType) {
      return tag;
    }
    
    public EObject getCorrepondenceSourceOldCollectionType(final TypedElement umlElement, final String tag) {
      return umlElement;
    }
  }
  
  public RemoveOldCollectionDataTypeCorrespondenceRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final TypedElement umlElement, final String tag) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmDataTypePropagationReactions.RemoveOldCollectionDataTypeCorrespondenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlElement = umlElement;this.tag = tag;
  }
  
  private TypedElement umlElement;
  
  private String tag;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveOldCollectionDataTypeCorrespondenceRoutine with input:");
    getLogger().debug("   umlElement: " + this.umlElement);
    getLogger().debug("   tag: " + this.tag);
    
    org.palladiosimulator.pcm.repository.CollectionDataType oldCollectionType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOldCollectionType(umlElement, tag), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CollectionDataType.class,
    	(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlElement, tag), 
    	false // asserted
    	);
    if (oldCollectionType == null) {
    	return false;
    }
    registerObjectUnderModification(oldCollectionType);
    removeCorrespondenceBetween(userExecution.getElement1(umlElement, tag, oldCollectionType), userExecution.getElement2(umlElement, tag, oldCollectionType), userExecution.getTag1(umlElement, tag, oldCollectionType));
    
    postprocessElements();
    
    return true;
  }
}
