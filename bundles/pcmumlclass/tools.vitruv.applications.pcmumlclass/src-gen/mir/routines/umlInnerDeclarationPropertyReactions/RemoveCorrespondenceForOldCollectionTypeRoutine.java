package mir.routines.umlInnerDeclarationPropertyReactions;

import java.io.IOException;
import mir.routines.umlInnerDeclarationPropertyReactions.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Property;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.applications.pcmumlclass.TagLiterals;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCorrespondenceForOldCollectionTypeRoutine extends AbstractRepairRoutineRealization {
  private RemoveCorrespondenceForOldCollectionTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return pcmCollectionType;
    }
    
    public String getRetrieveTag1(final Property umlProperty) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return umlProperty;
    }
    
    public String getTag1(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
    
    public EObject getCorrepondenceSourcePcmCollectionType(final Property umlProperty) {
      return umlProperty;
    }
  }
  
  public RemoveCorrespondenceForOldCollectionTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.RemoveCorrespondenceForOldCollectionTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;
  }
  
  private Property umlProperty;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCorrespondenceForOldCollectionTypeRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    
    org.palladiosimulator.pcm.repository.CollectionDataType pcmCollectionType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCollectionType(umlProperty), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CollectionDataType.class,
    	(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty), 
    	false // asserted
    	);
    if (pcmCollectionType == null) {
    	return false;
    }
    registerObjectUnderModification(pcmCollectionType);
    removeCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmCollectionType), userExecution.getElement2(umlProperty, pcmCollectionType), userExecution.getTag1(umlProperty, pcmCollectionType));
    
    postprocessElements();
    
    return true;
  }
}
