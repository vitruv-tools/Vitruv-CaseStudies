package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.DataType;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import tools.vitruv.applications.pcmumlcomponents.pcm2uml.PcmToUmlUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ClearCorrespondenceForCollectionTypesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ClearCorrespondenceForCollectionTypesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CollectionDataType pcmType, final DataType oldInnerType) {
      return pcmType;
    }
    
    public String getRetrieveTag1(final CollectionDataType pcmType) {
      return PcmToUmlUtil.COLLECTION_TYPE_TAG;
    }
    
    public EObject getElement2(final CollectionDataType pcmType, final DataType oldInnerType) {
      return oldInnerType;
    }
    
    public EObject getCorrepondenceSourceOldInnerType(final CollectionDataType pcmType) {
      return pcmType;
    }
  }
  
  public ClearCorrespondenceForCollectionTypesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CollectionDataType pcmType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.ClearCorrespondenceForCollectionTypesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.pcmType = pcmType;
  }
  
  private CollectionDataType pcmType;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine ClearCorrespondenceForCollectionTypesRoutine with input:");
    getLogger().debug("   pcmType: " + this.pcmType);
    
    org.eclipse.uml2.uml.DataType oldInnerType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOldInnerType(pcmType), // correspondence source supplier
    	org.eclipse.uml2.uml.DataType.class,
    	(org.eclipse.uml2.uml.DataType _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(pcmType), 
    	false // asserted
    	);
    if (oldInnerType == null) {
    	return false;
    }
    registerObjectUnderModification(oldInnerType);
    removeCorrespondenceBetween(userExecution.getElement1(pcmType, oldInnerType), userExecution.getElement2(pcmType, oldInnerType), "");
    
    postprocessElements();
    
    return true;
  }
}
