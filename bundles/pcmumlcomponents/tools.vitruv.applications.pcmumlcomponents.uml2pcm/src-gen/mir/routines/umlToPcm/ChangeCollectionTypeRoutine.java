package mir.routines.umlToPcm;

import java.io.IOException;
import mir.routines.umlToPcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeCollectionTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeCollectionTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final DataType umlOwner, final DataType umlType, final CollectionDataType pcmCollection, final org.palladiosimulator.pcm.repository.DataType pcmType) {
      return pcmCollection;
    }
    
    public void update0Element(final DataType umlOwner, final DataType umlType, final CollectionDataType pcmCollection, final org.palladiosimulator.pcm.repository.DataType pcmType) {
      pcmCollection.setInnerType_CollectionDataType(pcmType);
    }
    
    public EObject getCorrepondenceSourcePcmCollection(final DataType umlOwner, final DataType umlType) {
      return umlOwner;
    }
    
    public EObject getCorrepondenceSourcePcmType(final DataType umlOwner, final DataType umlType, final CollectionDataType pcmCollection) {
      return umlType;
    }
  }
  
  public ChangeCollectionTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final DataType umlOwner, final DataType umlType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToPcm.ChangeCollectionTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToPcm.RoutinesFacade(getExecutionState(), this);
    this.umlOwner = umlOwner;this.umlType = umlType;
  }
  
  private DataType umlOwner;
  
  private DataType umlType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeCollectionTypeRoutine with input:");
    getLogger().debug("   DataType: " + this.umlOwner);
    getLogger().debug("   DataType: " + this.umlType);
    
    CollectionDataType pcmCollection = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmCollection(umlOwner, umlType), // correspondence source supplier
    	CollectionDataType.class,
    	(CollectionDataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmCollection == null) {
    	return;
    }
    registerObjectUnderModification(pcmCollection);
    org.palladiosimulator.pcm.repository.DataType pcmType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmType(umlOwner, umlType, pcmCollection), // correspondence source supplier
    	org.palladiosimulator.pcm.repository.DataType.class,
    	(org.palladiosimulator.pcm.repository.DataType _element) -> true, // correspondence precondition checker
    	null);
    if (pcmType == null) {
    	return;
    }
    registerObjectUnderModification(pcmType);
    // val updatedElement userExecution.getElement1(umlOwner, umlType, pcmCollection, pcmType);
    userExecution.update0Element(umlOwner, umlType, pcmCollection, pcmType);
    
    postprocessElements();
  }
}
