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
public class AddCorrespondenceForCollectionTypeRoutine extends AbstractRepairRoutineRealization {
  private AddCorrespondenceForCollectionTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return pcmCollectionType;
    }
    
    public EObject getCorrepondenceSource1(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return umlProperty;
    }
    
    public String getRetrieveTag1(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
    
    public EObject getElement2(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return umlProperty;
    }
    
    public String getTag1(final Property umlProperty, final CollectionDataType pcmCollectionType) {
      return TagLiterals.COLLECTION_DATATYPE__PROPERTY;
    }
  }
  
  public AddCorrespondenceForCollectionTypeRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Property umlProperty, final CollectionDataType pcmCollectionType) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlInnerDeclarationPropertyReactions.AddCorrespondenceForCollectionTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.umlProperty = umlProperty;this.pcmCollectionType = pcmCollectionType;
  }
  
  private Property umlProperty;
  
  private CollectionDataType pcmCollectionType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AddCorrespondenceForCollectionTypeRoutine with input:");
    getLogger().debug("   umlProperty: " + this.umlProperty);
    getLogger().debug("   pcmCollectionType: " + this.pcmCollectionType);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(umlProperty, pcmCollectionType), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.CollectionDataType.class,
    	(org.palladiosimulator.pcm.repository.CollectionDataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(umlProperty, pcmCollectionType)
    ).isEmpty()) {
    	return false;
    }
    addCorrespondenceBetween(userExecution.getElement1(umlProperty, pcmCollectionType), userExecution.getElement2(umlProperty, pcmCollectionType), userExecution.getTag1(umlProperty, pcmCollectionType));
    
    postprocessElements();
    
    return true;
  }
}
